package com.mascova.talarion.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mascova.talarion.domain.util.CustomDateTimeDeserializer;
import com.mascova.talarion.domain.util.CustomDateTimeSerializer;

@Entity
@Table(name = "sales_head")
public class SalesHead implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  @Column(name = "sales_date", nullable = false)
  private DateTime salesDate = null;

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  private Double discount;

  private Double vat;

  @OneToMany(mappedBy = "salesHead")
  @JsonIgnore
  private Set<SalesItem> salesItems = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public DateTime getSalesDate() {
    return salesDate;
  }

  public void setSalesDate(DateTime salesDate) {
    this.salesDate = salesDate;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Double getVat() {
    return vat;
  }

  public void setVat(Double vat) {
    this.vat = vat;
  }

  public Set<SalesItem> getSalesItems() {
    return salesItems;
  }

  public void setSalesItems(Set<SalesItem> salesItems) {
    this.salesItems = salesItems;
  }

}
