package com.github.justalexandeer.SocialNewsAppBackend.util;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Tag;
import com.github.justalexandeer.SocialNewsAppBackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Протестировать класс
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
                    specification = specification.and(likeName(value));
                }
                break;
            }
            case ID_CATEGORY: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(equalIdCategory(value));
                }
                break;
            }
            case TAG: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(equalsTag(value));
                }
                break;
            }
            case TAGS_IN: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(tagsIn(value));
                }
                break;
            }
//            case TAGS_ALL: {
//                if (value != null && !value.isEmpty()) {
//                    specification = specification.and(tagsAll(value));
//                }
//                break;
//            }
            case POST_NAME: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(likePostName(value));
                }
                break;
            }
            case POST_CONTENT: {
                if (value != null && !value.isEmpty()) {
                    specification = specification.and(likePostContent(value));
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

    private Specification<Post> equalIdCategory(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.equal(root.get("category").get("id"), Long.valueOf(value));
    }

    private Specification<Post> likeName(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.like(root.get("appUser").get("name"), "%" + value + "%");
    }

    private Specification<Post> equalsTag(String value) {
        return (Specification<Post>) (root, query, cb) -> root.join("tags").get("id").in(Long.valueOf(value));
    }

//    private Specification<Post> tagsAll(String value) {
//
//    }

    private Specification<Post> tagsIn(String value) {
        List<String> listOfTagsId = new ArrayList(Arrays.asList(value.split(",")));
        return (Specification<Post>) (root, query, cb) -> root.join("tags").get("id").in(listOfTagsId);
    }

    private Specification<Post> likePostName(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.like(root.get("name"), "%" + value + "%");
    }

    private Specification<Post> likePostContent(String value) {
        return (Specification<Post>) (root, query, cb) -> cb.like(root.get("content"), "%" + value + "%");
    }

}
