package org.example;

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
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        UserDAO userDao = new UserDAOImpl(HibernateUtil.getSessionFactory());
        producer.produce(new AddCommand(new User("1", "Robert"), userDao));
        producer.produce(new AddCommand(new User("2", "Martin"), userDao));
        producer.produce(new PrintAllCommand(userDao, (callback) -> {
            callback.forEach(System.out::println);
        }));
        producer.produce(new DeleteAllCommand(userDao));

    }
}