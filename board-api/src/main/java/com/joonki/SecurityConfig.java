package com.joonki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.joonki.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    CustomUserDetailsService userDetailsService;
	
	@Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration               config = new CorsConfiguration();

        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        source.registerCorsConfiguration("/**", config);
        
        http
        	.addFilterBefore(new CorsFilter(source), LogoutFilter.class)
            .authorizeRequests()
            	.antMatchers("/signup").permitAll()
                //.anyRequest().authenticated()
            	.anyRequest().permitAll()
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(authenticationEntryPoint)
            	.and()
            .csrf()
            	.disable()
            .formLogin()
                //.loginPage("/login")
                //.permitAll();
            	.successHandler(authenticationSuccessHandler)
            	.failureHandler(authenticationFailureHandler);
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }
}