package cc.kertaskerja.kepegawaian.identity.domain;

public record CreateUserResult(
        String userId,
        Boolean created
) {
}
