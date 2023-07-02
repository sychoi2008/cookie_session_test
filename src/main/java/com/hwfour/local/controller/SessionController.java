package com.hwfour.local.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    //List 전역변수로 지정해서 추가될 때마다 넣자
    List<String> arr = new ArrayList<>();

    @GetMapping("/setSession")
    public void createSession(HttpServletRequest request, @RequestParam("studentName") String studentName, @RequestParam("subject")String subject) {
        //왜 하는 거지
        HttpSession session = request.getSession();
        arr.add(subject);
        session.setAttribute(studentName, arr);
        System.out.println("make session");
        System.out.println(session.getMaxInactiveInterval());
    }

    @GetMapping("/getSession")
    public void getSession(HttpServletRequest request, @RequestParam("studentName") String studentName) {
        HttpSession session = request.getSession(false);
        ArrayList subjects = (ArrayList) session.getAttribute(studentName);

        subjects.forEach(sub -> System.out.println(sub));
    }

    //세션 초기화
    @GetMapping("removeSession")
    public void removeSession(HttpServletRequest request ) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
