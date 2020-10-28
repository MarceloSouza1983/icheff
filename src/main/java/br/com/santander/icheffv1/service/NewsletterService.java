package br.com.santander.icheffv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.model.Newsletter;
import br.com.santander.icheffv1.repository.NewsletterRepository;

@Service
public class NewsletterService {

	private final NewsletterRepository newsletterRepository;
	
	public NewsletterService(NewsletterRepository newsletterRepository) {
		this.newsletterRepository = newsletterRepository;
	}

	public Newsletter save(Newsletter newsletter) {
		newsletter.setId(null);
		return this.newsletterRepository.save(newsletter);
	}

	public List<Newsletter> findAll(){
		return this.newsletterRepository.findAll();
	}

}