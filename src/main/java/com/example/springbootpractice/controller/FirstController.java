package com.example.springbootpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/greetings") // 접속할 URL을 넣어주면 매핑해줌
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "김동"); //"김동"이라는 값을 username
        return "greetings";  //templates/greetings.mustache -> 브라우저로 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "닉네임");
        return "goodbye";
    }
}
