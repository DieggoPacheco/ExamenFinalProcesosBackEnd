package com.procesos.concesionario.services;

import com.procesos.concesionario.models.Product;
import java.util.List;
public interface ProductService {

    List<Product> createAllProducts();

    Product createProduct(Product product);

    List<Product> getAll();

    Product getProductById (Long id);

    Product updateProduct(Long id,Product product);


}
