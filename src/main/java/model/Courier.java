package model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Courier {
    private String login;
    private String password;
    private String firstName;
}
