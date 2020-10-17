package br.com.santander.icheffv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/", method=RequestMethod.GET)
public class HomeController {
	
	public String start() {
		return "index";
	}

}