package cc.kertaskerja.kepegawaian.identity.domain;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepresentationMapper {

    public UserRepresentation toRepresentation(CreateIdentityRequest request) {

        UserRepresentation user = new UserRepresentation();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(request.enabled());
        user.setAttributes(
                toKeycloakAttributes(request.attributes())
        );

        return user;
    }

    public UserRepresentation toRepresentation(UpdateIdentityRequest request) {
        UserRepresentation user = new UserRepresentation();

        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(request.enabled());
        user.setAttributes(
                toKeycloakAttributes(request.attributes())
        );

        return user;
    }

    private Map<String, List<String>> toKeycloakAttributes(
            IdentityAttributes attributes
    ) {
        Map<String, List<String>> result = new HashMap<>();

        result.put("nip", List.of(attributes.nip()));
        result.put("kode_opd", List.of(attributes.kodeOpd()));
        result.put("nama_opd", List.of(attributes.namaOpd()));

        return result;
    }

}
