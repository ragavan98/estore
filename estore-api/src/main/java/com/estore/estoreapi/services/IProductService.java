package com.estore.estoreapi.services;

import com.estore.estoreapi.entity.Product;
import com.estore.estoreapi.exceptionHandling.BadResourceException;
import com.estore.estoreapi.exceptionHandling.ResourceAlreadyExistsException;
import com.estore.estoreapi.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface IProductService {
    public Product addProduct(Product product) throws BadResourceException, ResourceAlreadyExistsException;
    public List<Product> findAll();
    public Product findById(Long id) throws ResourceNotFoundException;
    /*public void updateProduct(Product product) throws BadResourceException, ResourceNotFoundException;*/
    public Product updateProduct(Product product) throws BadResourceException, ResourceNotFoundException;
    public Product deleteById(Long productId) throws ResourceNotFoundException;
    public  Long count();

}
