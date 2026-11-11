package io.github.vekzzdev.personalblog.exception;

public class ArticleException extends DomainException {

    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
