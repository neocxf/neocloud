//package top.neospot.cloud.common.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
//import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
//import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistrationAutoConfiguration;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
///**
// *  resolve the issues from
// *  https://github.com/spring-cloud/spring-cloud-consul/issues/302
// */
//@Configuration
//@ConditionalOnConsulEnabled
//@ConditionalOnMissingBean(type= "org.springframework.cloud.consul.discovery.ConsulLifecycle")
//@AutoConfigureAfter(ConsulAutoServiceRegistrationAutoConfiguration.class)
//public class MyConsulLifecycle implements ApplicationContextAware {
//
//     @Autowired(required=false)
//     private ConsulAutoServiceRegistration registration;
//
//     @Autowired
//     private Environment environment;
//
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//       if (registration !=null){
//          String portNumber = environment.getProperty("server.port");
//          registration.setPort(Integer.parseInt(portNumber));
//          registration.start();
//       }
//    }
//}