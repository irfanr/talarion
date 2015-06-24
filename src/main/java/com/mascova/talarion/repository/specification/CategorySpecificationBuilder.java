package com.mascova.talarion.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion.domain.Category;

public class CategorySpecificationBuilder {

  private final List<SearchCriteria> params;

  public CategorySpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public CategorySpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Category> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<Category>> specs = new ArrayList<Specification<Category>>();
    for (SearchCriteria param : params) {
      specs.add(new CategorySpecification(param));
    }

    Specification<Category> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
