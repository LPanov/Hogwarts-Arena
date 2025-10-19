package main.web.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import main.model.House;
import main.model.WizardAlignment;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 6, max = 12)
    private String username;

    @NotBlank
    @Digits(integer = 6, fraction = 0)
    private String password;

    @NotBlank
    @URL
    private String avatarUrl;

    private House house;

    private WizardAlignment alignment;
}
