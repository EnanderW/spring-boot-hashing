package me.code.springproducts.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/register")
    public String registerUser(@RequestBody UserCreate user, HttpServletResponse response) {
        int code = userService.registerUser(user);
        switch (code) {
            case 1:
                response.setStatus(409);
                return "There is already a user with that username";
            case 0:
                return "User has been registered.";
            default:
                response.setStatus(500);
                return "Something went wrong.";
        }
    }

    @PostMapping("/login")
    public String login(@RequestHeader("username") String username, @RequestHeader("password") String password, HttpServletResponse response) {
        String token = userService.login(username, password);
        if (token == null) {
            response.setStatus(406);
            return null;
        }

        return token;
    }

    @PostMapping("/logout")
    public void login(@RequestHeader("token") String token) {
        userService.logout(token);
    }


    @GetMapping("/all")
    public Collection<User> getUsers() {
        return userService.getAll();
    }

    @Getter
    @Setter
    public static final class UserCreate {
        private String username;
        private String password;
    }

}
