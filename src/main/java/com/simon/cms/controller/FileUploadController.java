package com.simon.cms.controller;

import com.simon.cms.Exception.StorageFileNotFoundException;
import com.simon.cms.dao.impl.StorageDAOImpl;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
public class FileUploadController {

  private final StorageDAOImpl storageDAO;

  @Autowired
  public FileUploadController(StorageDAOImpl storageDAO) {
    this.storageDAO = storageDAO;
  }

  @GetMapping("/noncpasca")
  public String listUploadedFiles(Model model) {

    model.addAttribute("files", storageDAO.loadAll().map(
        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
            "serveFile", path.getFileName().toString()).build().toString())
                                    .collect(Collectors.toList()));

    return "Files";
  }

  @GetMapping("/files/{filename:(?:\\w|\\W)+\\.(?:jpg|bmp|gif|jpeg|png)$}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    Resource file = storageDAO.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/noncpasca")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {

    storageDAO.store(file);
    redirectAttributes.addFlashAttribute("message",
        "GG --> " + file.getOriginalFilename());

    return "redirect:/noncpasca";
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
  public ResponseEntity<?> handleStorageSizeLimitExceeded(FileUploadBase.SizeLimitExceededException ex){
    return ResponseEntity.unprocessableEntity().build();
  }

}