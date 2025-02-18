package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

class ProductServiceTest {
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new ProductRepository();
        productService = new ProductServiceImpl();
        try {
            java.lang.reflect.Field field = ProductServiceImpl.class.getDeclaredField("productRepository");
            field.setAccessible(true);
            field.set(productService, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateAndFindAll() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productService.create(product);

        List<Product> products = productService.findAll();
        assertEquals(1, products.size());
        assertNotNull(products.get(0).getProductId());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        Product created = productService.create(product);

        Product found = productService.findById(created.getProductId());
        assertNotNull(found);
        assertEquals("Test Product", found.getProductName());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        Product created = productService.create(product);

        created.setProductName("New Name");
        created.setProductQuantity(20);
        Product updated = productService.update(created);

        assertEquals("New Name", updated.getProductName());
        assertEquals(20, updated.getProductQuantity());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductName("Delete Me");
        product.setProductQuantity(5);
        Product created = productService.create(product);

        productService.delete(created.getProductId());
        Product found = productService.findById(created.getProductId());
        assertNull(found);
    }
}
