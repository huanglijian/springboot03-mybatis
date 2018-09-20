package cn.ck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.ck.mapper")
public class ChuangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChuangApplication.class, args);
    }
}
