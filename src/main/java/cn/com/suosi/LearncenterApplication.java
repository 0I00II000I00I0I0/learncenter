package cn.com.suosi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "cn.com.suosi.mapper")
@SpringBootApplication
public class LearncenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearncenterApplication.class, args);
    }
}
