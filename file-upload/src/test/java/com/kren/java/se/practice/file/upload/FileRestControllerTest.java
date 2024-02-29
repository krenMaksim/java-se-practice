package com.kren.java.se.practice.file.upload;

import com.kren.java.se.practice.file.upload.test.util.FileUploadTestClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FileRestControllerTest {

  private static final String FILE_NAME = "random_bytes_data.txt";

  private static File file;

  @BeforeAll
  @SneakyThrows
  static void createTestFile() {
    file = Paths.get("target", FILE_NAME).toFile();
    file.createNewFile(); // temp solution to run tests faster during development
    // DataGeneratorUtil.writeRandomBytes(file, 1);
  }

  private FileUploadTestClient client;

  @BeforeEach
  void setUp(@Autowired TestRestTemplate restTemplate) {
    client = new FileUploadTestClient(restTemplate);
  }

  @Test
  void uploadFile() {
    var response = client.uploadFile(file);

    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody(), is(FILE_NAME));
  }

  @AfterAll
  static void deleteTestFile() {
    file.delete();
  }
}