package de.ait.javalessons.exceptions;

public class ArticleExistException extends RuntimeException {
    public ArticleExistException(String message) {
        super(message);
    }
}
