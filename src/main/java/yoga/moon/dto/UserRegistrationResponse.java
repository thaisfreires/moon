package yoga.moon.dto;

public class UserRegistrationResponse {
    
    private Long id;
    private String username;
    private String email;


    public UserRegistrationResponse(Long id,String username, String email) {
        this.id=id;
        this.username = username;
        this.email = email;
    }

    
}
