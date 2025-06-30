package yoga.moon.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import yoga.moon.enums.*;

@Entity
public class User {

    public User(String username,String password, String email, Role role){
        this.username=username;
        this.password=password;
        this.email=email;
        this.role=role;
    }

    public User(String email, String password){
        this.email=email;
        this.password=password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String username;

    @NotNull
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private Role role;

    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public Role getRole(){
        return role;
    }
    
}
