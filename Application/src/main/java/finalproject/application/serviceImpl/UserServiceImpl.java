package finalproject.application.serviceImpl;

import finalproject.application.config.JwtService;
import finalproject.application.entity.Account;
import finalproject.application.entity.Role;
import finalproject.application.repository.AccountRepository;
import finalproject.application.service.EmailService;
import finalproject.application.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;

    @Override
    public HashMap<String, Object> logIn(HashMap<String, String> data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        if(data.get("email") == null || data.get("password") == null){
            result.put("code", "400");
            result.put("message", "Please fill in all the fields");
        }
        String email = data.get("email");
        String password = data.get("password");

        Account account =  accountRepository.getAccountByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(account == null){
            result.put("code", "400");
            result.put("message", "Email/Password is incorrect");
        }
        else{
            if(!encoder.matches(password, account.getPassword())){
                result.put("code", "400");
                result.put("message", "Email/Password is incorrect");
            }
            else{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                result.put("code", "200");
                result.put("message", "Login successfully");
                var userDetails = new User(email, password, account.getAuthorities());
                String jwt = jwtService.generateToken(userDetails);
                result.put("accessToken", jwt);
                result.put("status", account.getStatus());
                result.put("data", account);
            }
        }
        return result;
    }

    @Override
    public void logOut() {

    }

    @Override
    public HashMap<String, Object> register(HashMap<String, String> data) throws MessagingException {
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
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Account newAccount = new Account();
            newAccount.setEmail(email);
            String encodedPassword = encoder.encode(password);
            newAccount.setPassword(encodedPassword);
            newAccount.setName(name);
            newAccount.setRole(Role.USER);
            LocalDateTime now = LocalDateTime.now();
            newAccount.setCreateat(now.toString());
            newAccount.setStatus(1);
            accountRepository.save(newAccount);

            String  content = "<h1>Welcome to Movie4U</h1><h2>Your account</h2><h3>Email: " + email + "</h3><h3>Password: " + password + "</h3>";

            CompletableFuture.runAsync(() -> {
                try {
                    emailService.sendEmail(email, "Movie4U - Register", content);
                    System.out.println("Email sent successfully");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

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

            if (jwtService.validateToken(jwt, userDetails)) {
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

    @Override
    public HashMap<String, Object> updateUserInfo(HashMap<String, String> data) {
        Account account = accountRepository.getAccountByEmail(data.get("email"));
        String name = data.get("name");
        String phone = data.get("phone");
        String address = data.get("address");
        String birthday = data.get("birthday");
        account.setName(name);
        account.setPhone(phone);
        account.setAddress(address);
        account.setBirthday(birthday);
        accountRepository.save(account);
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "Update successfully");
        response.put("data", account);
        return response;
    }

    @Override
    public HashMap<String, Object> changePassword(HashMap<String, String> data) {
        Account account = accountRepository.getAccountByEmail(data.get("email"));
        String password = data.get("password");
        String confirmPassword = data.get("confirmPassword");
        String newPassword = data.get("newPassword");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password, account.getPassword())){
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", "400");
            response.put("message", "Old password is incorrect");
            return response;
        }
        else{
            if(!newPassword.equals(confirmPassword)){
                HashMap<String, Object> response = new HashMap<>();
                response.put("code", "400");
                response.put("message", "Confirm password is incorrect");
                return response;
            }
            String hashedPassword = encoder.encode(newPassword);
            account.setPassword(hashedPassword);
            accountRepository.save(account);
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", "200");
            response.put("message", "Change password successfully");
            return response;
        }
    }
}
