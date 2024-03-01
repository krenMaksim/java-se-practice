package com.kren.java.se.practice.file.upload.test.util;

import lombok.AllArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;

@AllArgsConstructor
public class FileUploadTestClient {

  private final TestRestTemplate restTemplate;

  public ResponseEntity<Integer> uploadFile(File file) {
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    var body = new LinkedMultiValueMap<String, Object>();
    body.add("file", new FileSystemResource(file));

    var requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);

    return restTemplate.postForEntity("/upload-file-form", requestEntity, Integer.class);
  }
}
