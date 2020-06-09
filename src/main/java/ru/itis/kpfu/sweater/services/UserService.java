package ru.itis.kpfu.sweater.services;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.sweater.domains.Role;
import ru.itis.kpfu.sweater.domains.User;
import ru.itis.kpfu.sweater.repositories.UserRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if(user.getEmail() != null){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public Object findAll() {
        return userRepository.findAll();
    }

    public void save(String username, Map<String, String> form, User user) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());Collectors.toSet();

        user.getRoles().clear();
        for (String key: form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
         String userEmail = user.getEmail();

         boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                 (userEmail != null && !userEmail.equals(email));

         if(isEmailChanged){
             user.setEmail(email);

             if(email != null){
                 user.setActivationCode(UUID.randomUUID().toString());
             }
         }

         if(password != null){
             user.setPassword(password);
         }

         userRepository.save(user);

         if(isEmailChanged){
             sendMessage(user);
         }
    }
}
