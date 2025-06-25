package io.molenaar;

import io.molenaar.module.wordFrequencyAnalyzer.WordFrequencyAnalyzerControllerImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static void main(String[] args) throws IOException {
        ResourceConfig rc = new ResourceConfig()
                .packages("io.molenaar")
                .packages(
                        "org.glassfish.jersey.jackson",
                        "org.glassfish.jersey.server.validation",
                        "org.glassfish.jersey.media.multipart"
                );

        HttpServer server = HttpServer.createSimpleServer("/", 8080);
        WebappContext context = new WebappContext("api", "/");

        ServletContainer container = new ServletContainer(rc);
        ServletRegistration servlet = context.addServlet("jersey-servlet", container);
        servlet.setInitParameter("ApiFrequency.implementation", WordFrequencyAnalyzerControllerImpl.class.getCanonicalName());
        servlet.addMapping("/*");

        context.deploy(server);
        server.start();

        System.out.println("Server started at " + BASE_URI);
    }
}
