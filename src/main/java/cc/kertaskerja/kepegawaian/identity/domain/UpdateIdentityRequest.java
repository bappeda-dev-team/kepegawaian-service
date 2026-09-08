package cc.kertaskerja.kepegawaian.identity.domain;

public record UpdateIdentityRequest(

        String email,

        String firstName,

        String lastName,

        boolean enabled,

        IdentityAttributes attributes
) {
}
