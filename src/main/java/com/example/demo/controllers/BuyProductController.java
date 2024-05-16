package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class BuyProductController {

    private final ProductRepository productRepository;

    @Autowired
    public BuyProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/buyProduct/{productID}")
    public String buyProduct(@PathVariable("productID") Long productID, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(productID);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                return "redirect:/purchaseSuccess"; // Redirect to success page
            } else {
                return "redirect:/purchaseError"; // Redirect to error page
            }
        } else {
            return "redirect:/purchaseError"; // Redirect to error page
        }
    }
    @GetMapping("/purchaseSuccess")
    public String displayPurchaseSuccess() {
        return "purchaseSuccess";
    }
    @GetMapping("/purchaseError")
    public String displayPurchaseError() {
        return "purchaseError";
    }
}
