package finalproject.application.serviceImpl;

import finalproject.application.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String logIn(HashMap<String, String> data) {
        return null;
    }

    @Override
    public void logOut() {

    }

    @Override
    public String register(HashMap<String, String> data) {
        return null;
    }

    @Override
    public HashMap<String, Object> getAllUsers() {
        return null;
    }
}
