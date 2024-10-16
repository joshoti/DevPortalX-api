package com.devportalx.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "DevPortalX OpenAPI Specification",
        description = "OpenAPI Documentation for Developer portal",
        version = "1.0"
    ),
    servers = {
        @Server(
            description = "Local URL",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "Production URL",
            url = "TBA"
        )
    },
    security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
    name = "BearerAuth",
    description = "JWT Bearer Token",
    scheme = "Bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
