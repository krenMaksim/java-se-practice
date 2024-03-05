package com.kren.java.se.practice.file.upload;

import com.kren.java.se.practice.io.ByteNioUtil;
import com.kren.java.se.practice.io.ByteStreamsUtil;
import com.kren.java.se.practice.io.DataGeneratorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
class FileRestController {

  private final Resource file;

  @SneakyThrows
  FileRestController(@Value("${spring.servlet.multipart.location}") String tempFiles) {
    var file = Paths.get(tempFiles, "controller_file.txt");
    if (!Files.exists(file)) {
      Files.createFile(file);
      DataGeneratorUtil.writeRandomBytes(file.toFile(), 1);
    }
    this.file = new PathResource(file);
  }

  @PostMapping("/upload-file-form")
  public Integer uploadFile(@RequestParam("file") MultipartFile file,
      @RequestParam(value = "buffer_size") Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    readFile(file, processor, bufferSizeBytes);
    return processor.getReceivedBytes();
  }

  @SneakyThrows
  private void readFile(MultipartFile file, ByteProcessor processor, Integer bufferSizeBytes) {
    if (bufferSizeBytes == 0) {
      ByteStreamsUtil.readInputStream(file.getInputStream(), processor::handleByte);
    } else {
      ByteStreamsUtil.readInputStream(new BufferedInputStream(file.getInputStream(), bufferSizeBytes), processor::handleByte);
    }
  }

  @SneakyThrows
  @PostMapping("/upload-file-nio-form")
  public Integer uploadFileNio(@RequestParam("file") MultipartFile file,
      @RequestParam(value = "buffer_size") Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    ByteNioUtil.readBytes(Channels.newChannel(file.getInputStream()), bufferSizeBytes, processor::handleBuffer);
    return processor.getReceivedBytes();
  }

  // https://docs.spring.io/spring-framework/reference/core/resources.html
  // https://spring.io/guides/gs/uploading-files
  // Probably we can implement this sample https://www.baeldung.com/webclient-stream-large-byte-array-to-file#our-scenario-with-a-simple-server

  @GetMapping("/download-file")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile() {
    var contentDisposition = String.format("attachment; filename=\"%s\"", file.getFilename());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(file);
  }

  // TBD
  // read file different methods
  // calculate size and return it
  // add tests showing how performance is different for different technology and buffer

  // do the same functionality for downloading files
  // do the same functionality for Servlet API
}
