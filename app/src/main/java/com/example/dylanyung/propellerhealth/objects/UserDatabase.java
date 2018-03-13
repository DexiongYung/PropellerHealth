package com.example.dylanyung.propellerhealth.objects;

import java.util.HashMap;

/**
 * Created by Dylan Yung on 3/6/2018.
 */

public class UserDatabase {
    private static UserDatabase instance;
    private HashMap<String, Person> people = new HashMap<>();
    private String currentUser;

    private UserDatabase() {
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public HashMap<String, Person> getPeople() {
        return people;
    }

    public void addPerson(Person p) {
        this.people.put(p.getUid(), p);
    }

    public Person getPerson(String id) {
        return this.people.get(id);
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
