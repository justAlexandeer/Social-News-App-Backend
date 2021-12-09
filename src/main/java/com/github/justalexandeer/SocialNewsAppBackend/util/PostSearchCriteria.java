package com.github.justalexandeer.SocialNewsAppBackend.util;

public enum PostSearchCriteria {
    AFTER_POST_DATE,
    BEFORE_POST_DATE,
    NAME_AUTHOR,
    ID_CATEGORY,
    ID_TAG,
    POST_NAME,
    POST_CONTENT;

    public static PostSearchCriteria fromParamStringToPostSearchCriteria(String param) {
        switch (param) {
            case "afterPostDate":
                return AFTER_POST_DATE;
            case "beforePostDate":
                return BEFORE_POST_DATE;
            case "nameAuthor":
                return NAME_AUTHOR;
            case "idCategory":
                return ID_CATEGORY;
            case "idTag":
                return ID_TAG;
            case "postName":
                return POST_NAME;
            case "postContent":
                return POST_CONTENT;
            default:
                return null;
        }
    }
}
