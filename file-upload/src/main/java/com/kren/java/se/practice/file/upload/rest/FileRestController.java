package com.kren.java.se.practice.file.upload.rest;

import com.kren.java.se.practice.file.upload.util.ByteProcessor;
import com.kren.java.se.practice.io.ByteNioUtil;
import com.kren.java.se.practice.io.ByteStreamsUtil;
import com.kren.java.se.practice.io.DataGeneratorUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
  https://docs.spring.io/spring-framework/reference/core/resources.html
  https://spring.io/guides/gs/uploading-files
 */

@RestController
public class FileRestController {

  private static final int FILE_SIZE_MB = 1;
  static final long FILE_SIZE_BYTE = FILE_SIZE_MB * 1_000_000;

  private final Resource file;

  @SneakyThrows
  FileRestController(@Value("${spring.servlet.multipart.location}") String tempFiles) {
    var file = Paths.get(tempFiles, "controller_file.txt");
    Files.deleteIfExists(file);
    Files.createFile(file);
    DataGeneratorUtil.writeRandomBytes(file.toFile(), FILE_SIZE_MB);
    this.file = new PathResource(file);
  }

  @SneakyThrows
  @PostMapping("/upload-file-form")
  Integer uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "buffer_size") Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    var inputStream = file.getInputStream();
    readFile(inputStream, processor, bufferSizeBytes);
    return processor.getReceivedBytes();
  }

  @SneakyThrows
  public static void readFile(InputStream inputStream, ByteProcessor processor, Integer bufferSizeBytes) {
    if (bufferSizeBytes == 0) {
      ByteStreamsUtil.readInputStream(inputStream, processor::handleByte);
    } else {
      ByteStreamsUtil.readInputStream(new BufferedInputStream(inputStream, bufferSizeBytes), processor::handleByte);
    }
  }

  @SneakyThrows
  @PostMapping("/upload-file-nio-form")
  Integer uploadFileNio(@RequestParam("file") MultipartFile file, @RequestParam(value = "buffer_size") Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    ByteNioUtil.readBytes(Channels.newChannel(file.getInputStream()), bufferSizeBytes, processor::handleBuffer);
    return processor.getReceivedBytes();
  }

  @GetMapping("/download-file")
  @ResponseBody
  ResponseEntity<Resource> downloadFile() {
    var contentDisposition = String.format("attachment; filename=\"%s\"", file.getFilename());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(file);
  }
}
