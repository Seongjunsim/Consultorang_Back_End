package com.hungry.consultorang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ConsultorangApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ConsultorangApplication.class);
		// Linux 에서 종료 시그널을 보낼 때 필요한 pid 파일 만드는 코드
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
