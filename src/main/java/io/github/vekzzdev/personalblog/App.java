import io.github.vekzzdev.personalblog.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;

void main() {

    var port = Integer.parseInt(
            System.getenv().getOrDefault("PORT", "7070")
    );

    Storage storage = new Storage();
    ArticleService service = new ArticleService(storage);
    HomeController homeController = new HomeController(service);
    AdminController adminController = new AdminController(service);

    var app = Javalin.create(config -> {

        //Habilitar static files
        config.staticFiles.add("/static", Location.CLASSPATH);

        //Renderizar JTE (plantillas precompiladas en build)
        config.fileRenderer(new JavalinJte(TemplateEngine.createPrecompiled(ContentType.Html)));

        //Http basic auth
        config.routes.beforeMatched("/admin/", BasicAuth::authenticate);
        config.routes.beforeMatched("/admin/*", BasicAuth::authenticate);

        //Rutas
        config.routes.apiBuilder(homeController::registerRoutes);
        config.routes.apiBuilder(adminController::registerRoutes);

    }).start("0.0.0.0", port);
}