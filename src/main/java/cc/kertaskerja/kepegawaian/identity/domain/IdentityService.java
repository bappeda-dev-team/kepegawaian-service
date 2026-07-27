package cc.kertaskerja.kepegawaian.identity.domain;

import java.util.List;

public interface IdentityService {
    String createUser(CreateIdentityRequest request);

    void updateUser(String userId, UpdateIdentityRequest request);

    void disableUser(String userId);

    void enableUser(String userId);

    void resetPassword(String userId, String password, boolean temporary);

    void assignRoles(String userId, List<String> roles);

}
