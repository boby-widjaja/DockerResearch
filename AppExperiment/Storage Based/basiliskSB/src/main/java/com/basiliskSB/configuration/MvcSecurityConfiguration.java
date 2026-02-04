package com.basiliskSB.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration{

	@Bean
    @Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((request) -> {
            request.requestMatchers("/resources/**", "/account/**").permitAll();
            request.requestMatchers("/delivery/**",
                    "/category/upsertForm",
                    "/category/delete",
                    "/region/upsertForm",
                    "/region/delete",
                    "/region/assignDetailForm",
                    "/region/deleteDetail",
                    "/salesman/upsertForm",
                    "/salesman/delete",
                    "/salesman/assignDetailForm",
                    "/salesman/deleteDetail").hasAuthority("Administrator");
            request.requestMatchers("/customer/**",
                    "/static/resources/image/product/upsertForm",
                    "/static/resources/image/product/delete").hasAnyAuthority("Administrator", "Salesman");
            request.requestMatchers("/order/**").hasAnyAuthority("Administrator", "Finance");
            request.anyRequest().authenticated();
        });
        http.formLogin((form) -> {
            form.loginPage("/account/loginForm");
            form.failureUrl("/account/failLogin");
            form.loginProcessingUrl("/authenticating");
        });
        http.rememberMe((context) -> {
            context.key("liberate-tututme-ex-inferis-ad-astra-per-aspera");
            context.rememberMeCookieName("remember-me-cookie");
            context.tokenValiditySeconds(86400);
        });
        http.logout((logout) -> {
            logout.logoutUrl("/logout");
            logout.deleteCookies("JSESSIONID", "remember-me-cookie");
            logout.permitAll();
        });
        http.exceptionHandling(exception -> exception.accessDeniedPage("/account/accessDenied"));
		return http.build();
	}
}
