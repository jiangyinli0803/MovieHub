package org.example.backend.security;
import org.example.backend.model.user.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // System.out.println("loadUserByUsername called with: " + email); 登录请求传入的是 email, 不是 ID！

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + email));

        return UserPrincipal.create(user);
    }





}
