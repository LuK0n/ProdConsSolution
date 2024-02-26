package org.example.command;

import org.example.dao.UserDAO;
import org.example.model.User;

public class DeleteAllCommand implements Command {
    private UserDAO userDAO;

    public DeleteAllCommand(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        userDAO.deleteAllUsers();
    }
}
