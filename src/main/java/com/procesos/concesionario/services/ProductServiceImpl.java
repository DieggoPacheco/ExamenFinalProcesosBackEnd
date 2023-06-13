package com.procesos.concesionario.services;

import com.procesos.concesionario.models.Product;
import com.procesos.concesionario.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> createAllProducts(){
        RestTemplate restTemplate = new RestTemplate();
        Product[] productosExternos = restTemplate.getForObject("https://fakestoreapi.com/products", Product[].class);
        List<Product> productosLocales = new ArrayList<>();
        for (Product productoExterno : productosExternos) {

            Product productoLocal = new Product();
            productoLocal.setTitle(productoExterno.getTitle());
            productoLocal.setCategory(productoExterno.getCategory());
            productoLocal.setPrice(productoExterno.getPrice());
            productoLocal.setDescription(productoExterno.getDescription());
            productoLocal.setImage(productoExterno.getImage());
            productoLocal.setRating(productoExterno.getRating());

            productosLocales.add(productRepository.save(productoLocal));
        }
        return productosLocales;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }

    public Product updateProduct (Long id, Product product){
        Product productDB = productRepository.findById(id).get();
        productDB.setTitle(product.getTitle());
        productDB.setPrice(product.getPrice());
        productDB.setCategory(product.getCategory());
        productDB.setDescription(product.getDescription());
        productDB.setImage(product.getImage());
        productDB.setRating(product.getRating());
        productDB.setUser(product.getUser());

        return productRepository.save(productDB);
    }

}
