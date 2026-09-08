package cc.kertaskerja.kepegawaian.identity.domain;

public record CreateIdentityRequest(

        String username,

        String email,

        String firstName,

        String lastName,

        boolean enabled,

        IdentityAttributes attributes

) {
}
