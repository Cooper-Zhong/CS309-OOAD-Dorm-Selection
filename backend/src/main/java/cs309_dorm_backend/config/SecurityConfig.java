package cs309_dorm_backend.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Allow access from specific IPs
                .antMatchers("/**").hasIpAddress("10.32.44.222") // Single IP
                .antMatchers("/**").hasIpAddress("10.26.142.84") // Single IP
//                .antMatchers("/**").hasIpAddress("10.32.60.95") // Single IP
//                .antMatchers("/**").hasIpAddress("127.0.0.1") // Single IP
                .antMatchers("/**").permitAll()
//                .antMatchers("/**").anonymous() // Allow anonymous access
                .anyRequest().authenticated()
                .and()
            // Other configurations like form login, CSRF protection, etc.
            .formLogin()
                .and()
            .csrf().disable(); // Disabling CSRF for simplicity (consider enabling it in production)
    }
}
