package com.cse.traineeship;

import com.cse.traineeship.TraineeshipManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = TraineeshipManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BasicEndpointsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void homePage_Returns200() {
        ResponseEntity<String> res = restTemplate.getForEntity("/", String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginPage_Returns200() {
        ResponseEntity<String> res = restTemplate.getForEntity("/login", String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void registerPage_Returns200() {
        ResponseEntity<String> res = restTemplate.getForEntity("/register", String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
