package com.example.SpringAPI.Controllers;

import com.example.SpringAPI.models.User;
import com.example.SpringAPI.services.user.UserService;
import com.example.SpringAPI.utilities.CookiesManager;
import com.example.SpringAPI.utilities.JwtManager;
import com.example.SpringAPI.utilities.PasswordManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(("/register"))
    public ResponseEntity<Object> register(@RequestBody User user){
        if(userService.findByEmail(user.getEmail()) == null){
            user.setPassword(PasswordManager.hashPassword(user.getPassword()));
            User u = userService.create(user);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Already Exist !");

    }
    @PostMapping(("/login"))
    public ResponseEntity<Object> login(@RequestBody User user, HttpServletResponse response){
        User u = userService.findByEmail(user.getEmail());
        if(u!= null){
            if(PasswordManager.checkPassword(user.getPassword(),u.getPassword())){
                String token = JwtManager.generateJwtToken(u.getId());
                CookiesManager.setCookie(response,"jwt",token);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong Password");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong Email");
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response){
        CookiesManager.deleteCookie(request,response,"jwt");
        return ResponseEntity.ok("Logout Seccessfully !");
    }

    @GetMapping
    public ResponseEntity<Object> read(@CookieValue(name = "jwt", required = false)String cookieValue){
        String token = CookiesManager.getCookie(cookieValue);
        if(token != null){
            String uid = JwtManager.validateJwtToken(token);
            if(uid != null){
                List<User> users = userService.read();
                return ResponseEntity.ok(users);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@RequestBody User user,@CookieValue(name = "jwt", required = false)String cookieValue) {
        String token = CookiesManager.getCookie(cookieValue);
        if (token != null) {
            String uid = JwtManager.validateJwtToken(token);
            if (uid != null) {
                User u = userService.update(id, user);
                if (u != null) {
                    return ResponseEntity.ok(u);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
    }

        @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,@CookieValue(name = "jwt", required = false)String cookieValue){
            String token = CookiesManager.getCookie(cookieValue);
            if (token != null) {
                String uid = JwtManager.validateJwtToken(token);
                if (uid != null) {
                    User u = userService.delete(id);
                    if (u != null) {
                        return ResponseEntity.ok(u);
                    }
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");

        }
}
