package org.example.command;

import org.example.dao.UserDAO;
import org.example.model.User;

public class AddCommand implements Command {
    private User user;
    private UserDAO userDAO;

    public AddCommand(User user, UserDAO userDAO) {
        this.user = user;
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        userDAO.addUser(user);
    }
}
