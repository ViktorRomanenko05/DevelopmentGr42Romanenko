package de.ait.javalessons.repositories;

import de.ait.javalessons.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
