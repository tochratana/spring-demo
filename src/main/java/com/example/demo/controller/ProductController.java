package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

//@Controller // make this class to spring mvc controller
@RestController
public class ProductController {

    @GetMapping("/products") //
    public String getProducts(Model model) { // Used to pass data to the view.
        List<Product> products = Arrays.asList(
                new Product("iPhone 15", 999.99),
                new Product("Samsung S24", 899.99),
                new Product("Pixel 8", 799.99)
        );

        model.addAttribute("products", products); // Passes the product list to the view with name products
        return "products"; // This will look for templates/products.html, Tells Spring to render templates/products
        // .html.
    }

    @GetMapping("/users")
    // @ResponseBody
    public String getUser(){
        return "User Render Here";
    }

// @RestController = @Controller + @ResponseBody
}
