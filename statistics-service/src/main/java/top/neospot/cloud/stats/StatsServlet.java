package top.neospot.cloud.stats;

import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.annotation.WebServlet;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/19.
 */
@WebServlet(urlPatterns = "/stats", name = "annotatedServletHandler")
public class StatsServlet extends HttpRequestHandlerServlet {


}
