package com.kren.java.se.practice.file.upload.servlet;

import com.kren.java.se.practice.file.upload.test.util.FileUploadTestClient;
import com.kren.java.se.practice.io.DataGeneratorUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FileUploaderServletTest {

  private static final int FILE_SIZE_MB = 1;
  private static final int FILE_SIZE_BYTE = FILE_SIZE_MB * 1_000_000;
  private static final String FILE_NAME = "random_bytes_data.txt";

  private static File file;

  @BeforeAll
  static void createTestFile() {
    file = Paths.get("target", FILE_NAME).toFile();
    DataGeneratorUtil.writeRandomBytes(file, FILE_SIZE_MB);
  }

  private FileUploadTestClient client;

  @BeforeEach
  void setUp(@Autowired TestRestTemplate restTemplate) {
    client = new FileUploadTestClient(restTemplate);
  }

  @ParameterizedTest
  @ValueSource(ints = {
      1024
  })
  void uploadFile(Integer bufferSizeBytes) {
    var response = client.uploadFile("/servlet/upload-file", file, bufferSizeBytes);

    assertThat(response.getStatusCode(), is(OK));
    assertThat(response.getBody(), is(FILE_SIZE_BYTE));
  }

  @AfterAll
  static void deleteTestFile() {
    file.delete();
  }
}