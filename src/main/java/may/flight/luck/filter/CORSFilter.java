package may.flight.luck.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CORSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String host = httpServletRequest.getHeader("host");
        String origin = httpServletRequest.getHeader("Origin");
        if (StringUtils.isBlank(host)) {
            return;
        }

        if (host.contains("127.0.0.1")
                || host.contains("localhost")) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.setHeader("Access-Control-Allow-Credentials ", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
