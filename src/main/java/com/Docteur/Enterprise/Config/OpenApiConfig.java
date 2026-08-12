package com.Docteur.Enterprise.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SMARTPHONE REPAIR ENTERPRISE",
                version = "1.0",
                description = "API sécurisée avec JWT",
                contact = @Contact(
                        name = "Support API",
                        email = "support@docteur-enterprise.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(
                        url = "http://localhost:8080/api/enterprise/v1",
                        description = "Development Server"
                ),
                @Server(
                        url = "https://api.docteur-enterprise.com/api/enterprise/v1",
                        description = "Production Server"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT authentication token"
)

public class OpenApiConfig {

}
