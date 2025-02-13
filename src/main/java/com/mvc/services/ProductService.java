package com.mvc.services;

import com.mvc.dao.ProductRepository;
import com.mvc.dto.ProductRequest;
import com.mvc.dto.ProductResponse;
import com.mvc.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts() throws Exception {
        if(this.productRepository.findAll().isEmpty()) {
            throw new Exception("no records");
        }
        return this.productRepository.findAll();
    }

    public List<Product> getProductsByPrice(float price) throws Exception {
        if(this.productRepository.findByPriceGreaterThanEqual(price).isEmpty())
        {
            throw new Exception("no price");
        }
        return this.productRepository.findByPriceGreaterThanEqual(price);
    }

    public ProductResponse create(ProductRequest productRequest) throws Exception{
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.getStatus());
        this.productRepository.save(product);
        return  new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                "product created"
        );
    }

    public ProductResponse update(Long id, ProductRequest productRequest) throws Exception {
        Product existingProduct = this.productRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Product not found"));
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setStatus(productRequest.getStatus());
        this.productRepository.save(existingProduct);
        return new ProductResponse(
                existingProduct.getName(),
                existingProduct.getDescription(),
                existingProduct.getPrice(),
                existingProduct.getQuantity(),
                existingProduct.getStatus(),
                "updated product"
        );
    }

    public String delete(Long id) throws Exception {
        Product existingProduct = this.productRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Product not found"));
        this.productRepository.delete(existingProduct);
        return "Product id" + id + "has been deleted";
    }

}
