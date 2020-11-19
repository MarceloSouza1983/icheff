package br.com.santander.icheffv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.santander.icheffv1.model.Dashboard;
import br.com.santander.icheffv1.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<Dashboard> getDados(){
		
		Dashboard dashboard = dashboardService.dashboardBuilder();
		
		return ResponseEntity.ok(dashboard);
		
	} 
	
}
