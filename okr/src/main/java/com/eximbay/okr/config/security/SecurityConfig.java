package com.eximbay.okr.config.security;

import com.eximbay.okr.constant.*;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.handler.*;
import com.eximbay.okr.service.Interface.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.oauth2.client.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    private final Optional<CompanyDto> companyDto;
    private final MyUserDetailsService userDetailsService;
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private CustomLogOutSuccessHandler logOutSuccessHandler;

    public SecurityConfig(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
                          MyUserDetailsService userDetailsService,
                          ICompanyService companyService,
                          CustomLogOutSuccessHandler logOutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.companyDto = companyService.getCompany();
        this.logOutSuccessHandler = logOutSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/oauth2/**", "/").permitAll()
                .antMatchers("/assets/**", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .logoutSuccessHandler(logOutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");

        if (this.companyDto.isPresent() && this.companyDto.get().getGoogleSignIn().equals(FlagOption.Y)){
            http    .oauth2Login()
                    .successHandler(oAuth2LoginSuccessHandler)
                    .loginPage("/login")
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .authorizedClientService(authorizedClientService());
        }
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = getRegistration("google");
        return new InMemoryClientRegistrationRepository(registration);
    }

    private ClientRegistration getRegistration(String client) {
        String clientId = this.companyDto.get().getGoogleClientId();
        String clientSecret = this.companyDto.get().getGoogleClientSecretKey();
        return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(clientId).clientSecret(clientSecret).build();
    }

    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

}
