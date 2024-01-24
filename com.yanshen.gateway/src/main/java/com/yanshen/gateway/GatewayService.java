package com.yanshen.gateway;

import cn.hutool.core.util.StrUtil;
import com.yanshen.common.core.util.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayService {
    public static void main(String[] args) {
        SpringApplication application=  new SpringApplication(GatewayService.class);
        ConfigurableApplicationContext run = application.run(args);
        Environment env = run.getEnvironment();
        String ip = IpUtils.getLocalHostExactAddress().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        System.out.println("(♥◠‿◠)ﾉﾞ  网关服务启动成功   ლ(´ڡ`ლ)ﾞ ");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Service Swagger文档地址: \thttp://" + ip + ":" + port + (StrUtil.isBlank(path) ? "" : path) + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}