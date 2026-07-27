package cc.kertaskerja.kepegawaian.identity.domain;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class UserRepresentationMapper {

    public UserRepresentation toRepresentation(CreateIdentityRequest request) {

        UserRepresentation user = new UserRepresentation();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(request.enabled());
        user.setAttributes(request.attributes());

        return user;
    }

    public UserRepresentation toRepresentation(UpdateIdentityRequest request) {
        UserRepresentation user = new UserRepresentation();

        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(request.enabled());
        user.setAttributes(request.attributes());

        return user;
    }

}
