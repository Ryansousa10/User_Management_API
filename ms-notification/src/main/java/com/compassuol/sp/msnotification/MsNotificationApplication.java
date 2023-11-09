package com.compassuol.sp.msnotification;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsNotificationApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsNotificationApplication.class, args);
	}

}
