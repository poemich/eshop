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
    void testEditExistingProduct() {
        Product product = new Product();
        product.setProductId("upd-123");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);
        
        product.setProductName("Edited Name");
        product.setProductQuantity(20);
        Product edited = productRepository.edit(product);
        assertNotNull(edited);
        assertEquals("Edited Name", edited.getProductName());
        assertEquals(20, edited.getProductQuantity());
    }

    @Test
    void testEditNonexistentProduct() {
        Product product = new Product();
        product.setProductId("nonexistent");
        product.setProductName("No Name");
        product.setProductQuantity(0);
        
        Product edited = productRepository.edit(product);
        assertNull(edited);
    }

    @Test
    void testDeleteExistingProduct() {
        Product product = new Product();
        product.setProductId("del-123");
        product.setProductName("To Be Deleted");
        product.setProductQuantity(5);
        productRepository.create(product);
        
        Product deleted = productRepository.delete("del-123");
        assertNotNull(deleted);
        assertEquals("del-123", deleted.getProductId());
        assertNull(productRepository.findById("del-123"));
    }

    @Test
    void testDeleteNonexistentProduct() {
        Product deleted = productRepository.delete("nonexistent");
        assertNull(deleted);
    }
}