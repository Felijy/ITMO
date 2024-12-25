package web.lab.web41;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.findByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .map(u -> ResponseEntity.ok("Login successful"))
                .orElse(ResponseEntity.status(401).body("Invalid username or password"));
    }

            @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("User already exists");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<String> checkLogin(@RequestBody User user) {
        return userService.findByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .map(u -> ResponseEntity.ok("OK"))
                .orElse(ResponseEntity.status(401).body("Invalid username or password"));
    }
}