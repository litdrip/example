package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import persistent.UserRepository;

import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
public class DatabaseUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<persistent.User> list = userRepository.findByName(name);
        if (CollectionUtils.isEmpty(list)) {
            throw new UsernameNotFoundException(name);
        } else {
            persistent.User org = list.get(0);
            return new User(org.getName(), org.getPassword(), Collections.singleton(new SimpleGrantedAuthority(org.getRole())));
        }
    }

    public void createUser(String name, String password) {
        persistent.User user = new persistent.User();
        user.setName(name);
        user.setPassword(pe.encode(password));
        user.setRole("USER");
        userRepository.save(user);
    }
}
