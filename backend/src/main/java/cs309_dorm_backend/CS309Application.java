package cs309_dorm_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableSwagger2
public class CS309Application {
    // remember to create trigger in database first.

    public static void main(String[] args) {
        SpringApplication.run(CS309Application.class, args);
    }

}
