package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.model.GeneralResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public GeneralResponse root() {
        return new GeneralResponse(true, "ok");
    }
}
