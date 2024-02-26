package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "USER_GUID")
    private String userGuid;

    @Column(name = "USER_NAME")
    private String userName;

    public User(String userGuid, String userName) {
        this.userGuid = userGuid;
        this.userName = userName;
    }

    public User() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(userId).append("\n");
        sb.append("GUID: ").append(userGuid).append("\n");
        sb.append("Name: ").append(userName).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if(obj instanceof User) {
            return ((User) obj).userId == this.userId;
        }

        return false;
    }
}
