package agh.edu.pl.tai.lineup.infrastructure.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

class TokenAuthenticationFilter extends GenericFilterBean {

    private AuthenticationProvider authenticationProvider;

    public TokenAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
