package vn.iotstar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
	@GetMapping("/")
	public String index(Model m) {

		return "index";
	}
}
