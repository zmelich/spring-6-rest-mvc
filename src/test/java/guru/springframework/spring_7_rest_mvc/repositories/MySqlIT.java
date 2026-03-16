package guru.springframework.spring_7_rest_mvc.repositories;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import guru.springframework.spring_7_rest_mvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//Deprecated!
//import org.testcontainers.containers.MySQLContainer;
//New
import org.testcontainers.mysql.MySQLContainer;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlIT {

    @Container
    @ServiceConnection
    //Old container class
    //static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9");
    //New container class
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.2");

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {

        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(0);

    }
}
