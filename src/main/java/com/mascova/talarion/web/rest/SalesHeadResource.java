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
import com.mascova.talarion.domain.SalesHead;
import com.mascova.talarion.repository.SalesHeadRepository;
import com.mascova.talarion.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SalesHead.
 */
@RestController
@RequestMapping("/api")
public class SalesHeadResource {

  private final Logger log = LoggerFactory.getLogger(SalesHeadResource.class);

  @Inject
  private SalesHeadRepository salesHeadRepository;

  /**
   * POST /sales-head -> Create a new salesHead.
   */
  @RequestMapping(value = "/sales-head", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody SalesHead salesHead) throws URISyntaxException {
    log.debug("REST request to save SalesHead : {}", salesHead);
    if (salesHead.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new salesHead cannot already have an ID").build();
    }
    salesHeadRepository.save(salesHead);
    return ResponseEntity.created(new URI("/api/sales-head/" + salesHead.getId())).build();
  }

  /**
   * PUT /sales-head -> Updates an existing salesHead.
   */
  @RequestMapping(value = "/sales-head", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody SalesHead salesHead) throws URISyntaxException {
    log.debug("REST request to update SalesHead : {}", salesHead);
    if (salesHead.getId() == null) {
      return create(salesHead);
    }
    salesHeadRepository.save(salesHead);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /sales-head -> get all the sales-head.
   */
  @RequestMapping(value = "/sales-head", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<SalesHead>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
    Page<SalesHead> page = salesHeadRepository.findAll(PaginationUtil.generatePageRequest(offset,
        limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sales-head",
        offset, limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /sales-head/:id -> get the "id" salesHead.
   */
  @RequestMapping(value = "/sales-head/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<SalesHead> get(@PathVariable Long id) {
    log.debug("REST request to get SalesHead : {}", id);
    return Optional.ofNullable(salesHeadRepository.findOne(id))
        .map(salesHead -> new ResponseEntity<>(salesHead, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /sales-head/:id -> delete the "id" salesHead.
   */
  @RequestMapping(value = "/sales-head/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete SalesHead : {}", id);
    salesHeadRepository.delete(id);
  }
}
