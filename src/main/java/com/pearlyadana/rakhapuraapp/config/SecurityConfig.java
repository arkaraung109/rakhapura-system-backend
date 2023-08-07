package com.pearlyadana.rakhapuraapp.config;

import com.pearlyadana.rakhapuraapp.http.AuthenticationEntryPointImpl;
import com.pearlyadana.rakhapuraapp.http.JWTConfigurer;
import com.pearlyadana.rakhapuraapp.http.TokenProvider;
import com.pearlyadana.rakhapuraapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
@Profile({"uat","prod"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private CorsFilter corsFilter;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPointImpl;

	@PostConstruct
	private void init(){
		System.out.println("Security Configrations are enabled");
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(11);
	}
	
	@Override
    public void configure(WebSecurity web) {
        web.ignoring()
        	.antMatchers(HttpMethod.GET,"/profile/**")
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers(HttpMethod.POST, "/api/v1/authenticate")
            .antMatchers("/HOME/**");
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/api/v1/authenticate").permitAll()
		.anyRequest().authenticated();
	
		http
        .csrf()
        .disable()
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPointImpl)
            .accessDeniedHandler(this.accessDeniedHandler)
	    .and()
	       .headers()
	       //ref: https://developers.google.com/web/fundamentals/security/csp/
	        .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
	    .and()
	        .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
	    .and()
	        .frameOptions()
	        .deny()
	    .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    .and()
	        
        .httpBasic()
        .and()
        .apply(this.securityConfigurerAdapter());
	}

	@Override 
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(this.tokenProvider);
    }
}
