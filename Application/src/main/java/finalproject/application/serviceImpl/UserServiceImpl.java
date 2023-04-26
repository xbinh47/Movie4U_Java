package finalproject.application.serviceImpl;

import finalproject.application.config.JwtService;
import finalproject.application.entity.Account;
import finalproject.application.repository.AccountRepository;
import finalproject.application.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public HashMap<String, Object> logIn(HashMap<String, String> data) {
        HashMap<String, Object> response = new HashMap<>();
        if(data.get("email") == null || data.get("password") == null){
            response.put("code", "400");
            response.put("message", "Please fill in all the fields");
        }
        String email = data.get("email");
        String password = data.get("password");

        Account account =  accountRepository.getAccountByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(account == null){
            response.put("code", "400");
            response.put("message", "Email/Password is incorrect");
        }
        else{
            if(!encoder.matches(password, account.getPassword())){
                response.put("code", "400");
                response.put("message", "Email/Password is incorrect");
            }
            else{
                response.put("code", "200");
                response.put("message", "Login successfully");
                response.put("data", account);
            }
        }
        return response;
    }

    @Override
    public void logOut() {

    }

    @Override
    public HashMap<String, Object> register(HashMap<String, String> data) {
        HashMap<String, Object> response = new HashMap<>();
        if(data.get("email") == null || data.get("password") == null || data.get("name") == null){
            response.put("code", "400");
            response.put("message", "Please fill in all the fields");
        }
        String email = data.get("email");
        String password = data.get("password");
        String name = data.get("name");

        Account account =  accountRepository.getAccountByEmail(email);
        if(account != null){
            response.put("code", "400");
            response.put("message", "Email already exists");
        }
        else{
            Account newAccount = new Account();
            newAccount.setEmail(email);

            newAccount.setPassword(password);
            newAccount.setName(name);
            accountRepository.save(newAccount);
            response.put("code", "200");
            response.put("message", "Account created successfully");
        }
        return response;
    }

    @Override
    public HashMap<String, Object> getAllUsers() {
        return null;
    }

    @Override
    public UserDetails getAccountByEmail(String email) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByEmail(email);
        if (account == null) {
            throw new EntityNotFoundException("User with email " + email + " not found.");
        }

        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(account.getStatus().toString())
                .build();
    }

    @Override
    public HashMap<String, Object> checkToken(String token, String authorizationHeader) {
        String email = null;
        String jwt = null;
        JwtService jwtService = new JwtService();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            jwt = authorizationHeader.substring(7);
            email = jwtService.extractUsername(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = getAccountByEmail(email);

            if (jwtService.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(null));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                HashMap<String, Object> response = new HashMap<>();
                response.put("code", "200");
                response.put("message", "Token is valid");
                return response;
            } else {
                HashMap<String, Object> response = new HashMap<>();
                response.put("code", "400");
                response.put("message", "Token is invalid");
                return response;
            }
        }else{
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", "400");
            response.put("message", "Token is invalid");
            return response;
        }
    }
}
