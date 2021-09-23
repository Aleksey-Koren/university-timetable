package com.foxminded.koren.university.repository.exceptions;

public class RepositoryException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7938364894400516990L;

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException() {
        super();
    }

    public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}