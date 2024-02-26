package org.example.integration;

import org.example.command.AddCommand;
import org.example.command.Command;
import org.example.command.DeleteAllCommand;
import org.example.command.PrintAllCommand;
import org.example.consumer.Consumer;
import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.model.User;
import org.example.producer.Producer;
import org.example.util.HibernateUtil;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProducerConsumerIntegrationTest {

    private BlockingQueue<Command> queue;
    private Producer producer;
    private Consumer consumer;
    private Thread consumerThread;

    @BeforeEach
    public void setup() {
        queue = new LinkedBlockingQueue<>();
        producer = new Producer(queue);
        consumer = new Consumer(queue);
        consumerThread = new Thread(consumer);

        consumerThread.start();
    }

    @Test
    public void testConsumerProducerWorkflow() {

        User user1 = new User("1", "Robert");
        User user2 = new User("2", "Martin");

        UserDAO userDao = new UserDAOImpl(HibernateUtil.getSessionFactory());
        producer.produce(new AddCommand(user1, userDao));
        producer.produce(new AddCommand(user2, userDao));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producer.produce(new PrintAllCommand(userDao, (callback) -> {
            assertTrue(callback.contains(user1) && callback.contains(user2), "Users are not added.");
        }));
        producer.produce(new DeleteAllCommand(userDao));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producer.produce(new PrintAllCommand(userDao, (callback) -> {
            assertTrue(callback.isEmpty(), "Users are not removed.");
        }));
    }
}
