package fr.uparis.pandaparser.core.serve.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_0;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class HttpPandaParserServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String output;
    private FullHttpRequest request;

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
    private static final String INDEX_HTML_FILE = "index.html";


    public HttpPandaParserServerHandler(String output) {
        if (!output.endsWith(File.separator))
            output += File.separator;
        this.output = output;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) throws Exception {
        this.request = request;
        if (this.request.decoderResult().isFailure()) {
            this.sendError(context, BAD_REQUEST);
            return;
        }

        if (!GET.equals(request.method())) {
            sendError(context, METHOD_NOT_ALLOWED);
            return;
        }
        final boolean keepAlive = HttpUtil.isKeepAlive(request);

        final String uri = request.uri();

        final String path = this.output + sanitizeUri(uri);

        System.out.println("path url  : " + path);
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(context, NOT_FOUND);
            return;
        }

        if (!file.isFile()) {
            sendError(context, FORBIDDEN);
            return;
        }

        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException ignore) {
            sendError(context, NOT_FOUND);
            return;
        }
        long fileLength = raf.length();

        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        HttpUtil.setContentLength(response, fileLength);

        if (!keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        } else if (request.protocolVersion().equals(HTTP_1_0)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // Write the initial line and the header.
        context.write(response);

        // Write the content.
        ChannelFuture sendFileFuture;
        ChannelFuture lastContentFuture;
        if (context.pipeline().get(SslHandler.class) == null) {
            sendFileFuture =
                    context.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength), context.newProgressivePromise());
            // Write the end marker.
            lastContentFuture = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        } else {
            sendFileFuture =
                    context.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength, 8192)),
                            context.newProgressivePromise());
            // HttpChunkedInput will write the end marker (LastHttpContent) for us.
            lastContentFuture = sendFileFuture;
        }

        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                if (total < 0) { // total unknown
                    System.err.println(future.channel() + " Transfer progress: " + progress);
                } else {
                    System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) {
                System.err.println(future.channel() + " Transfer complete.");
            }
        });

        // Decide whether to close the connection or not.
        if (!keepAlive) {
            // Close the connection when the whole content is written out.
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }


    private static String sanitizeUri(String uri) {
        // Decode the path.
        uri = URLDecoder.decode(uri, StandardCharsets.UTF_8);

        if (uri.isEmpty() || uri.charAt(0) != '/') {
            return null;
        }

        // Convert file separators.
        uri = uri.replace('/', File.separatorChar);

        // Simplistic dumb security check.
        // You will have to do something serious in the production environment.
        if (uri.contains(File.separator + '.') ||
                uri.contains('.' + File.separator) ||
                uri.charAt(0) == '.' || uri.charAt(uri.length() - 1) == '.' ||
                INSECURE_URI.matcher(uri).matches()) {
            return null;
        }

        return uri.equals(File.separator) ? INDEX_HTML_FILE : uri;
    }


    private void sendError(ChannelHandlerContext context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        this.sendAndCleanupConnection(context, response);
    }

    /**
     * If Keep-Alive is disabled, attaches "Connection: close" header to the response
     * and closes the connection after the response being sent.
     */
    private void sendAndCleanupConnection(ChannelHandlerContext context, FullHttpResponse response) {
        final FullHttpRequest request = this.request;
        final boolean keepAlive = HttpUtil.isKeepAlive(request);
        HttpUtil.setContentLength(response, response.content().readableBytes());
        if (!keepAlive) {
            // We're going to close the connection as soon as the response is sent,
            // so we should also make it clear for the client.
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        } else if (request.protocolVersion().equals(HTTP_1_0)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ChannelFuture flushPromise = context.writeAndFlush(response);

        if (!keepAlive) {
            // Close the connection as soon as the response is sent.
            flushPromise.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
