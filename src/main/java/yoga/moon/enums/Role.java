package yoga.moon.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN("admin"), 
    USER("user");

    private String role;

    Role(String role){
        this.role=role;
    }
    public String getRole(){
        return role;
    }

    @JsonCreator
    public static Role from(String value) {
        for (Role role : Role.values()) {
            if (role.getRole().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Role inválido: " + value);
    }
     
}
