package yoga.moon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import yoga.moon.enums.Role;

public class UserRegistrationRequest {
    
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private Role role;

    public UserRegistrationRequest(String username, String email, String password, Role role){
        this.username=username;
        this.email=email;
        this.password=password;
        this.role=role;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole(){
        return role;
    }
    
}
