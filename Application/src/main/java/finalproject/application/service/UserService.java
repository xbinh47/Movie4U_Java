package finalproject.application.service;

import finalproject.application.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;

public interface UserService {
    public HashMap<String, Object> logIn(HashMap<String,String> data);
    public void logOut();
    public HashMap<String, Object> register(HashMap<String,String> data);
    HashMap<String, Object> getAllUsers();
    UserDetails getAccountByEmail(String email) throws UsernameNotFoundException;
    HashMap<String, Object> checkToken(String token, String authorizationHeader) throws Exception;
}
