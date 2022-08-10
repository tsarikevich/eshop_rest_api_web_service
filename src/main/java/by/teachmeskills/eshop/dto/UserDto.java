package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotBlank(message = "Login must be entered")
    private String login;
    @NotBlank(message = "Password must be entered")
    private String password;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private BigDecimal balance;
    private List<OrderDto> orders;
    private Set<RoleDto> roles;
}
