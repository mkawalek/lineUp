package agh.edu.pl.tai.lineup.domain.user;

import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import io.jsonwebtoken.Claims;

public interface TokenAuthenticator {

    public String provideToken(UserId userId);

    public Claims decodeToken(String token);

}
