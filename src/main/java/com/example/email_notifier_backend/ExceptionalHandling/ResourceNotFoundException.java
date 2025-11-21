package com.example.email_notifier_backend.ExceptionalHandling;


    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String msg) {
            super(msg);
        }
    }

