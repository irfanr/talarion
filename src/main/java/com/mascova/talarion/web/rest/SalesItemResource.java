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
import com.mascova.talarion.domain.SalesItem;
import com.mascova.talarion.repository.SalesItemRepository;
import com.mascova.talarion.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SalesItem.
 */
@RestController
@RequestMapping("/api")
public class SalesItemResource {

  private final Logger log = LoggerFactory.getLogger(SalesItemResource.class);

  @Inject
  private SalesItemRepository salesItemRepository;

  /**
   * POST /salesItems -> Create a new salesItem.
   */
  @RequestMapping(value = "/salesItems", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody SalesItem salesItem) throws URISyntaxException {
    log.debug("REST request to save SalesItem : {}", salesItem);
    if (salesItem.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new salesItem cannot already have an ID").build();
    }
    salesItemRepository.save(salesItem);
    return ResponseEntity.created(new URI("/api/salesItems/" + salesItem.getId())).build();
  }

  /**
   * PUT /salesItems -> Updates an existing salesItem.
   */
  @RequestMapping(value = "/salesItems", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody SalesItem salesItem) throws URISyntaxException {
    log.debug("REST request to update SalesItem : {}", salesItem);
    if (salesItem.getId() == null) {
      return create(salesItem);
    }
    salesItemRepository.save(salesItem);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /salesItems -> get all the salesItems.
   */
  @RequestMapping(value = "/salesItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<SalesItem>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
    Page<SalesItem> page = salesItemRepository.findAll(PaginationUtil.generatePageRequest(offset,
        limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salesItems",
        offset, limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /salesItems/:id -> get the "id" salesItem.
   */
  @RequestMapping(value = "/salesItems/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<SalesItem> get(@PathVariable Long id) {
    log.debug("REST request to get SalesItem : {}", id);
    return Optional.ofNullable(salesItemRepository.findOne(id))
        .map(salesItem -> new ResponseEntity<>(salesItem, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /salesItems/:id -> delete the "id" salesItem.
   */
  @RequestMapping(value = "/salesItems/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete SalesItem : {}", id);
    salesItemRepository.delete(id);
  }
}
