package org.example.command;

import org.example.dao.UserDAO;
import org.example.model.User;

import java.util.List;

public class PrintAllCommand implements Command {
    private UserDAO userDAO;

    private ResultHandler<List<User>> resultHandler;

    public PrintAllCommand(UserDAO userDAO, ResultHandler<List<User>> resultHandler) {
        this.userDAO = userDAO;
        this.resultHandler = resultHandler;
    }

    @Override
    public void execute() {
        List<User> users = userDAO.getAllUsers();
        resultHandler.handle(users);
    }
}
