package com.valuewith.tweaver.constants;

import lombok.Getter;

@Getter
public enum ImageType {

    PROFILE("profile/"),
    THUMBNAIL("thumbnail/"),
    LOCATION("location/"),
    MEMBER("member/"),
    POST("post/");

    private final String path;


    ImageType(String path) {
        this.path = path;
    }

}
