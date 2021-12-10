package com.github.justalexandeer.SocialNewsAppBackend.util;

public enum PostSearchCriteria {
    AFTER_POST_DATE,
    BEFORE_POST_DATE,
    NAME_AUTHOR,
    ID_CATEGORY,
    TAG,
    TAGS_IN,
//    TAGS_ALL,
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
            case "tag":
                return TAG;
            case "tagsIn":
                return TAGS_IN;
//            case "tagsAll":
//                return TAGS_ALL;
            case "postName":
                return POST_NAME;
            case "postContent":
                return POST_CONTENT;
            default:
                return null;
        }
    }
}
