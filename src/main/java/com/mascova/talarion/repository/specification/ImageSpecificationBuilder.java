package com.mascova.talarion.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion.domain.Image;

public class ImageSpecificationBuilder {

  private final List<SearchCriteria> params;

  public ImageSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public ImageSpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Image> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<Image>> specs = new ArrayList<Specification<Image>>();
    for (SearchCriteria param : params) {
      specs.add(new ImageSpecification(param));
    }

    Specification<Image> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
