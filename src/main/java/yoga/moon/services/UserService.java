package yoga.moon.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import yoga.moon.dto.UserRegistrationRequest;
import yoga.moon.dto.UserRegistrationResponse;
import yoga.moon.model.User;
import yoga.moon.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());
        
        return
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest dto) {

        User user = requestToUser(dto);
        return userToResponse(repository.save(user));
    }

    private User requestToUser(UserRegistrationRequest request){
        return new User(
                request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getRole()
        );
    }

    private UserRegistrationResponse userToResponse(User user){
        return new UserRegistrationResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail());
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
}
