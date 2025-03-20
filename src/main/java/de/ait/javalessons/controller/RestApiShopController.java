package de.ait.javalessons.controller;

import de.ait.javalessons.model.Article;
import de.ait.javalessons.model.ShopCart;
import de.ait.javalessons.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class RestApiShopController {

    @Autowired
    ShopService shopService;

    //Get all Articles (public)
    @GetMapping("/public/list")
    public ResponseEntity<List<Article>> getAllArticles(){
        return ResponseEntity.ok(shopService.findAllArticles());
    }

    //Get all items in cart (customer)
    @GetMapping("/customer/cart")
    public ResponseEntity<List<ShopCart>> getAllArticlesInCart () {
        return ResponseEntity.ok(shopService.findAllInCart());
    }

    //Add article to cart
    @PostMapping("/customer/cart")
    public ResponseEntity <ShopCart> addArticleToCart(@RequestParam Long id, @RequestParam int quantity){
    return ResponseEntity.ok(shopService.addArticleToCart(id, quantity));
    }

    //Add new article
    @PostMapping("/manager/add")
    public ResponseEntity<Article> addNewArticle (
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity){
        return ResponseEntity.ok(shopService.addNewArticle(name, description, price, quantity));
    }

}
