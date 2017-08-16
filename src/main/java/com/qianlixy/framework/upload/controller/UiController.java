package com.qianlixy.framework.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front")
public class UiController {

	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
}
