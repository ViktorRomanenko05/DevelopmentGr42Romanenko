package de.ait.javalessons.service;

import de.ait.javalessons.exceptions.ArticleExistException;
import de.ait.javalessons.exceptions.ArticleNotFoundException;
import de.ait.javalessons.exceptions.QuantityException;
import de.ait.javalessons.model.Article;
import de.ait.javalessons.model.ShopCart;
import de.ait.javalessons.repositories.ArticleRepository;
import de.ait.javalessons.repositories.ShopCartRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShopService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ShopCartRepository shopCartRepository;

    //Метод просмотра всех существующих товаров
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    //Метод просмотра всех товаров в корзине
    public List<ShopCart> findAllInCart() {
        return shopCartRepository.findAll();
    }

    //метод добавления нового товара
    public Article addNewArticle(String name, String description, double price, int quantity) {
        if (name == null){
            log.error("Name is null");
            throw new IllegalArgumentException("Name is null");
        }
        boolean existName = articleRepository.findAll().stream().map(Article::getName).anyMatch(articleName -> articleName.equals(name));
        if (existName) {
            log.info("Article with name {} is already exist", name);
            throw new ArticleExistException("Article with name {} is already exist");
        } else {
            Article newArticle = new Article(name, description, price, quantity);
            articleRepository.save(newArticle);
            return newArticle;
        }
    }

    //Метод добавления товара в корзину
    @Transactional
    public ShopCart addArticleToCart(Long articleId, int quantity) {
        if(articleId == null){
            log.error("article id is null");
            throw new IllegalArgumentException("Article id is null");
        }
        if(quantity <= 0){
            log.error("quantity is less or equal 0");
            throw new IllegalArgumentException("quantity is less or equal 0");
        }
        Optional<Article> foundArticle = articleRepository.findById(articleId);
        if (foundArticle.isEmpty()) {
            log.error("Article with id {} was not found", articleId);
            throw new ArticleNotFoundException("Article was not found");
        } else if ((foundArticle.get().getQuantity() - quantity) < 0) {
            log.info("Insufficient quantity of {}, {}", articleId, foundArticle.get().getName());
            throw new QuantityException("Insufficient quantity of goods");
        } else {
            Article articleToAdd = foundArticle.get();
            Optional<ShopCart> existInCart = shopCartRepository.findByArticle(articleToAdd);
            articleToAdd.setQuantity(articleToAdd.getQuantity() - quantity);
            if (existInCart.isPresent()) {
                int quantityAfterAdd = existInCart.get().getQuantityInCart() + quantity;
                existInCart.get().setQuantityInCart(quantityAfterAdd);
            } else {
                shopCartRepository.save(new ShopCart(articleToAdd, quantity));
            }
        }
        Optional<ShopCart> renewedItem = shopCartRepository.findAll().stream().filter(cart -> cart.getArticle().getId() == articleId).findFirst();
        return renewedItem.get();
    }
}
