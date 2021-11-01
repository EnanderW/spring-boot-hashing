package me.code.springproducts.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    Map<String, User> tokens = new HashMap<>();

    public int registerUser(UserController.UserCreate user) {

        User existing = userRepository.get(user.getUsername());
        if (existing != null)
            return 1;


        String salt = generateSalt();
        String hash = hashPassword(user.getPassword(), salt);

        userRepository.save(new User(user.getUsername(), hash, salt));

        return 0;
    }

    public String login(String username, String password) {
        User user = userRepository.get(username);
        if (user == null)
            return null;


        String salt = user.getSalt();
        String hash = hashPassword(password, salt);

        if (!user.getPassword().equals(hash))
            return null;

        String token = UUID.randomUUID().toString();
        tokens.put(token, user);
        return token;
    }

    public void logout(String token) {
        tokens.remove(token);
    }

    public User validate(String token) {
        return tokens.get(token);
    }

    public static String generateSalt() {
        return Math.random() + "friuehfiuv" + Math.random() + ((char)(Math.random() * 100));
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            digest.update(password.getBytes());
            digest.update(salt.getBytes());

            return bytesToHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
