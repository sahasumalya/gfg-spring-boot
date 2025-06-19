package org.example.gfgspringboot.configurations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gfgspringboot.repositories.UserRepository;
import org.example.gfgspringboot.requestFilters.CustomSessionValidationFilter;
import org.example.gfgspringboot.requestFilters.JwtRequestFilter;
import org.example.gfgspringboot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {





    @Autowired
    private UserRepository userRepository;
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
                        .authenticated())
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }*/

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->

                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/login", "get", true)).permitAll()
                                .anyRequest().authenticated()  // All other requests should be authenticated
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Configure the custom login page
                        .defaultSuccessUrl("/home", true)  // Redirect to home after successful login
                        .permitAll()  // Allow all to access the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")  // On logout, redirect to login with query parameter
                        .permitAll()  // Allow all to trigger logout
                );
        return http.build();
    }*/

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public CustomUserDetailsService userDetailsService() {
        /*UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("user1Pass"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();*/
        return new CustomUserDetailsService(userRepository);
    }
   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                .userDetailsService(userDetailsService())
                .authorizeRequests()
                .requestMatchers("/v1/users/**")
                .hasRole("LIBRARIAN")
                .requestMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }*/


    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf()
                 .disable()
                 .authorizeRequests(authorize -> authorize
                         .requestMatchers( "/", "/error", "/webjars/**", "/session-expired","/login/oauth2/code/github").permitAll() // Public pages
                         .anyRequest().authenticated() // All other pages require authentication
                 )
                 .logout(logout -> logout
                         .logoutUrl("/logout")
                         .logoutSuccessHandler(oidcLogoutSuccessHandler())
                         .invalidateHttpSession(true)
                         .deleteCookies("JSESSIONID")
                         .clearAuthentication(true)
                 )
                 .oauth2Login();


         return http.build();
    }*/

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setTargetUrlParameter("http://localhost:3000/session-expired");
        //successHandler.setDefaultTargetUrl("http://localhost:3000/session-expired");
        SecurityContextHolder.clearContext();// Replace with your redirect URL

        return successHandler;
    }

    //
    // eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJzdWIiOiJzYWhhc3VtYWx5YSIsIklzc3VlciI6Iklzc3VlciIsImV4cCI6MTc0OTk3OTM3MCwiaWF0IjoxNzQ5ODkyOTcwfQ.YE9Ra8Vz3CteBitnb9ovICYnShvdDZzX8-voMg6Aaic

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/invalidSession")
                        .invalidSessionStrategy(invalidSessionStrategy())
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        )

                //.userDetailsService(userDetailsService())
                .addFilterAfter(new JwtRequestFilter(new JwtUserDetailsService(), new JwtUtil()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomSessionValidationFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                /*.sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .invalidSessionUrl("/invalidSession")
                                .invalidSessionStrategy(invalidSessionStrategy())
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                )*/
                /*.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/session-expired")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "token")
                        .clearAuthentication(true)
                )*/

                //.userDetailsService(userDetailsService())
                //.addFilterAfter(new JwtRequestFilter(new JwtUserDetailsService(), new JwtUtil()), UsernamePasswordAuthenticationFilter.class)
               // .addFilterBefore(new CustomSessionValidationFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                .requestMatchers("/v1/users/login", "/v1/users/register", "/session-expired", "/invalidSession")
                .permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }


    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new SimpleRedirectInvalidSessionStrategy("/session-expired");
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    /*@Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return new SecurityContextHolderStrategy() {
            @Override
            public void clearContext() {

            }

            @Override
            public SecurityContext getContext() {
                return null;
            }

            @Override
            public void setContext(SecurityContext context) {

            }

            @Override
            public SecurityContext createEmptyContext() {
                return null;
            }
        }
    }*/

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic(withDefaults())
                .authorizeRequests()
                .requestMatchers("/login", "/home").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }*/


}