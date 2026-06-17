package cc.kertaskerja.kepegawaian.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
        name = "sessionId",
        type = SecuritySchemeType.APIKEY,
        in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
        paramName = "X-Session-Id"
)
public class OpenApiConfig {
}
