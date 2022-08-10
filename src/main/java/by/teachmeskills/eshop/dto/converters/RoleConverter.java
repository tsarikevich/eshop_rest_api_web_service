package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.RoleDto;
import by.teachmeskills.eshop.entities.Role;
import by.teachmeskills.eshop.repositories.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class RoleConverter {
    private final RoleRepository roleRepository;

    public RoleConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDto toDto(Role role) {
        return Optional.ofNullable(role).map(r -> RoleDto.builder()
                        .id(r.getId())
                        .name(r.getRole())
                        .build())
                .orElse(null);
    }

    public Role fromDto(RoleDto roleDto) {
        return roleRepository.findById(roleDto.getId()).get();
    }
}
