package com.mvc.controller;

import com.mvc.dto.ProductRequest;
import com.mvc.dto.ProductResponse;
import com.mvc.model.Product;
import com.mvc.services.ProductService;
import com.mvc.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final JWTUtil jwtUtil = new JWTUtil();

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ProductRequest productRequest,
                                 @RequestHeader("Authorization") String jwt)
    {
        try{
            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("Token não válido");
            }
            if(!this.jwtUtil.verifyRole(jwt, "SUPER")) {
                return ResponseEntity.badRequest().body("Não cumpre com os requisitos de permissão");
            }
            ProductResponse productResponse;
            productResponse = this.productService.create(productRequest);
            return ResponseEntity.ok(productResponse);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode criar produto" + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity listProduct(@RequestHeader("Authorization") String jwt) {
        try{
            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("Token não válido");
            }
            if(!this.jwtUtil.verifyRole(jwt, "TEC")) {
                return ResponseEntity.badRequest().body("Não cumpre com os requisitos de permissão");
            }
            List<Product> products;
            products = this.productService.listProducts();
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode listar produto" + e.getMessage());
        }
    }

    @GetMapping("/by-price/{price}")
    public ResponseEntity getProductsByPrice(@PathVariable float price,
                                             @RequestHeader("Authorization") String jwt) {
        try{
            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("Token não válido");
            }
            if(!this.jwtUtil.verifyRole(jwt, "SUPER")) {
                return ResponseEntity.badRequest().body("Não cumpre com os requisitos de permissão");
            }
            List<Product> products;
            products = this.productService.getProductsByPrice(price);
            return ResponseEntity.ok(products);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode listar preços " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestBody ProductRequest productRequest,
                                 @RequestHeader("Authorization") String jwt) {
        try{
            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("Token não válido");
            }
            if(!this.jwtUtil.verifyRole(jwt, "SUPER")) {
                return ResponseEntity.badRequest().body("Não cumpre com os requisitos de permissão");
            }
            ProductResponse productResponse;
            productResponse = this.productService.update(id, productRequest);
            return ResponseEntity.ok(productResponse)
;        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode atualizar produto" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestHeader("Authorization") String jwt) {
        try{
            if(!this.jwtUtil.isTokenValid(jwt)){
                return ResponseEntity.badRequest().body("Token não válido");
            }
            if(!this.jwtUtil.verifyRole(jwt, "ADMIN")) {
                return ResponseEntity.badRequest().body("Não cumpre com os requisitos de permissão");
            }
            String message;
            message = this.productService.delete(id);
            return ResponseEntity.ok(message);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode deletar produto" + e.getMessage());
        }
    }

}
