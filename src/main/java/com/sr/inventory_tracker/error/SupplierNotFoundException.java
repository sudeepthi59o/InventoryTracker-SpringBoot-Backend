package com.sr.inventory_tracker.error;

public class SupplierNotFoundException extends Exception {
    protected SupplierNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SupplierNotFoundException(Throwable cause) {
        super(cause);
    }

    public SupplierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException() {
        super();
    }
}
