package fr.uparis.pandaparser.core.serve.http;

import lombok.extern.java.Log;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

@Log
public record HttpPandaParserServer(int port, String output) {

    static final boolean SSL = System.getProperty("ssl") != null;

    public void start() throws CertificateException, SSLException {

    }
}
