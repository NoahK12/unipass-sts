package com.pro.unipass.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/java")
	public static void main(String[] args) {
		System.out.println("helloWorld!!!");
	}
	
    @RequestMapping("/web")
    public String reHello() {
        return "helloword!!!!!!!!!!!!!!!!!!!!!";
    }
}
