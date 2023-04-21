package finalproject.application.service;

import java.util.HashMap;

public interface UserService {
    public String logIn(HashMap<String,String> data);
    public void logOut();
    public String register(HashMap<String,String> data);
}
