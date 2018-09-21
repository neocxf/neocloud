package top.neospot.cloud.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("annotatedServletHandler")
public class AnnotatedHttpServletRequestHandler implements HttpRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotatedHttpServletRequestHandler.class.getName());

    @Autowired
    private HelloService helloService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write("<h1>Spring Beans Injection into Java Servlets!</h1><h2>" + helloService.sayHello("World") + "</h2>");
    }
}

@Component
class HelloService {
    public String sayHello(String msg) {
        return "hello " + msg;
    }
}