package com.example.wine.domain.callback;

public interface RepositoryCallback<T> {
    void onSuccess(T result);
    void onError(Exception e); // Ou um tipo de erro mais específico
}