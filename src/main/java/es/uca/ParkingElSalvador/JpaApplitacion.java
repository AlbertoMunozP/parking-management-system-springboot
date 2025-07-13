package es.uca.ParkingElSalvador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JpaApplitacion {
    public static void main(String[] args){
        SpringApplication.run(JpaApplitacion.class, args);
    }
}