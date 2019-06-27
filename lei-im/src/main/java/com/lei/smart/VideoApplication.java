package com.lei.smart;

import com.lei.smart.im.MiniChatServer;
import com.lei.smart.util.SpringApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class VideoApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VideoApplication.class);
    @Value("${nettyport}")
    private Integer port;

    public static void main(String[] args) throws Exception {
        // SpringApplication 将引导我们的应用，启动 Spring，相应地启动被自动配置的 Tomcat web 服务器。
        ConfigurableApplicationContext run = SpringApplication.run(VideoApplication.class, args);
        SpringApplicationContextUtil.setApplicationContext(run);

    }

    // 注意这里的 run 方法是重载自 CommandLineRunner
    @Override
    public void run(String... args) throws Exception {

//        SchedultaskController schedultaskController = SpringApplicationContextUtil.getBean(SchedultaskController.class);
//        WebSocketClientUtil.clientCompose= WebSocketClientUtil.getWebsocket();
        MiniChatServer ws =new MiniChatServer();
        logger.info("Netty's ws server is listen: "+port);
        ws.init();
        ws.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ws.shutdown();
            }
        });
    }

}
