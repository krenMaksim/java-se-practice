package com.kren.java.se.practice.file.upload.servlet;

import com.kren.java.se.practice.file.upload.rest.FileRestController;
import com.kren.java.se.practice.file.upload.util.ByteProcessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet(urlPatterns = "/servlet/upload-file", loadOnStartup = 1)
public class FileUploaderServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    var bufferSize = Integer.valueOf(request.getParameter("buffer_size"));
    var inputStream = request.getPart("file").getInputStream();
    var processor = new ByteProcessor();

    FileRestController.readFile(inputStream, processor, bufferSize);

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.println(processor.getReceivedBytes());
  }
}
