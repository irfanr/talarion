package com.mascova.talarion.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mascova.talarion.domain.Image;

public class ImageSpecification implements Specification<Image> {

  private SearchCriteria criteria;

  public ImageSpecification(SearchCriteria criteria) {
    super();
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(Root<Image> imageRoot, CriteriaQuery<?> query,
      CriteriaBuilder builder) {
    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return builder.greaterThanOrEqualTo(imageRoot.<String> get(criteria.getKey()), criteria
          .getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase("<")) {
      return builder.lessThanOrEqualTo(imageRoot.<String> get(criteria.getKey()), criteria
          .getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (imageRoot.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(imageRoot.<String> get(criteria.getKey()), "%" + criteria.getValue()
            + "%");
      } else {

        return builder.equal(imageRoot.get(criteria.getKey()), criteria.getValue());
      }
    }
    return null;
  }
}
