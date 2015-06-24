package com.mascova.talarion.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import com.mascova.talarion.domain.Product;
import com.mascova.talarion.repository.ProductRepository;
import com.mascova.talarion.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

  private final Logger log = LoggerFactory.getLogger(ProductResource.class);

  @Inject
  private ProductRepository productRepository;

  /**
   * POST /product -> Create a new product.
   */
  @RequestMapping(value = "/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Product product) throws URISyntaxException {
    log.debug("REST request to save Product : {}", product);
    if (product.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new product cannot already have an ID").build();
    }
    productRepository.save(product);
    return ResponseEntity.created(new URI("/api/product/" + product.getId())).build();
  }

  /**
   * PUT /product -> Updates an existing product.
   */
  @RequestMapping(value = "/product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Product product) throws URISyntaxException {
    log.debug("REST request to update Product : {}", product);
    if (product.getId() == null) {
      return create(product);
    }
    productRepository.save(product);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /product -> get all the products.
   */
  @RequestMapping(value = "/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Product>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
    Page<Product> page = productRepository.findAll(PaginationUtil
        .generatePageRequest(offset, limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product",
        offset, limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /product/:id -> get the "id" product.
   */
  @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Product> get(@PathVariable Long id) {
    log.debug("REST request to get Product : {}", id);
    return Optional.ofNullable(productRepository.findOne(id))
        .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /product/:id -> delete the "id" product.
   */
  @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Product : {}", id);
    productRepository.delete(id);
  }
}
