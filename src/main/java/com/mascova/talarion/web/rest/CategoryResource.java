package com.mascova.talarion.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mascova.talarion.domain.Category;
import com.mascova.talarion.repository.CategoryRepository;
import com.mascova.talarion.repository.specification.CategorySpecificationBuilder;
import com.mascova.talarion.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

  private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

  @Inject
  private CategoryRepository categoryRepository;

  /**
   * POST /category -> Create a new category.
   */
  @RequestMapping(value = "/category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Category category) throws URISyntaxException {
    log.debug("REST request to save Category : {}", category);
    if (category.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new category cannot already have an ID").build();
    }
    categoryRepository.save(category);
    return ResponseEntity.created(new URI("/api/category/" + category.getId())).build();
  }

  /**
   * PUT /category -> Updates an existing category.
   */
  @RequestMapping(value = "/category", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Category category) throws URISyntaxException {
    log.debug("REST request to update Category : {}", category);
    if (category.getId() == null) {
      return create(category);
    }
    categoryRepository.save(category);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /category -> get all the categorys.
   */
  @RequestMapping(value = "/category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Category>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    CategorySpecificationBuilder builder = new CategorySpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<Category> page = categoryRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/category",
        offset, limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /category/:id -> get the "id" category.
   */
  @RequestMapping(value = "/category/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Category> get(@PathVariable Long id) {
    log.debug("REST request to get Category : {}", id);
    return Optional.ofNullable(categoryRepository.findOne(id))
        .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /category/:id -> delete the "id" category.
   */
  @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Category : {}", id);
    categoryRepository.delete(id);
  }
}
