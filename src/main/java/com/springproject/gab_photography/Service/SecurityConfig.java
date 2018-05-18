package com.springproject.gab_photography.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("root").password("{noop}root").roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/uploadFile","/images").access("hasRole(\"ADMIN\")")
                .and()
                .formLogin().loginPage("/loginForm").failureUrl("/loginForm")
                .usernameParameter("username").passwordParameter("password")
                .permitAll().and().logout().permitAll();

    }
}