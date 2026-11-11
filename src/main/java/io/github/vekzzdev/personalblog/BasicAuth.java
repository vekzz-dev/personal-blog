package io.github.vekzzdev.personalblog;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;

import java.util.Map;

public class BasicAuth {

    public static void authenticate(Context ctx) {
        var credentials = ctx.basicAuthCredentials();

        var envCredentials = getEnvCrendentials();

        if (credentials == null ||
                !credentials.getUsername().equals(envCredentials.get("username")) ||
                !credentials.getPassword().equals(envCredentials.get("password"))) {

            ctx.header(
                    Header.WWW_AUTHENTICATE,
                    "Basic realm=\"Admin\", charset=\"UTF-8\""
            );

            throw new UnauthorizedResponse();
        }
    }

    private static Map<String, String> getEnvCrendentials() {
        var usernameKey = "PERSONAL_BLOG_USER";
        var passwordKey = "PERSONAL_BLOG_PASSWORD";

        var username = System.getenv(usernameKey);
        var password = System.getenv(passwordKey);

        if (username == null || password == null) {
            var env = Dotenv.configure()
                    .filename("config.env")
                    .ignoreIfMissing()
                    .load();

            username = env.get(usernameKey);
            password = env.get(passwordKey);
        }
        return Map.of("username", username, "password", password);
    }
}
