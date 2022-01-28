package com.example.springbootpractice.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI 컨트롤러(주로 JSON(데이터)을 반환, 일반 Controller는 뷰 페이지를 반환함)
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello";
    }

}
