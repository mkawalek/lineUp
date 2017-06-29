package agh.edu.pl.tai.lineup.infrastructure.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticatedToken extends AbstractAuthenticationToken {

    private final Object principal;

    AuthenticatedToken(Object principal) {
        super(null);

        if (principal == null)
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");

        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuthenticatedToken that = (AuthenticatedToken) o;

        return principal.equals(that.principal);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + principal.hashCode();
        return result;
    }

}