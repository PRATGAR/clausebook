package com.acme.contracts.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import org.springframework.web.filter.OncePerRequestFilter;

public class SearchTermFilter extends OncePerRequestFilter {

    private static final Pattern UNSUPPORTED = Pattern.compile("['\";\\\\]|--|/\\*");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String[] terms = request.getParameterValues("q");
        if (terms != null) {
            for (String term : terms) {
                if (UNSUPPORTED.matcher(term).find()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported characters in search term");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
