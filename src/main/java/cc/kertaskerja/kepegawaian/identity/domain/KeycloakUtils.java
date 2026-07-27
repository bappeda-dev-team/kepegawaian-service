package cc.kertaskerja.kepegawaian.identity.domain;

import jakarta.ws.rs.core.Response;


public final class KeycloakUtils {

    private KeycloakUtils() {}

    public static String extractUserId(Response response) {
        String location = response.getHeaderString("Location");
        return location.substring(location.lastIndexOf('/') + 1);
    }

}
