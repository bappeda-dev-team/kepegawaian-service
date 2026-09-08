package cc.kertaskerja.kepegawaian.identity.domain;

public record CreateUserResult(
        String userId,
        boolean created
) {
}
