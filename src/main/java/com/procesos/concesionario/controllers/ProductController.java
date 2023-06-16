package com.procesos.concesionario.controllers;

import com.procesos.concesionario.models.Product;
import com.procesos.concesionario.models.User;
import com.procesos.concesionario.services.ProductService;
import com.procesos.concesionario.services.UserServiceImp;
import com.procesos.concesionario.utils.TokenValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Product API")
public class ProductController {

    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserServiceImp userService;

    @PostMapping("/products")
    @Operation(summary = "consumir api externa",description  = "Aqui podemos consumir los datos que se tienen en una pagina web y los podemos almacenar en la DB local")
    public ResponseEntity createAllProducts(
            @Parameter(description = "Token de autenticación", required = true)
            /*@RequestHeader(value="Authorization")*/ String token) {
        Map response = new HashMap();
        try{
            if(!tokenValidator.validateToken(token)){
                return new ResponseEntity("token invalido", HttpStatus.UNAUTHORIZED);
            }
            response.put("message","Producto consimidos correctamente en la BD");
            response.put("data",productService.createAllProducts());
            return new ResponseEntity(response, HttpStatus.CREATED);
        }catch (Exception e){
            response.put("message","Error al consumir los producto");
            response.put("data",e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/products")
    @Operation(summary = "Obtener todos los productos",description  = "aqui podemos obtener todos los productos registrados en la base de datos")
    public ResponseEntity getAll(
            @Parameter(description = "Token de autenticación", required = true)
            /*@RequestHeader(value="Authorization")*/ String token) {
        Map response = new HashMap();
        try {
            if(!tokenValidator.validateToken(token)){
                return new ResponseEntity("token invalido", HttpStatus.UNAUTHORIZED);
            }
            List<Product> productList = productService.getAll();
            if (productList.size() > 0) {
                response.put("message", "se encontraron los productos");
                response.put("data", productService.getAll());
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                response.put("message", "no se encontraron los productos");
                response.put("data", null);
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "no se encontraron los productos");
            response.put("data", null);//userService.getUserById(id) //Optional.Empty //"e.getMessage()"
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/product")
    @Operation(summary = "Crear nuevos productos",description  = "aqui podemos crear nuevos productos y guardarlos en la base de datos local")
    public ResponseEntity createProduct(@RequestBody Product product,@Parameter(description = "Token de autenticación", required = true)/*@RequestHeader(value="Authorization")*/ String token){
        Map response = new HashMap();
        try{
            if(!tokenValidator.validateToken(token)){
                return new ResponseEntity("token invalido", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserById(product.getUser().getId());// Obtener el usuario correspondiente al producto
            product.setUser(user);// Asignar el usuario al producto

            response.put("message","Producto creado correctamente");
            response.put("data",productService.createProduct(product));
            return new ResponseEntity(response, HttpStatus.CREATED);
        }catch (Exception e){
            response.put("message","Error al crear producto");
            response.put("data",e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/products/{id}")
    @Operation(summary = "obtener producto por id",description  = "aqui podemos obtener un producto atravez de ID solicitado en la base de datos local")
    public ResponseEntity getProduc(@PathVariable(name = "id")Long id,@Parameter(description = "Token de autenticación", required = true)/*@RequestHeader(value="Authorization")*/ String token){
        Map response = new HashMap();
        try{
            if(!tokenValidator.validateToken(token)){
                return new ResponseEntity("token invalido", HttpStatus.UNAUTHORIZED);
            }
            response.put("mensaje", "se encontro el producto");
            response.put("data", productService.getProductById(id));
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("mensaje","error al buscar el producto");
            response.put("data",e.getMessage());
            return new ResponseEntity(response,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/products/{id}")
    @Operation(summary = "modificar un producto en especifico",description  = "sirve para modificar algun producto ya creado en la base de datos")
    public ResponseEntity updateProduct(@PathVariable(name = "id") Long id,@Parameter(description = "Token de autenticación", required = true) @RequestBody Product product,/*@RequestHeader(value="Authorization")*/ String token){
        Map response = new HashMap();
        try{
            if(!tokenValidator.validateToken(token)){
                return new ResponseEntity("token invalido", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserById(product.getUser().getId());// Obtener el usuario correspondiente al producto
            product.setUser(user);// Asignar el usuario al producto
            response.put("message", "producto actualizado correctamente");
            response.put("data", productService.updateProduct(id, product));
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("message","El producto no se encontro");
            response.put("data",id);
            return new ResponseEntity(response,HttpStatus.NOT_FOUND);
        }
    }
}