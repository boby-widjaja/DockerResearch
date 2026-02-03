package com.basiliskSB.configuration;
import com.basiliskSB.component.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration{

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**");
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((auth) -> {
            //auth.requestMatchers("/api/**").permitAll(); //Comment ini untuk mengembalikan security API
            auth.requestMatchers("/api/account/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/dashboard/**", "/api/category/**", "/api/product/**", "/api/region/**", "/api/salesman/**")
                .hasAnyAuthority("Administrator", "Salesman", "Finance");
            auth.requestMatchers(HttpMethod.GET, "/api/customer/**")
                .hasAnyAuthority("Administrator", "Salesman");
            auth.requestMatchers(HttpMethod.GET, "/api/order/**").
                hasAnyAuthority("Administrator", "Finance");
            auth.requestMatchers(HttpMethod.GET, "/api/supplier/**", "/api/delivery/**")
                .hasAuthority("Administrator");
            auth.requestMatchers(HttpMethod.POST, "/api/product/**", "/api/customer/**")
                .hasAnyAuthority("Administrator", "Salesman");
            auth.requestMatchers(HttpMethod.POST, "/api/order/**")
                .hasAnyAuthority("Administrator", "Finance");
            auth.requestMatchers(HttpMethod.POST, "/api/category/**", "/api/supplier/**", "/api/delivery/**", "/api/salesman/**")
                .hasAuthority("Administrator");
            auth.requestMatchers(HttpMethod.PUT, "/api/product/**", "/api/customer/**")
                .hasAnyAuthority("Administrator", "Salesman");
            auth.requestMatchers(HttpMethod.PUT, "/api/order/**")
                .hasAnyAuthority("Administrator", "Finance");
            auth.requestMatchers(HttpMethod.PUT, "/api/category/**", "/api/supplier/**", "/api/delivery/**", "/api/salesman/**")
                .hasAuthority("Administrator");
            auth.requestMatchers(HttpMethod.PATCH, "/api/product/**", "/api/customer/**")
                .hasAnyAuthority("Administrator", "Salesman");
            auth.requestMatchers(HttpMethod.PATCH, "/api/order/**")
                .hasAnyAuthority("Administrator", "Finance");
            auth.requestMatchers(HttpMethod.PATCH, "/api/category/**", "/api/supplier/**", "/api/delivery/**", "/api/salesman/**")
                .hasAuthority("Administrator");
            auth.requestMatchers(HttpMethod.DELETE, "/api/product/**", "/api/customer/**")
                .hasAnyAuthority("Administrator", "Salesman");
            auth.requestMatchers(HttpMethod.DELETE, "/api/order/**")
                .hasAnyAuthority("Administrator", "Finance");
            auth.requestMatchers(HttpMethod.DELETE, "/api/category/**", "/api/supplier/**", "/api/delivery/**", "/api/salesman/**")
                .hasAuthority("Administrator");
            auth.anyRequest().authenticated();
        });
        http.cors(request -> request.configurationSource(corsConfigurationSource()));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(entry -> entry.authenticationEntryPoint(authenticationEntryPoint()));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "PATCH", "DELETE"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access Denied");
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized request header");
        };
    }
}
