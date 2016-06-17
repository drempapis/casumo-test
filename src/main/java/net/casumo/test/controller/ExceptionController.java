package net.casumo.test.controller;

import net.casumo.test.controller.response.ExceptionResponse;
import net.casumo.test.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public final static String CONTENT_TYPE = "Content-Type";

    @Autowired
    private JsonUtils jsonUtils;

    @ExceptionHandler(Exception.class)
    public void errorOccurred(final HttpServletRequest request, final HttpServletResponse response, final Exception e) {
        final ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        writeMessageToResponse(response, responseStatus.value(), e.getMessage());
    }

    private void writeMessageToResponse(final HttpServletResponse response, final HttpStatus status, final String message) {
        response.setStatus(status.value());
        response.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try (final PrintWriter responseWriter = response.getWriter()) {
            final ExceptionResponse exceptionResponse = new ExceptionResponse(status.toString(), message);
            responseWriter.println(jsonUtils.getObjectMapper().writeValueAsString(exceptionResponse));
            responseWriter.close();
        } catch (IOException e1) {
            logger.error("[writing-message-to-response-failed]", e1);
        }
    }
}
