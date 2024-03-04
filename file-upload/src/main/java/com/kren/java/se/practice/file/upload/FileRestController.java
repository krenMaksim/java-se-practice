package com.kren.java.se.practice.file.upload;

import com.kren.java.se.practice.io.ByteNioUtil;
import com.kren.java.se.practice.io.ByteStreamsUtil;
import com.kren.java.se.practice.io.DataGeneratorUtil;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import java.io.File;
import java.nio.channels.Channels;
import java.nio.file.Paths;

@Slf4j
@RestController
class FileRestController {

  private File file;

  @SneakyThrows
  @PostConstruct
  void init() {
    file = Paths.get("file-upload", "target", "controller_file.txt").toFile();
    if (!file.exists()) {
      file.createNewFile();
      DataGeneratorUtil.writeRandomBytes(file, 1);
    }
  }

  // focus on ---------------------

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

  @GetMapping("/download-file")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile() {

    Resource file = new PathResource(this.file.toPath()); // storageService.loadAsResource(filename);

    if (file == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  // focus on ----------------

  // TBD
  @PostMapping("/upload-file-form-v2")
  public String uploadFile2(
      @RequestParam("file") MultipartFile file,
      @RequestParam("io_lib") String lib, // io, nio
      @RequestParam("buffer_size") Integer bufferSize) {

    // read file different methods
    // calculate size and return it
    // add tests showing how performance is different for different technology and buffer

    // do the same functionality for downloading files
    // do the same functionality for Servlet API

    log.info("Uploaded {}", file.getOriginalFilename());
    readInputStream(file);
    return file.getOriginalFilename();
  }

  private static final int EOF_BYTE = -1;

  @SneakyThrows
  private static void readInputStream(MultipartFile file) {
    try (var input = new BufferedInputStream(file.getInputStream())) {
      int byteOfData;
      while ((byteOfData = input.read()) != EOF_BYTE) {
        System.out.print((char) byteOfData);
      }
    }
  }

  // TBD
  @PostMapping("/upload-file-binary")
  public String uploadFileBinary(@RequestParam("data") MultipartFile file) {
    return "fileUploadView";
  }
}
