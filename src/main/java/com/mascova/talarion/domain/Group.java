package com.mascova.talarion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "JHI_GROUP")
public class Group implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "group")
  @JsonIgnore
  private Set<User> users = new HashSet<>();

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "JHI_GROUP_AUTHORITY", joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") })
  private Set<Authority> authorities = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

}
