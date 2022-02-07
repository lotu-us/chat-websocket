package com.example.chat_websocket.controller;

import com.example.chat_websocket.domain.Member;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ChatController {

    @GetMapping("/")
    public String home(){
        return "home";
    }


    @PostMapping("/login/{userId}/{username}")
    @ResponseBody
    public ResponseEntity login(@PathVariable String userId, @PathVariable String username, HttpSession session){
        Member loginMember = new Member();
        loginMember.setUserId(userId);
        loginMember.setUsername(username);

        session.setAttribute("loginMember", loginMember);
        System.out.println("세션 저장");
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
