package com.epam.util;

import org.springframework.web.multipart.MultipartFile;

public class ValidateParam {

    private ValidateParam() {}

    public static boolean isNotValid(String ids) {
        return ids.length() >= 200;
    }

}
