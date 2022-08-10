package by.teachmeskills.eshop.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "users")
public class User extends BaseEntity implements UserDetails{
    @CsvBindByName
    @NotEmpty(message = "Login must not be empty")
    @Size(min = 2, max = 30, message = "Login must be between 2 and 30 characters")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    @Column(name = "LOGIN")
    private String login;
    @CsvBindByName
    @NotEmpty(message = "Password must not be empty")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    @Column(name = "PASSWORD")
    private String password;
    @CsvBindByName
    @Column(name = "NAME")
    private String name;
    @CsvBindByName
    @Column(name = "SURNAME")
    private String surname;
    @CsvBindByName
    @Column(name = "EMAIL")
    private String email;
    @CsvBindByName
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate birthDate;
    @CsvBindByName
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns ={@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    @ToString.Exclude
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(birthDate, user.birthDate) && Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, name, surname, email, birthDate, balance);
    }

    public User fromUserEntityToCustomUserDetails(User userEntity) {
        User userDetails = new User();
        userDetails.login = userEntity.getLogin();
        userDetails.password = userEntity.getPassword();
        userDetails.roles = userEntity.getRoles();
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
