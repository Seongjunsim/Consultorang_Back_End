package com.hungry.consultorang.config;


import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.rest.login.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    final JwtTokenProvider jwtTokenProvider;
    final String tokenName = "Authorization";
    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //3 & 4 way handshake 가능 할 수 있도록록
        if(request.getMethod().toLowerCase().equals("options")){
            return true;
        }
        if(request.getHeader(tokenName) == null)
            throw new LoginException("비정상적인 접근입니다.");
        String auth = request.getHeader(tokenName);
        String token = auth.replace("Bearer ", "");

        if(jwtTokenProvider.validateToken(token))
            return true;

        throw new LoginException("비정상적인 접근입니다.");
    }
}
