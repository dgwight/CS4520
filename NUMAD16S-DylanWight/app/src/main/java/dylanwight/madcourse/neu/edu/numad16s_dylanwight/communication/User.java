package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

/**
 * Created by DylanWight on 3/24/16.
 */
public class User {
    private String username;
    private String userId;

    public User() {}

    public User(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
