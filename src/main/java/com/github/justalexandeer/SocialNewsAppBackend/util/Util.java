package com.github.justalexandeer.SocialNewsAppBackend.util;

public class Util {
    public static String getSecretKey() {
        return System.getenv("secretkey");
    }
}
