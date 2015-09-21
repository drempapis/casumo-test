package net.casumo.test.controller;

import net.casumo.test.exception.InternalErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ServerController {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String testPost() {
        return "done";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String testGet() {
        logger.debug(String.format("[testGet]:[/test]:[%s]", "test"));
        return "testGet Done!";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void error() {
        throw new InternalErrorException();
    }
}
