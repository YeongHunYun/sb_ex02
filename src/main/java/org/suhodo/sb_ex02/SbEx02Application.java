package org.suhodo.sb_ex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SbEx02Application {

    public static void main(String[] args) {
        SpringApplication.run(SbEx02Application.class, args);
    }

}
