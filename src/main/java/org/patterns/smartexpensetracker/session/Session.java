package org.patterns.smartexpensetracker.session;

import org.patterns.smartexpensetracker.models.User;

public class Session {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
