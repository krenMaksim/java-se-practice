package com.kren.java.se.practice.file.upload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
class FileRestController {

  @PostMapping("/upload-file-form")
  public String uploadFile(@RequestParam("file") MultipartFile file) {
    return file.getOriginalFilename();
  }

  // TBD
  @PostMapping("/upload-file-binary")
  public String uploadFileBinary(@RequestParam("data") MultipartFile file) {
    return "fileUploadView";
  }
}
