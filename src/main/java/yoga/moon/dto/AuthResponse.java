package yoga.moon.dto;

public class AuthResponse {
    private String username;
    private String token;

    public AuthResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public String getUsername(){
        return username;
    }
}
