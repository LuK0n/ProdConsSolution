package org.example.consumer;

import org.example.command.Command;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Command> queue;

    public Consumer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Command command = queue.take();
                command.execute();
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
