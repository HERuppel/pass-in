package rocketseat.com.passin.middlewares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rocketseat.com.passin.dto.auth.ConfirmAccountRequestDTO;
import rocketseat.com.passin.dto.auth.SignInRequestDTO;
import rocketseat.com.passin.dto.auth.SignUpRequestDTO;
import rocketseat.com.passin.dto.auth.validation.ConfirmAccountRequestValidator;
import rocketseat.com.passin.dto.auth.validation.SignInRequestValidator;
import rocketseat.com.passin.dto.auth.validation.SignUpRequestValidator;

@Component
public class RequestValidationInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(RequestValidationInterceptor.class);

    public RequestValidationInterceptor(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      logger.info(request.getRequestURI());
      if ("/auth/signup".equals(request.getRequestURI()) && HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
        SignUpRequestDTO body = objectMapper.readValue(request.getInputStream(), SignUpRequestDTO.class);

        SignUpRequestValidator.validate(body);
      }

      if ("/auth/signin".equals(request.getRequestURI()) && HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
        SignInRequestDTO body = objectMapper.readValue(request.getInputStream(), SignInRequestDTO.class);
        logger.info(body.toString());
        SignInRequestValidator.validate(body);
      }

      if ("/auth/confirm-account".equals(request.getRequestURI()) && HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
        ConfirmAccountRequestDTO body = objectMapper.readValue(request.getInputStream(), ConfirmAccountRequestDTO.class);
        ConfirmAccountRequestValidator.validate(body);
      }

      return true;
    }
}
