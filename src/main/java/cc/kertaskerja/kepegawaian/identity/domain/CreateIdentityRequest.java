package cc.kertaskerja.kepegawaian.identity.domain;

import java.util.List;
import java.util.Map;

public record CreateIdentityRequest(

        String username,

        String email,

        String firstName,

        String lastName,

        boolean enabled,

        Map<String, List<String>> attributes

) {
}
