package com.kren.java.se.practice.file.upload;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileRestControllerTest {

  private static File file;

  @BeforeAll
  @SneakyThrows
  static void createTestFile() {
    file = Paths.get("target", "random_bytes_data.txt").toFile();
    file.createNewFile(); // temp solution to run tests faster during development
    // DataGeneratorUtil.writeRandomBytes(file, 1);
  }

  @Autowired
  private TestRestTemplate restTemplate;

  private FileUploadTestClient client;

  @BeforeEach
  void setUp() {
    client = new FileUploadTestClient(restTemplate);
  }

  @Test
  void uploadFile() {
    var response = client.uploadFile(file);

    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody(), is("random_bytes_data.txt"));
  }

  @AfterAll
  static void deleteTestFile() {
    file.delete();
  }

  @AllArgsConstructor
  static class FileUploadTestClient {

    private final TestRestTemplate restTemplate;

    public ResponseEntity<String> uploadFile(File file) {
      var headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      var body = new LinkedMultiValueMap<String, Object>();
      body.add("file", new FileSystemResource(file));

      var requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);

      return restTemplate.postForEntity("/upload-file-form", requestEntity, String.class);
    }
  }
}