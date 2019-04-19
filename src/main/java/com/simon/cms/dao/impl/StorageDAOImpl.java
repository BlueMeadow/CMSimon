package com.simon.cms.dao.impl;

import com.simon.cms.Exception.StorageException;
import com.simon.cms.Exception.StorageFileNotFoundException;
import com.simon.cms.config.StorageConfig;
import com.simon.cms.dao.dao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Repository
public class StorageDAOImpl implements StorageDAO {

  private final Path rootLocation;

  @Autowired
  public StorageDAOImpl(StorageConfig properties) {
    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public void store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      if (file.isEmpty()) {
        throw new StorageException("Impossible de d'enregistrer le fichier, " + filename +" est vide.");
      }
      if (filename.contains("..")) {
        throw new StorageException(
            "Impossible de stocker un fichier dont le chemin relatif est hors de ce dossier."
                + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, this.rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }

    }
    catch (IOException e) {
      throw new StorageException("Echec lors de l'enregistrement du fichier " + filename, e);
    }

  }


  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 1)
                 .filter(path -> !path.equals(this.rootLocation))
                 .map(this.rootLocation::relativize);
    }
    catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    }
    catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    }
    catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

}
