package sia.tacocloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .anyRequest().fullyAuthenticated()
        .and()
      .formLogin();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .contextSource()
          .url("ldap://localhost:8389/dc=tacocloud,dc=com")
          .and()
        .passwordCompare()
          .passwordEncoder(NoOpPasswordEncoder.getInstance())
          .passwordAttribute("userPassword");
  }
}
