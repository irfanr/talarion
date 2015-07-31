package com.mascova.talarion.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.mascova.talarion.domain.Image;
import com.mascova.talarion.repository.ImageRepository;
import com.mascova.talarion.repository.specification.ImageSpecificationBuilder;
import com.mascova.talarion.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class ImageResource {

  private final Logger log = LoggerFactory.getLogger(ImageResource.class);

  @Inject
  private ImageRepository imageRepository;

  private String realPath;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/image", method = RequestMethod.POST)
  public ResponseEntity<Void> create(@RequestParam("file") MultipartFile incomingFile,
      HttpServletRequest request) throws IOException, URISyntaxException {

    realPath = request.getSession().getServletContext().getRealPath("/");

    String fileName = incomingFile.getOriginalFilename();

    if (!incomingFile.isEmpty()) {

      File newFile = uploadFile(incomingFile, fileName);

      Image image = new Image();
      image.setName(fileName);
      image.setPath(realPath + "upload/images/" + fileName);
      image.setType(FilenameUtils.getExtension(newFile.getAbsolutePath()));

      imageRepository.save(image);

    }

    return ResponseEntity.created(new URI("/api/image/")).build();

  }

  /**
   * GET /product -> get all the products.
   */
  @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Image>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    ImageSpecificationBuilder builder = new ImageSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<Image> page = imageRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/image", offset,
        limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  private File uploadFile(MultipartFile file, String fileName) {
    InputStream inputStream;
    OutputStream outputStream;
    File newFile = null;
    // BEGIN Upload file
    try {
      inputStream = file.getInputStream();

      newFile = new File(realPath + "/upload/images/" + fileName);
      if (!newFile.exists()) {
        newFile.createNewFile();
      }
      outputStream = new FileOutputStream(newFile);
      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }

      return newFile;

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // END Upload file
    return newFile;
  }

  /**
   * GET /image/:id -> get the "id" category.
   */
  @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Image> get(@PathVariable Long id) {
    log.debug("REST request to get Category : {}", id);
    return Optional.ofNullable(imageRepository.findOne(id))
        .map(image -> new ResponseEntity<>(image, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /category/:id -> delete the "id" category.
   */
  @RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id, HttpServletRequest request) {
    log.debug("REST request to delete Image : {}", id);
    Image image = imageRepository.findOne(id);
    if (image != null) {
      imageRepository.delete(id);
      deleteFile(request, image.getName());
    }

  }

  private void deleteFile(HttpServletRequest request, String fileName) {

    realPath = request.getSession().getServletContext().getRealPath("/");

    File existingFile = new File(realPath + "/upload/images/" + fileName);
    if (existingFile.exists()) {
      existingFile.delete();
    }
  }

}
