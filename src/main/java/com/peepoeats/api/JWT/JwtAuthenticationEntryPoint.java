package com.peepoeats.api.JWT;

import com.google.gson.Gson;
import com.peepoeats.api.exception.ExceptionDetailedResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    // handle unauthenticated access
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ExceptionDetailedResponse error = new ExceptionDetailedResponse(authException.getLocalizedMessage(),
                List.of("Please login to the app"));

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(error));
    }

}