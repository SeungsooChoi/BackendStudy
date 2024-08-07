package com.example.demo.global.util;

import com.example.demo.global.constant.AuthConstant;
import io.jsonwebtoken.lang.Strings;
import org.springframework.util.StringUtils;

public class TokenUtil {
    public static String parsingToken(String authHeader){
        if(StringUtils.isEmpty(authHeader) || ! authHeader.startsWith(AuthConstant.TOKEN_TYPE)) return Strings.EMPTY;
        // String form is 'Bearer jwtTokenValue'
        String[] tokenSplit = authHeader.split(" ");
        return tokenSplit[1];
    }
}
