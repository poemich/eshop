package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("d09f046-9b1b-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap User");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateExisting() {
        // Create and add a product
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        productRepository.create(product);
        
        // Update product details
        Product updated = new Product();
        updated.setProductId("prod-1");
        updated.setProductName("New Name");
        updated.setProductQuantity(20);
        Product result = productRepository.update(updated);
        
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }
    
    @Test
    void testUpdateNonExisting() {
        Product updated = new Product();
        updated.setProductId("non-existent");
        updated.setProductName("Name");
        updated.setProductQuantity(5);
        assertNull(productRepository.update(updated));
    }
    
    @Test
    void testDeleteExisting() {
        Product product = new Product();
        product.setProductId("prod-2");
        product.setProductName("Name");
        product.setProductQuantity(30);
        productRepository.create(product);
        
        Product deleted = productRepository.delete("prod-2");
        assertNotNull(deleted);
        // Verify product has been removed
        assertNull(productRepository.findById("prod-2"));
    }

    @Test
    void testDeleteNonExisting() {
        assertNull(productRepository.delete("non-existent"));
    }

    @Test
    void testFindByIdExisting() {
        Product product = new Product();
        product.setProductId("find-1");
        product.setProductName("Find Me");
        product.setProductQuantity(5);
        productRepository.create(product);
        
        Product found = productRepository.findById("find-1");
        assertNotNull(found);
        assertEquals("Find Me", found.getProductName());
    }
    
    @Test
    void testFindByIdNonExisting() {
        assertNull(productRepository.findById("non-existent-id"));
    }
}