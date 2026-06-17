package cc.kertaskerja.kepegawaian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class KepegawaianApplication {

    public static void main(String[] args) {
        SpringApplication.run(KepegawaianApplication.class, args);
    }

}
