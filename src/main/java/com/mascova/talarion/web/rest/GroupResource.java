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
import com.mascova.talarion.domain.Group;
import com.mascova.talarion.repository.GroupRepository;
import com.mascova.talarion.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Group.
 */
@RestController
@RequestMapping("/api")
public class GroupResource {

  private final Logger log = LoggerFactory.getLogger(GroupResource.class);

  @Inject
  private GroupRepository groupRepository;

  /**
   * POST /group -> Create a new group.
   */
  @RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Group group) throws URISyntaxException {
    log.debug("REST request to save Group : {}", group);
    if (group.getId() != null) {
      return ResponseEntity.badRequest().header("Failure", "A new group cannot already have an ID")
          .build();
    }
    groupRepository.save(group);
    return ResponseEntity.created(new URI("/api/group/" + group.getId())).build();
  }

  /**
   * PUT /group -> Updates an existing group.
   */
  @RequestMapping(value = "/group", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Group group) throws URISyntaxException {
    log.debug("REST request to update Group : {}", group);
    if (group.getId() == null) {
      return create(group);
    }
    groupRepository.save(group);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /group -> get all the group.
   */
  @RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Group>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
    Page<Group> page = groupRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group", offset,
        limit);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /group/:id -> get the "id" group.
   */
  @RequestMapping(value = "/group/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Group> get(@PathVariable Long id) {
    log.debug("REST request to get Group : {}", id);
    return Optional.ofNullable(groupRepository.findOne(id))
        .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /group/:id -> delete the "id" group.
   */
  @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete    : {}", id);
    groupRepository.delete(id);
  }
}
