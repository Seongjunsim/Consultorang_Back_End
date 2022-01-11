package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.config.EnvSet;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    EnvSet envSet;

    public JwtTokenProvider(EnvSet envSet) {
        this.envSet = envSet;
    }

    public String createToken(String userId) throws Exception {
        return null;
    }

    public boolean validateToken(String token){
        return true;
    }

}
