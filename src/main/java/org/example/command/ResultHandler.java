package org.example.command;

public interface ResultHandler<T> {
    void handle(T result);
}
