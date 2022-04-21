package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.build.incremental.HistoryManager;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static fr.uparis.pandaparser.utils.FilesUtils.*;
import static fr.uparis.pandaparser.utils.RegExUtils.*;

/**
 * Translation from markdown to HTML
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Simple extends PandaParser {

    /* An instance of the parser of common-markdown library */
    List<Extension> extensions = Arrays.asList(TablesExtension.create(), StrikethroughExtension.create());
    private final org.commonmark.parser.Parser parser = Parser.builder().extensions(extensions).build();


    public Simple(String input, String output, String template, boolean watch, int jobs) {
        super(input, output, template, watch, jobs, ParserType.SIMPLE);
    }

    private String getBodyContentWithoutHeaderHTML(String fileContent) {

        // remove the header from the fileContent
        String fileContentWithoutHeader = removeHeaderFromContent(fileContent);

        //parse the fileContentWithoutHeader in html for the key "content"
        Node document = parser.parse(fileContentWithoutHeader);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    @Override
    public void parse() {
        boolean shouldBeRebuild = HistoryManager.getInstance().shouldBeRebuild(input);
        System.out.println(FilesUtils.getFileName(input) + " : " + (shouldBeRebuild ? "Yes Rebuild" : "NO Rebuild"));
        if (!shouldBeRebuild) return;
        try {
            String inputFileName = FilesUtils.getHtmlFilenameFromMdFile(FilesUtils.getFileName(input));
            String fileContent = getFileContent(input);

            String bodyContentHTML = getBodyContentWithoutHeaderHTML(fileContent);
            Metadata meta = new Metadata(fileContent, bodyContentHTML);
            //if there is a path for the template in the metadata, take the path otherwise take the default template
            String FileContentAfterTemplate = TemplateProvider.applyTemplate(meta);
            createFileFromContent(this.output + inputFileName, FileContentAfterTemplate);
            log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + inputFileName);
            HistoryManager.getInstance().update(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
