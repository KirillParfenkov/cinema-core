package by.cinema.dao.user;

import by.cinema.bean.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Kiryl_Parfiankou on 10/25/2015.
 */
public class UserDaoImpl implements UserDao {

    private List<User> users;
    /**
     * Map for quick access to the User object by Id
     * Id -- > User
     */
    private Map<String, User> userIdMap;
    /**
     * Map for quick access to the User object by Email
     * Email -- > User
     */
    private Map<String, User> userEmailMap;
    /**
     * Map for quick access to the User object by Name
     * Name -- > User
     */
    private Map<String, User> userNameMap;

    public UserDaoImpl() {
        users = new ArrayList<User>();
        userIdMap = new HashMap<String, User>();
        userEmailMap = new HashMap<String, User>();
        userNameMap = new HashMap<String, User>();
    }

    public User add(User user) {

        // Generate id for user
        User addedUser = new User(UUID.randomUUID().toString(), user);

        users.add(addedUser);
        addToMaps(addedUser);
        return addedUser;
    }

    public User get(String id) {
        return userIdMap.get(id);
    }

    public void remove(String id) {
        User user = userIdMap.get(id);
        users.remove(user);
        removeFromMaps(user);
    }

    public User getByEmail(String email) {
        return userEmailMap.get(email);
    }

    // Get user by full name
    public User getByName(String name) {
        return userNameMap.get(name);
    }

    /**
     * Update userIdMap, userEmailMap, userNameMap
     * @param user
     */
    private void addToMaps(User user) {
        userIdMap.put(user.getId(), user);
        userEmailMap.put(user.getEmail(), user);
        userNameMap.put(user.getFullName(), user);
    }
    /**
     * Update userIdMap, userEmailMap, userNameMap
     * @param user
     */
    private void removeFromMaps(User user) {
        userIdMap.remove(user.getId());
        userEmailMap.remove(user.getEmail());
        userNameMap.remove(user.getFullName());
    }
}
