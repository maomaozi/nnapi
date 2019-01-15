package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.model.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public GeneralResponse root() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        return new GeneralResponse(true, "ok");
    }
}
