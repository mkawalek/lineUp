package agh.edu.pl.tai.lineup.infrastructure.security;

import agh.edu.pl.tai.lineup.api.security.AuthenticatedUser;
import agh.edu.pl.tai.lineup.domain.user.TokenAuthenticator;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import org.apache.http.auth.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class TokenAuthenticationFilter extends GenericFilterBean {
    private static final String HEADER_STRING = "Authorization";

    private TokenAuthenticator tokenAuthenticator;

    TokenAuthenticationFilter(TokenAuthenticator tokenAuthenticator) {
        this.tokenAuthenticator = tokenAuthenticator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = Optional.ofNullable(asHttp(request).getHeader(HEADER_STRING)).orElseThrow(() -> new AuthenticationException("No token provided"));
            Optional<UserId> userIdFromToken = Optional.ofNullable(UserId.of(tokenAuthenticator.decodeToken(token).getSubject()));

            if (!userIdFromToken.isPresent()) throw new AuthenticationException("Validation Error during decoding token");
            else SecurityContextHolder.getContext().setAuthentication(new AuthenticatedToken(new AuthenticatedUser(userIdFromToken.get())));

            chain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            asHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}
