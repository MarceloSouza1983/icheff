package br.com.santander.icheffv1.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.santander.icheffv1.model.Newsletter;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.Response;
import br.com.santander.icheffv1.service.NewsletterService;
import br.com.santander.icheffv1.service.ReceitaService;

@RestController
@RequestMapping("/newsletter")
public class NewsletterController {
	
	private final NewsletterService newsletterService;

	public NewsletterController(NewsletterService newsletterService) {
		this.newsletterService = newsletterService;
	}

	@GetMapping(value="/all")
	public ResponseEntity<List<Newsletter>> findAll() {
		
		List<Newsletter> newsletter = this.newsletterService.findAll();
		
		return ResponseEntity.ok(newsletter);
	}
	
	/* @GetMapping(value="/all")
	public Response getResource() {
		Response response = new Response("Done", listaNewsletter);
		
		return response;
	} */
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody Newsletter newsletter) {
		
		newsletter = this.newsletterService.save(newsletter);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(newsletter.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/*
	@PostMapping(value="/save")
	public Response postNewsletter(@RequestBody Newsletter newsletter) {
		listaNewsletter.add(newsletter);
		
		Response response = new Response("Done", newsletter);
		return response;
	} */
	
}