package com.java.Bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig {

   @Autowired
   Filter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Bean
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        final AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/auth/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users/createUser").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/{id}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/email/{email}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users/updateUser").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/users/deleteUser/{id}").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/accounts/AddAccount/{userId}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/accounts/user/{username}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/accounts/{type}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/accounts/deleteAccount/{id}").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/transactions/deposit").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/transactions/withdraw").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/transactions/transfer").hasRole("USER");


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }



}