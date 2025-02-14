package id.ac.ui.cs.advprog.eshop.repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
    
    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
        }
        
        public Product update(Product product) {
        Product existingProduct = findById(product.getProductId());
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductQuantity(product.getProductQuantity());
            return existingProduct;
        }
        return null;
        }

        public Product delete(String productId) {
        Product product = findById(productId);
        if (product != null) {
            productData.remove(product);
            return product;
        }
        return null;
        }
    }
