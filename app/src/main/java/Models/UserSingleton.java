package Models;

import android.content.Context;

public class UserSingleton {
    private static final UserSingleton ourInstance = new UserSingleton();

    private String name;



    public static UserSingleton getInstance(Context context) {
        return ourInstance;
    }

    public static UserSingleton getOurInstance() {
        return ourInstance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private UserSingleton() {
    }
}
