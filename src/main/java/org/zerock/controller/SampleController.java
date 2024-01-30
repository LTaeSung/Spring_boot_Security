package org.zerock.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class SampleController {
	@GetMapping("/")
	public String index() {
		log.info("index");
		return "index";
	}
	@RequestMapping("/guest")
	public void guest() {
		log.info("guest");
	}
	@RequestMapping("/manager")
	public void manager() {
		log.info("manager");
	}
	@RequestMapping("/admin")
	public void admin() {
		log.info("admin");
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping("/adminSecret")
	public void adminSecret() {
		log.info("adminSecret");
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@RequestMapping("/adminSecret2")
	public void adminSecret2() {
		log.info("adminSecret");
	}
}
