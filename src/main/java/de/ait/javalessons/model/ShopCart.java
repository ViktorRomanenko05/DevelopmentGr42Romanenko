package de.ait.javalessons.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Связь с товаром
    @ManyToOne
    private Article article;

    // Количество данного товара в корзине
    private int quantityInCart;

    public ShopCart() {
    }

    public ShopCart(Article article, int quantityInCart) {
        this.article = article;
        this.quantityInCart = quantityInCart;
    }
}