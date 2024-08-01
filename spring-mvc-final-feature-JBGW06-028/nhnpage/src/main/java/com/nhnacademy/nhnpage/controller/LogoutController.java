package com.nhnacademy.nhnpage.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @GetMapping("/cs/logout")
    public String logout(HttpServletRequest request) {
        request.getCookies();
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("SESSIONNHN")) {
                cookie.setValue(null);
            }
            request.getSession().invalidate();
        }
        return "redirect:/cs/login";
    }
    //쿠키세션을 널로 바꾼다.
    //리다이렉트로 로그인 창으로 간다.
}
