package cc.kertaskerja.kepegawaian.identity.domain;

import jakarta.validation.Valid;
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
            @Valid UpdateIdentityRequest request
    ) {
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
            boolean temporary
    ) {
        keycloakAdminClient.resetPassword(
                userId,
                password,
                temporary
        );
    }

    @Override
    public void assignRoles(
            String userId,
            Set<String> roles
    ) {
        keycloakAdminClient.assignRoles(
                userId,
                roles
        );
    }
}
