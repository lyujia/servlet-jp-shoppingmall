package com.nhnacademy.nhnpage.interceptor;

import com.nhnacademy.nhnpage.exception.NotLoginException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SESSIONNHN")&& Objects.equals(request.getSession().getId(), cookie.getValue())) {
                    System.out.println("request세션 아디디" + request.getSession().getId());
                    return true;
                }
            }
        }
        System.out.println("로그인 상태가 아닙니다.");
        throw new NotLoginException();
    }
}
