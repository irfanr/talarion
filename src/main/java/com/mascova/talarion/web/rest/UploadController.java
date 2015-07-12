package com.mascova.talarion.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/upload")
  public void upload(@RequestParam("file") MultipartFile incomingFile, HttpServletRequest request)
      throws IOException {

    String fileName = incomingFile.getOriginalFilename();

    if (!incomingFile.isEmpty()) {

      uploadFile(incomingFile, request, fileName);

    }

  }

  private void uploadFile(MultipartFile file, HttpServletRequest request, String fileName) {
    InputStream inputStream;
    OutputStream outputStream;
    // BEGIN Upload file
    try {
      inputStream = file.getInputStream();

      String realPath = request.getSession().getServletContext().getRealPath("/");

      File newFile = new File(realPath + "/upload/" + fileName);
      if (!newFile.exists()) {
        newFile.createNewFile();
      }
      outputStream = new FileOutputStream(newFile);
      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // END Upload file
  }

}
