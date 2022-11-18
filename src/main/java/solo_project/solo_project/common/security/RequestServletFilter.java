package solo_project.solo_project.common.security;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestServletFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (Objects.nonNull(request.getContentType()) &&
        request.getContentType().toLowerCase().contains("multipart/form-data")) {
      chain.doFilter(request, response);
    } else {
      RequestServletWrapper requestServletWrapper = new RequestServletWrapper(
          (HttpServletRequest) request);
      chain.doFilter(requestServletWrapper, response);
    }
  }

}
