package cc.kertaskerja.kepegawaian.identity.domain;

import java.util.Collection;
import java.util.Optional;

public interface KeycloakAdminClient {

    Optional<String> findUserIdByUsername(String username);

    CreateUserResult createUser(CreateIdentityRequest request);

    void updateUser(String userId, UpdateIdentityRequest request);

    void enableUser(String userId);

    void disableUser(String userId);

    void resetPassword(
            String userId,
            String password,
            boolean temporary
    );

    void assignRoles(
            String userId,
            Collection<String> roleNames
    );

    void removeRoles(
            String userId,
            Collection<String> roleNames
    );
}
