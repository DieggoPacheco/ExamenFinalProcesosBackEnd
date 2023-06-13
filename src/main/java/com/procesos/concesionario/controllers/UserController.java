package com.procesos.concesionario.controllers;

import com.procesos.concesionario.models.User;
import com.procesos.concesionario.services.UserServiceImp;
import com.procesos.concesionario.utils.ApiResponse;
import com.procesos.concesionario.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceImp userService;
    private ApiResponse apiResponse;

    @GetMapping(value = "/{id}")
    public ResponseEntity getById (@PathVariable(name = "id") Long id) {

        try{
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND,userService.getUserById(id));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, e.getMessage());
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "")
    public ResponseEntity createUser(@RequestBody User user){
        try{
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED,userService.createUser(user));
            return new ResponseEntity(apiResponse, HttpStatus.CREATED);
        }catch (Exception e){
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_CREATED, e.getMessage());
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity getAllUser (){
        try{
            List<User> userList = userService.allUser();
            if (!userList.isEmpty()) {
                apiResponse = new ApiResponse(Constants.REGISTERS_FOUND, userList);
            } else {
                apiResponse = new ApiResponse(Constants.REGISTERS_NOT_FOUND, null);
            }
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch(Exception e){
            apiResponse = new ApiResponse(Constants.REGISTERS_NOT_FOUND, e.getMessage());
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping  (value = "/{id}")
    public ResponseEntity editUser (@PathVariable(name = "id") Long id,@RequestBody User user){
        Map response= new HashMap();
        try{
            apiResponse = new ApiResponse(Constants.REGISTER_UPDATED, userService.updateUser(id, user));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_UPDATED, e.getMessage());
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
