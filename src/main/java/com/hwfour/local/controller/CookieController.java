package com.hwfour.local.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//RequestParam을 사용할 것이기에 뒤에 {} 없음
@RestController
@RequestMapping("/api/v1/cookie")
public class CookieController {
    @GetMapping("/setCookie")
    public void createCookie(HttpServletRequest request, HttpServletResponse response, @RequestParam("studentName") String studentName, @RequestParam("subject") String subject) {
        //일단 모든 쿠키를 가져온다.
        Cookie[] cookie = request.getCookies();
        String cart = "";
        //처음 담는 거라면 새롭게 쿠키
        if(cookie == null) {
            Cookie rememberCookie = new Cookie(studentName, subject); //쿠키 객체 생성
            rememberCookie.setPath("/"); //쿠키가 적용될 url path를 지정한다.
            rememberCookie.setMaxAge(60*60*24*30); // 쿠키 만료 : 30일
            rememberCookie.setDomain("localhost");
            response.addCookie(rememberCookie);

            System.out.println("set Cookie");
        } else { // 기존 쿠키들이 있음
            for(int i= 0; i<cookie.length; i++) {
                if(cookie[i].getName().equals(studentName)) { //하지만 기존 쿠키 중에서 찾는 값이 있음
                    String addSubject = cookie[i].getValue();
                    cart += addSubject + "/";
                    cart += subject;
                    Cookie cartCookie = new Cookie(studentName, cart);
                    cartCookie.setPath("/"); //쿠키가 적용될 url path를 지정한다.
                    cartCookie.setMaxAge(60*60*24*30); // 쿠키 만료 : 30일
                    cartCookie.setDomain("localhost");
                    response.addCookie(cartCookie);
                }
            }
        }
    }

    @RequestMapping("/getCookie")
    public void getCookie(HttpServletRequest request, @RequestParam("studentName") String studentName ) {
        Cookie[] cookie = request.getCookies();

        for(int i= 0; i<cookie.length; i++) {
            if(cookie[i].getName().equals(studentName)) {
                System.out.println(cookie[i].getValue());
            }
        }
    }

    @RequestMapping("/removeCookie")
    public void removeCookie(HttpServletResponse response, @RequestParam String removeCookieName) {
        Cookie newCookie = new Cookie(removeCookieName, null);
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);
    }

}
