package com.estore.estoreapi;

import com.estore.estoreapi.entity.Product;
import com.estore.estoreapi.exceptionHandling.BadResourceException;
import com.estore.estoreapi.exceptionHandling.ResourceAlreadyExistsException;
import com.estore.estoreapi.exceptionHandling.ResourceNotFoundException;
import com.estore.estoreapi.repository.ProductRepository;
import com.estore.estoreapi.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProductsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    private String uri;
    @PostConstruct
    public void init() {uri="http://localhost:"+port;}
    @MockBean
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @Test
    void checkSizeOfMockProducts() {
        List<Product> products = Arrays.asList(
                new Product("Black Coin", 16, 90, 100, true),
                new Product("White Coin", 16, 90, 100, true),
                new Product("Chess Board", 1, 70, 100, true)
        );
        when(productService.findAll()).thenReturn(products);
        get(uri + "/api/products").then().statusCode(200).assertThat().body("size()", is(3));
    }

    @Test
    void testProductsIsNotEmpty() {
        String responseString = get(uri + "/api/products/").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(responseString).isNotEmpty();
    }

    @Test
    void testProductById() throws ResourceNotFoundException {
        Product productValue = new Product("Black Coin", 16, 90, 100, true);
        int n = 1;
        long lnum = n;
        when(productService.findById(lnum)).thenReturn(productValue);
        get(uri + "/api/products/" + lnum).then().statusCode(200).assertThat().equals(productValue);
    }

    @Test
    void testAddProduct() throws BadResourceException, ResourceAlreadyExistsException {
        Product product = new Product("testing", 16, 90, 100, true);
        when(productService.addProduct(product)).thenReturn(product);
        post(uri + "/api/products").then().assertThat().equals(product);
    }

    @Test
    void testUpdateProduct() throws ResourceNotFoundException, BadResourceException {
        Product product = new Product("testing", 16, 90, 100, true);
        long lnum = 15;
        product.setProductId(lnum);
        when(productService.updateProduct(product)).thenReturn(product);
        given().put(uri + "/api/products/{id}", 15).then().assertThat().equals(product);
    }

    @Test
    void testDeleteProduct() throws BadResourceException, ResourceAlreadyExistsException, ResourceNotFoundException {
        Product product = new Product("testingProduct", 16, 90, 100, true);
        Product prd = productRepository.save(product);
        productService.deleteById(prd.getProductId());
    }




}
