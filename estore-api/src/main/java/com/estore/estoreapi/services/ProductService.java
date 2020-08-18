package com.estore.estoreapi.services;

import com.estore.estoreapi.repository.ProductRepository;
import com.estore.estoreapi.exceptionHandling.BadResourceException;
import com.estore.estoreapi.exceptionHandling.ResourceAlreadyExistsException;
import com.estore.estoreapi.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.estore.estoreapi.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;


    private boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product findById(Long id) throws ResourceNotFoundException{
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new ResourceNotFoundException("Cannot find Product with id: "+id);
        }else {
            return product;
        }
    }





    @Override
    public List<Product> findAll(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product addProduct(Product product) throws BadResourceException,ResourceAlreadyExistsException{
        if(!StringUtils.isEmpty(product.getProductName())){
            return productRepository.save(product);
        }else {
            BadResourceException exc = new BadResourceException("Failed to add product");
            exc.addErrorMessage("Product is null or empty");
            throw exc;
        }
    }
   /* @Override
    public void updateProduct(Product product) throws BadResourceException, ResourceNotFoundException {
            if(!StringUtils.isEmpty(product.getProductName())){
                if (!existsById(product.getProductId())){
                    throw new ResourceNotFoundException("Cannot find product with id: "+product.getProductId());
                }
                productRepository.save(product);
            }else {
                BadResourceException exc = new BadResourceException("Failed to update product");
                exc.addErrorMessage("Product is null or empty");
                throw exc;
            }
    }*/

    @Override
    public Product updateProduct(Product product) throws BadResourceException, ResourceNotFoundException {
        if(!StringUtils.isEmpty(product.getProductName())){
            if (!existsById(product.getProductId())){
                throw new ResourceNotFoundException("Cannot find product with id: "+product.getProductId());
            }
            return productRepository.save(product);
        }else {
            BadResourceException exc = new BadResourceException("Failed to update product");
            exc.addErrorMessage("Product is null or empty");
            throw exc;
        }
    }
    @Override
    public Product deleteById(Long productId) throws ResourceNotFoundException{
        if(!existsById(productId)){
            throw new ResourceNotFoundException("Cannot find Product with id: "+productId);
        }else{
            Product products = findById(productId);
            productRepository.deleteById(productId);
            return  products;
        }
    }
    @Override
    public  Long count(){
        return productRepository.count();
    }

}
