package com.mascova.talarion.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion.domain.Product;

public class ProductSpecificationBuilder {

  private final List<SearchCriteria> params;

  public ProductSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public ProductSpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Product> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<Product>> specs = new ArrayList<Specification<Product>>();
    for (SearchCriteria param : params) {
      specs.add(new ProductSpecification(param));
    }

    Specification<Product> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
