package top.neospot.cloud.inventory.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.neospot.cloud.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@WebListener
@Slf4j
@Component
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("init processor thread pool");

        RequestProcessorThreadPool threadPool = RequestProcessorThreadPool.getInstance();
        threadPool.init();
        sce.getServletContext().setAttribute(RequestProcessorThreadPool.class.getName(), threadPool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("going to destroy processor thread pool");
        RequestProcessorThreadPool processorThreadPool = (RequestProcessorThreadPool) sce.getServletContext().getAttribute(RequestProcessorThreadPool.class.getName());
        try {
            processorThreadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
