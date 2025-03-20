package de.ait.javalessons.repositories;

import de.ait.javalessons.model.Article;
import de.ait.javalessons.model.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {
    Optional<ShopCart> findByArticle(Article article);

    List<Article> findAllByArticle(Article article);
}
