package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.model.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public ResponseEntity<GeneralResponse> root() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<>(new GeneralResponse(true, "ok"), headers, HttpStatus.OK);
    }
}
