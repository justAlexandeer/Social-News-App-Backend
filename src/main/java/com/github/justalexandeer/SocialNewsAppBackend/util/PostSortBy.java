package com.github.justalexandeer.SocialNewsAppBackend.util;

import org.springframework.data.domain.Sort;

// Протестировать класс
public enum PostSortBy {
    SORT_BY_DATE_ASCENDING,
    SORT_BY_DATE_DESCENDING,
    SORT_BY_AUTHOR_NAME_ASCENDING,
    SORT_BY_AUTHOR_NAME_DESCENDING,
    SORT_BY_CATEGORY_NAME_ASCENDING,
    SORT_BY_CATEGORY_NAME_DESCENDING;

    public static PostSortBy fromParamStringToPostSortBy(String param) {
        switch (param) {
            case "date_ascending":
                return SORT_BY_DATE_ASCENDING;
            case "date_descending":
                return SORT_BY_DATE_DESCENDING;
            case "author_name_ascending":
                return SORT_BY_AUTHOR_NAME_ASCENDING;
            case "author_name_descending":
                return SORT_BY_AUTHOR_NAME_DESCENDING;
            case "category_name_ascending":
                return SORT_BY_CATEGORY_NAME_ASCENDING;
            case "category_name_descending":
                return SORT_BY_CATEGORY_NAME_DESCENDING;
            case "default":
                return SORT_BY_DATE_DESCENDING;
            default:
                return SORT_BY_DATE_DESCENDING;
        }
    }

    public static Sort fromPostSortByToSort(PostSortBy postSortBy) {
        switch (postSortBy) {
            case SORT_BY_DATE_ASCENDING:
                return Sort.by("date").ascending();
            case SORT_BY_DATE_DESCENDING:
                return Sort.by("date").descending();
            case SORT_BY_AUTHOR_NAME_ASCENDING:
                return Sort.by("appUser.username").ascending();
            case SORT_BY_AUTHOR_NAME_DESCENDING:
                return Sort.by("appUser.username").descending();
            case SORT_BY_CATEGORY_NAME_ASCENDING:
                return Sort.by("category.name").ascending();
            case SORT_BY_CATEGORY_NAME_DESCENDING:
                return Sort.by("category.name").descending();
            default:
                return null;
        }
    }

}
