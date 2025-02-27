package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("CreateProduct"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Created");
        product.setProductQuantity(1);
        when(service.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/create")
                        .param("productName", "Created")
                        .param("productQuantity", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Listed");
        product.setProductQuantity(2);
        when(service.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/product/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("ProductList"))
            .andExpect(model().attributeExists("products"));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductId("edit-id");
        product.setProductName("To Edit");
        product.setProductQuantity(3);
        when(service.findById("edit-id")).thenReturn(product);

        mockMvc.perform(get("/product/edit").param("productId", "edit-id"))
            .andExpect(status().isOk())
            .andExpect(view().name("EditProduct"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("edit-id");
        product.setProductName("Edited");
        product.setProductQuantity(4);
        when(service.update(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/edit")
                        .param("productId", "edit-id")
                        .param("productName", "Edited")
                        .param("productQuantity", "4"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(service).delete("delete-id");

        mockMvc.perform(get("/product/delete").param("productId", "delete-id"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/product/"))
            .andExpect(status().isOk())
            .andExpect(view().name("HomePage"));
    }
}
