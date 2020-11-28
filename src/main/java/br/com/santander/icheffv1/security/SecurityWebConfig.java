package br.com.santander.icheffv1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.santander.icheffv1.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl usuarioService;
	private JwtUtil jwtUtil;

	@Autowired
	public SecurityWebConfig(UserDetailsServiceImpl usuarioService, JwtUtil jwtUti) {
		this.usuarioService = usuarioService;
		this.jwtUtil = jwtUti;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		
			.antMatchers(
					"/",
					"/csrf",
					"/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources/**",
					"/configuration/**",
					"/swagger-ui.html",
					"/webjars/**"
			).permitAll()
			
			.antMatchers(HttpMethod.GET,
					"/favicon.ico",
					"/css/**",
					"/img/**",
					"/imagens/**",
					"/js/**"
			).permitAll()
			
			.antMatchers(HttpMethod.GET,
					"/*.html",
					"/api/receitas"
			).permitAll()
			
			.antMatchers(HttpMethod.POST,
					"/api/login",
					"/api/newsletter",
					"/api/usuarios/save"
			).permitAll()
			
			.antMatchers(HttpMethod.POST, "/api/vendas").hasAnyRole("admin","usuario")
			.antMatchers(HttpMethod.PUT, "/api/pagamento").hasAnyRole("admin","usuario")
			
			.antMatchers(HttpMethod.GET, "/api/**").hasRole("admin")
			.antMatchers(HttpMethod.POST, "/api/**").hasRole("admin")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("admin")
			
			.anyRequest().authenticated()
			
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and().cors().disable()
				  .csrf().disable()
			
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil));
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder());

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}