package com.example.SpringAPI.utilities;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookiesManager {
    public static void setCookie(HttpServletResponse response, String cookieName, String dataToInject) {
        // Create an HTTP-only cookie and set the information
        Cookie cookie = new Cookie(cookieName, dataToInject);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static String getCookie(String cookieValue) {
        return cookieValue;
    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,String cookieName){
        Cookie deleteCookie = new Cookie(cookieName, null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setHttpOnly(true);
        response.addCookie(deleteCookie);
    }
}
