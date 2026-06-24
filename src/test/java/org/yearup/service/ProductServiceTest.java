package org.yearup.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void search() {
        //Arrange

        Product product1 = new Product();
        product1.setStock(1);
        product1.setFeatured(true);

        Product product2 = new Product();
        product2.setStock(2);
        product2.setFeatured(false);

        ArrayList<Product> products = new ArrayList<>();

        products.add(product1);
        products.add(product2);
        when(productRepository.findAll()).thenReturn(products);

        //Act
        List<Product> list = productService.search(null, null, null, null);
        //Assert
        Assertions.assertEquals(products.size(), list.size());
        verify(productRepository).findAll();
    }

    @Test
    void saveProduct() {
        // Arrange

        Product product1 = new Product();
        product1.setStock(1);

        Product product2 = new Product();
        product2.setStock(2);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
        when(productRepository.save(product1)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product output = productService.update(1, product2);
        // Assert
        Assertions.assertEquals(2, output.getStock());
        verify(productRepository).save(product1);
    }

    //Again

}