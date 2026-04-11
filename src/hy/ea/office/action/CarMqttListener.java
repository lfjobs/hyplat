package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CarMqttListener{
	private static final Logger logger = LoggerFactory.getLogger(CarMqttListener.class);

    private CarMqttService carMqttService = null;
    @PostConstruct
    public void init(){
        logger.info("初始化道闸mqtt");
        //this.carMqttService = CarMqttService.getInstance();
    }
//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        try {
//            logger.info("发一条道闸mqtt消息试试");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    /*private CarMqttService carMqttService = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.carMqttService = CarMqttService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }*/
}