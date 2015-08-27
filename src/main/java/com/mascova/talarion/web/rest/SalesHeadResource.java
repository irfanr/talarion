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
   * POST /salesHeads -> Create a new salesHead.
   */
  @RequestMapping(value = "/salesHeads", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody SalesHead salesHead) throws URISyntaxException {
    log.debug("REST request to save SalesHead : {}", salesHead);
    if (salesHead.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new salesHead cannot already have an ID").build();
    }
    salesHeadRepository.save(salesHead);
    return ResponseEntity.created(new URI("/api/salesHeads/" + salesHead.getId())).build();
  }

  /**
   * PUT /salesHeads -> Updates an existing salesHead.
   */
  @RequestMapping(value = "/salesHeads", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
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
   * GET /salesHeads -> get all the salesHeads.
   */
  @RequestMapping(value = "/salesHeads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<SalesHead>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
    Page<SalesHead> page = salesHeadRepository.findAll(PaginationUtil.generatePageRequest(offset,
        limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salesHeads",
        offset, limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /salesHeads/:id -> get the "id" salesHead.
   */
  @RequestMapping(value = "/salesHeads/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<SalesHead> get(@PathVariable Long id) {
    log.debug("REST request to get SalesHead : {}", id);
    return Optional.ofNullable(salesHeadRepository.findOne(id))
        .map(salesHead -> new ResponseEntity<>(salesHead, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /salesHeads/:id -> delete the "id" salesHead.
   */
  @RequestMapping(value = "/salesHeads/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete SalesHead : {}", id);
    salesHeadRepository.delete(id);
  }
}
