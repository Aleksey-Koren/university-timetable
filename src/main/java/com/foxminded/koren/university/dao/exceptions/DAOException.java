package com.foxminded.koren.university.dao.exceptions;

public class DAOException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7938364894400516990L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}