package cc.kertaskerja.kepegawaian.identity.domain;

import cc.kertaskerja.kepegawaian.config.IdentityProperties;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class KeycloakAdminClientImpl implements KeycloakAdminClient {

    private final Keycloak keycloak;
    private final IdentityProperties properties;
    private final UserRepresentationMapper mapper;
    private final Logger log = LoggerFactory.getLogger(KeycloakAdminClientImpl.class);


    public KeycloakAdminClientImpl(Keycloak keycloak, IdentityProperties properties, UserRepresentationMapper mapper) {
        this.keycloak = keycloak;
        this.properties = properties;
        this.mapper = mapper;
    }

    private RealmResource realm() {
        return keycloak.realm(properties.keycloak().realm());
    }

    @Override
    public Optional<String> findUserIdByUsername(String username) {
        // find users
        List<UserRepresentation> users = realm().users()
                .searchByUsername(username, true);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        if (users.size() > 1) {
            throw new IdentityException("Multiple users found for username: " + username);
        }

        return Optional.of(users.getFirst().getId());
    }

    @Override
    public CreateUserResult createUser(CreateIdentityRequest request) {

        UserRepresentation user = mapper.toRepresentation(request);

        // check existing user in keycloak
        Optional<String> existingUserId = findUserIdByUsername(user.getUsername());

        if (existingUserId.isPresent()) {
            String userId = existingUserId.get();
            user.setId(userId);

            realm().users().get(userId).update(user);
            return new CreateUserResult(userId, false);
        }

        try(Response response = realm().users().create(user)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new IdentityException(
                        "Unable to create user. Status = " + response.getStatus()
                );
            }
            String newUserId =  CreatedResponseUtil.getCreatedId(response);

            return new CreateUserResult(newUserId, false);
        }
    }

    @Override
    public void updateUser(String userId, UpdateIdentityRequest request) {

        UserRepresentation user = mapper.toRepresentation(request);

        realm()
                .users()
                .get(userId)
                .update(user);
    }

    @Override
    public void enableUser(String userId) {

        UserResource user = realm()
                .users()
                .get(userId);

        UserRepresentation rep = user.toRepresentation();

        rep.setEnabled(true);

        user.update(rep);
    }

    @Override
    public void disableUser(String userId) {

        UserRepresentation rep = user(userId).toRepresentation();

        rep.setEnabled(false);

        user(userId).update(rep);
    }

    @Override
    public void resetPassword(
            String userId,
            String password,
            boolean temporary
    ) {

        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);

        credential.setValue(password);

        credential.setTemporary(temporary);

        user(userId).resetPassword(credential);
    }

    @Override
    public void assignRoles(
            String userId,
            Collection<String> roleNames
    ) {

        List<RoleRepresentation> roles =
                roleNames.stream()
                        .map(name -> realm().roles().get(name).toRepresentation())
                        .toList();

        realm()
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roles);
    }

    @Override
    public void removeRoles(
            String userId,
            Collection<String> roleNames
    ) {

        List<RoleRepresentation> roles =
                roleNames.stream()
                        .map(name -> realm().roles().get(name).toRepresentation())
                        .toList();

        realm()
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .remove(roles);
    }

    private UserResource user(String userId) {
        return realm().users().get(userId);
    }
}
