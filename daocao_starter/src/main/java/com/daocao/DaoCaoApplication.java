package com.daocao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DaoCaoApplication {

    //启动工程
    public static void main(String[] args) {
        SpringApplication.run(DaoCaoApplication.class,args);

    }

}
