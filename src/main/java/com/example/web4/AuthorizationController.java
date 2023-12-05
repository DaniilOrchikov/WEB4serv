package com.example.web4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {
    private UserRepository userRepository;

    @Autowired
    public AuthorizationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Boolean> form(@RequestBody UserForm userForm) throws NoSuchAlgorithmException {
        User user = new User(userForm.name(), userForm.login());
        user.setSalt(generateSalt());
        user.setHashedPassword(hashPassword(userForm.password(), user.getSalt()));
        try {
            userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            System.out.println("error");
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @DeleteMapping
    public void clear() {
        userRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void clearByLogin(@PathVariable String id) {
        userRepository.deleteById(Long.parseLong(id));
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        String saltedPassword = password + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(saltedPassword.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean checkPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException {
        String hashedInput = hashPassword(password, salt);
        return hashedInput.equals(hashedPassword);
    }
}
