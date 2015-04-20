
package com.dr.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter
{
    public static final String PASSWORD = "guest";

    @Autowired
    EmailFormatValidator emailFormatValidator;

    @Override
    public void init (AuthenticationManagerBuilder auth)
        throws Exception
    {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService ()
    {
        return new UserDetailsService()
        {
            @Override
            public UserDetails loadUserByUsername (String username)
                throws UsernameNotFoundException
            {
                if (emailFormatValidator.validate(username)) {
                    return new User(username,
                                    PASSWORD,
                                    true,
                                    true,
                                    true,
                                    true,
                                    AuthorityUtils.createAuthorityList("USER"));
                }
                else {
                    throw new BadCredentialsException(username
                            + " is not a valid user email.");
                }
            }
        };
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure (HttpSecurity http)
        throws Exception
    {
        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/");

        http.csrf().disable();
    }
}
