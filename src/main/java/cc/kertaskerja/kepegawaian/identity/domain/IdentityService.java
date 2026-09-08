package cc.kertaskerja.kepegawaian.identity.domain;

import java.util.List;
import java.util.Set;

public interface IdentityService {
    CreateUserResult createUser(CreateIdentityRequest request);

    void updateUser(String userId, UpdateIdentityRequest request);

    void disableUser(String userId);

    void enableUser(String userId);

    void resetPassword(String userId, String password, boolean temporary);

    void assignRoles(String userId, Set<String> roles);
}
