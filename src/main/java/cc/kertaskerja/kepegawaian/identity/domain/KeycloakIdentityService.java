package cc.kertaskerja.kepegawaian.identity.domain;

import jakarta.validation.Valid;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class KeycloakIdentityService implements IdentityService {
    private final KeycloakAdminClient keycloakAdminClient;

    public KeycloakIdentityService(KeycloakAdminClient keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }

    @Override
    public CreateUserResult createUser(@Valid CreateIdentityRequest request) {
        return keycloakAdminClient.createUser(request);
    }

    @Override
    public void updateUser(
            String userId,
            @Valid UpdateIdentityRequest request) {
        keycloakAdminClient.updateUser(userId, request);
    }

    @Override
    public void disableUser(String userId) {
        keycloakAdminClient.disableUser(userId);
    }

    @Override
    public void enableUser(String userId) {
        keycloakAdminClient.enableUser(userId);
    }

    @Override
    public void resetPassword(
            String userId,
            String password,
            boolean temporary) {
        keycloakAdminClient.resetPassword(
                userId,
                password,
                temporary);
    }

    @Override
    public void assignRoles(
            String userId,
            Set<String> roles) {
        keycloakAdminClient.assignRoles(
                userId,
                roles);
    }

    @Override
    public List<User> getUsers() {
        return keycloakAdminClient.findUsers()
                .stream()
                .map(this::toUser)
                .toList();
    }

    private User toUser(UserRepresentation representation) {
        return new User(
                representation.getUsername(),
                representation.getEmail(),
                keycloakAdminClient.findRoles(
                        representation.getId()),
                getAttribute(representation, "kode_opd"),
                getAttribute(representation, "nama_opd"),
                Boolean.TRUE.equals(representation.isEnabled()));
    }

    private String getAttribute(
            UserRepresentation user,
            String name) {
        if (user.getAttributes() == null) {
            return null;
        }

        var values = user.getAttributes().get(name);

        return values == null || values.isEmpty()
                ? null
                : values.getFirst();
    }
}
