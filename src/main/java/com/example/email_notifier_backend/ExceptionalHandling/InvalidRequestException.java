package com.example.email_notifier_backend.ExceptionalHandling;

 public class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String msg) {
            super(msg);
        }
    }

