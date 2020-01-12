package Models;

import android.content.Context;

import java.util.List;

public class UserSingleton {
    private static final UserSingleton ourInstance = new UserSingleton();

    private String name;

    private String photoPath;

    private Trash someTrash;

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

    public String getPhotoPath() { return photoPath; }

    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    private UserSingleton() {
    }
}
