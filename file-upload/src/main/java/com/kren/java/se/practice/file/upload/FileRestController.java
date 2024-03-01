package com.kren.java.se.practice.file.upload;

import com.kren.java.se.practice.io.ByteNioUtil;
import com.kren.java.se.practice.io.ByteStreamsUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.nio.channels.Channels;
import java.util.Objects;

@Slf4j
@RestController
class FileRestController {

  // focus on ---------------------

  @PostMapping("/upload-file-form")
  public Integer uploadFile(@RequestParam("file") MultipartFile file,
      @RequestParam(value = "buffer_size", required = false) Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    readFile(file, processor, bufferSizeBytes);
    return processor.getReceivedBytes();
  }

  @SneakyThrows
  private void readFile(MultipartFile file, ByteProcessor processor, Integer bufferSizeBytes) {
    if (Objects.isNull(bufferSizeBytes)) {
      ByteStreamsUtil.readInputStream(file.getInputStream(), processor::handleByte);
    } else {
      ByteStreamsUtil.readInputStream(new BufferedInputStream(file.getInputStream(), bufferSizeBytes), processor::handleByte);
    }
  }

  @SneakyThrows
  @PostMapping("/upload-file-nio-form")
  public Integer uploadFileNio(@RequestParam("file") MultipartFile file,
      @RequestParam(value = "buffer_size", required = false) Integer bufferSizeBytes) {
    var processor = new ByteProcessor();
    if (Objects.isNull(bufferSizeBytes)) {
      //TBD
    } else {
      ByteNioUtil.readBytes(Channels.newChannel(file.getInputStream()), bufferSizeBytes, processor::handleBuffer);
    }
    return processor.getReceivedBytes();
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
    // OPTIONAL extend existing implementation for characters

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
