package cc.kertaskerja.kepegawaian.identity.domain;

import cc.kertaskerja.kepegawaian.config.IdentityProperties;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class KeycloakAdminClientImpl implements KeycloakAdminClient {

    private final Keycloak keycloak;
    private final IdentityProperties properties;
    private final UserRepresentationMapper mapper;


    public KeycloakAdminClientImpl(Keycloak keycloak, IdentityProperties properties, UserRepresentationMapper mapper) {
        this.keycloak = keycloak;
        this.properties = properties;
        this.mapper = mapper;
    }

    private RealmResource realm() {
        return keycloak.realm(properties.keycloak().realm());
    }

    @Override
    public String createUser(CreateIdentityRequest request) {

        UserRepresentation user = mapper.toRepresentation(request);

        try(Response response = realm().users().create(user)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new IdentityException(
                        "Unable to create user. Status = " + response.getStatus()
                );
            }
            return CreatedResponseUtil.getCreatedId(response);
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
