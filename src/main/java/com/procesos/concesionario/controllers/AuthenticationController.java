package com.procesos.concesionario.controllers;

import com.procesos.concesionario.models.User;
import com.procesos.concesionario.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.procesos.concesionario.utils.ApiResponse;
import com.procesos.concesionario.utils.Constants;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserServiceImp userService;
    private ApiResponse apiResponse;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){
        try{
            apiResponse= new ApiResponse(Constants.USER_LOGIN,userService.login(user));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse(e.getMessage(),"");
            return new ResponseEntity(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

}
