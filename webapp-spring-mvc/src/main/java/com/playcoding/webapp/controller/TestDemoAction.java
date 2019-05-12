package com.playcoding.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playcoding.webapp.bean.TestDemoBean;
import com.playcoding.webapp.service.TestDemoService;

@RequestMapping("web")
@Controller
public class TestDemoAction {

	@Autowired
	TestDemoService service;

	/**
	 * @return response a json
	 */
	@ResponseBody
	@RequestMapping("test")
	public TestDemoBean testAction() throws IOException {
		return new TestDemoBean(service.getString());
	}
	
	/**
	 * @return forward to a jsp page
	 */
	@RequestMapping("page")
	public String pageAction(Model m) {
		
		// put some attribute
		m.addAttribute("string", service.getString());
		
		return "page";
	}
	
}
