package com.estore.estoreapi;


import com.estore.estoreapi.entity.Product;
import com.estore.estoreapi.exceptionHandling.BadResourceException;
import com.estore.estoreapi.exceptionHandling.ResourceAlreadyExistsException;
import com.estore.estoreapi.exceptionHandling.ResourceNotFoundException;
import com.estore.estoreapi.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findAll(@RequestParam(required = false) String productName){

            return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/products/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findProductById(@PathVariable("productId") long productId){
        try {
            Product doc = productService.findById(productId);
            return ResponseEntity.ok(doc);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) throws URISyntaxException {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.created(new URI("/api/products/"+newProduct.getProductId())).body(product);
        }catch (ResourceAlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (BadResourceException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product,@PathVariable long productId){
        try {
            product.setProductId(productId);
            productService.updateProduct(product);
            String res = "Product updated successfully";
            return ResponseEntity.ok().build().accepted().body(res);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }catch (BadResourceException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable long productId){
        try {
            Product product = productService.deleteById(productId);
            String res = "Product deleted successfully";
            return ResponseEntity.accepted().body(res);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
