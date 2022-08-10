package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "ROLE",unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getRole();
    }
}
