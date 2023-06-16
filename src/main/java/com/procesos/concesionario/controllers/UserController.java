package com.procesos.concesionario.controllers;

import com.procesos.concesionario.models.User;
import com.procesos.concesionario.services.UserServiceImp;
import com.procesos.concesionario.utils.ApiResponse;
import com.procesos.concesionario.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User API")
public class UserController {
    @Autowired
    private UserServiceImp userService;
    private ApiResponse apiResponse;

    @GetMapping(value = "/{id}")
    @Operation(summary = "obtener usuario por ID",description  = "sirve para obtener un solo usuario por id , que este en la base de datos local")
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
    @Operation(summary = "Crear un nuevo usuario",description  = "sirve para crear un usuario nuevo y almacenarlo en la DB local")
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
    @Operation(summary = "obtener todos los usuarios",description  = "sirve para obtener todos los usuarios registrados en la DB local")
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
    @Operation(summary = "modificar usuario por ID",description  = "sirve para modificar un usuario antes registrado atraves de su ID")
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
