package miljoboven;

import java.util.Arrays;

/**
 * Class for user data.
 * @author Johan
 * @author Kristoffer
 * @version 2015-05-21
 */
public class User {
    
    private final String username;
    private char[] password;
    private UserTypeE userType;
    private DepartmentsE department;
    
    /**
     * Constructs a new User object with username, password and userType.
     * Does not check if username is unique. Class may be changed to use
     * factory method that checks for unique username.
     * @param username The User's username
     * @param password The User's password
     * @param userType The User type to get correct access
     * @param department The User's department
     */
    public User(String username, char[] password, UserTypeE userType, DepartmentsE department) {
        this.username = username;
        this.password = password.clone();
        this.userType = userType;
        this.department = department;
    }
    
    /**
     * Verifies if password is correct for the user.
     * @param password
     * @return true if the password is correct
     */
    public boolean verifyPassword(char[] password) {
        return Arrays.equals(password, this.password);
    }

    /**
     * Gets the username.
     * @return username.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the userType
     * @return userType
     */
    public UserTypeE getUserType() {
        return userType;
    }
    
    /**
     * Gets the department of the user
     * @return department
     */
    public DepartmentsE getDepartment() {
        return department;
    }
        
}
