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
class TraineeshipManagementApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		// απλώς ελέγχει ότι το Spring context φορτώνει χωρίς σφάλματα
	}

	@Test
	void homePage_Returns200() {
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
