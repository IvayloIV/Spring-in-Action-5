package sia.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
             .antMatchers("/design/show", "/order", "/order/current").access("hasRole('ROLE_USER')")
             .antMatchers("/", "/**").permitAll()
           .and()
            .formLogin()
             .loginPage("/login")
             .usernameParameter("user")
             .passwordParameter("pass")
             .defaultSuccessUrl("/", true) // redirect to hame page independently from where it comes
           .and()
            .logout()
             .logoutUrl("/user/logout")
             .logoutSuccessUrl("/");
        //    .and()
        //     .csrf()
        //      .disable(); 
    }
}
