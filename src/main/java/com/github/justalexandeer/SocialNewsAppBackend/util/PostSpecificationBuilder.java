package com.github.justalexandeer.SocialNewsAppBackend.util;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PostSpecificationBuilder {

    private Specification<Post> specification;

    public PostSpecificationBuilder() {
        specification = initSpecification();
    }

    public void with(PostSearchCriteria criteria, String value) {
        switch (criteria) {
            case AFTER_POST_DATE: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(afterPostDate(value));
                }
                break;
            }
            case BEFORE_POST_DATE: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(beforePostDate(value));
                }
                break;
            }
            case NAME_AUTHOR: {
                if (value != null && !value.isEmpty()) {
                    System.out.println("name author");
                    specification = specification.and(likeName(value));
                }
                break;
            }
            case ID_CATEGORY: {
                if (value != null && !value.isEmpty()) {

                }
                break;
            }
            case ID_TAG: {
                if (value != null && !value.isEmpty()) {

                }
                break;
            }
            case POST_NAME: {
                if (value != null && !value.isEmpty()) {

                }
                break;
            }
            case POST_CONTENT: {
                if (value != null && !value.isEmpty()) {

                }
                break;
            }
        }
    }

    public Specification<Post> build() {
        return specification;
    }

    private Specification<Post> initSpecification() {
        return (Specification<Post>) (root, query, cb) -> cb.isNotNull(root.get("id"));
    }

    private Specification<Post> afterPostDate(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.greaterThan(root.get("date"), Long.valueOf(value));
    }

    private Specification<Post> beforePostDate(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.lessThan(root.get("date"), Long.valueOf(value));
    }

    private Specification<Post> likeName(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.like(root.get("appUser").get("name"), "%" + value + "%");
    }
//
//    private Specification<Post> equalsTag(String value) {
//        return (Specification<Post>) (root, query, cb) -> cb.equal(root.get("appUser.name"), Long.valueOf(value));
//    }
}
