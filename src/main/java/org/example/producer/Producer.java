package org.example.producer;

import org.example.command.Command;

import java.util.concurrent.BlockingQueue;

public class Producer {
    private final BlockingQueue<Command> queue;

    public Producer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    public void produce(Command command) {
        try {
            queue.put(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
