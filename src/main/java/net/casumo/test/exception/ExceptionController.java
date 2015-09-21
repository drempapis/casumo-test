package net.casumo.test.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    public void errorOccurred(final HttpServletRequest request, final HttpServletResponse response, final Exception e) {
        final ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        writeMessageToResponse(response, responseStatus.value(), e.getMessage());
        logger.error("[Path]: " + request.getRequestURI() + " [Method]: " + request.getMethod() +
                     " [Exception]: " + e.getClass().getSimpleName() + " [Messasge]: " + e.getMessage());
    }

    private void writeMessageToResponse(final HttpServletResponse response, final HttpStatus status, final String message) {
        response.setStatus(status.value());
        response.setHeader("Content-Type", "application/json");

        try (final PrintWriter responseWriter = response.getWriter()) {
            responseWriter.println(convertToJson(status, message));
            responseWriter.close();
        } catch (IOException e1) {
            logger.error("[writing-message-to-response-failed]", e1);
        }
    }

    private String convertToJson(final HttpStatus status, final String message) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final ExceptionResponse response = new ExceptionResponse(status.toString(), message);
        return mapper.writeValueAsString(response);
    }

}
