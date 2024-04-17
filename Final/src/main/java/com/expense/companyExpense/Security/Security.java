package com.expense.companyExpense.Security;

import static org.springframework.security.config.Customizer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Security {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin= User.withUsername("Ritesh agarwal").password(passwordEncoder.encode("agarwal")).roles("ADMIN","USER").build();
        UserDetails user= User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build();
        UserDetails admin1 = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN","USER").build();
        return new InMemoryUserDetailsManager(admin,user,admin1);
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).httpBasic(withDefaults());
        http.authorizeHttpRequests(authorizeHttpRequests->authorizeHttpRequests.anyRequest().authenticated());
        return http.build();
    }
}
