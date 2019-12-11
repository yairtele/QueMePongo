package Server;

import Controllers.CalendarioController;
import Controllers.GuardarropaController;
import Controllers.HomeController;
import Controllers.InicioController;
import Utils.BooleanHelper;
import Utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Router {

    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){

        //Instancio los controladores necesarios
        InicioController inicio = new InicioController();
        HomeController home = new HomeController();
        CalendarioController calendario = new CalendarioController();
        GuardarropaController guardarropa = new GuardarropaController();


        //UsuarioController usuarioController = new UsuarioController();

        //Spark.get("/usuarios", usuarioController::mostrarTodos, Router.engine);

        //Spark.get("/usuario/:id", usuarioController::mostrar, Router.engine);

        //Spark.get("/usuario", usuarioController::crear, Router.engine);

        //Spark.post("/usuario/:id", usuarioController::modificar);

        //Spark.post("/usuario", usuarioController::guardar);

        //Spark.delete("/usuario/:id", usuarioController::eliminar);

        //Rutas
        get("/", inicio::inicio, Router.engine);
        get("/login", inicio::loginView, Router.engine);
        get("/404", inicio::notFound, Router.engine);
        post("/login", inicio::login, Router.engine);
        post("/logout", inicio::logout, Router.engine);
        get("/register", inicio::registerView, Router.engine);
        post("/register", inicio::register, Router.engine);

        get("/home", home::inicio, Router.engine);

        get("/calendar", calendario::calendarView, Router.engine);
        get("/calendar/:month/:year", calendario::customizedCalendarView, Router.engine);

        get("/guardarropa/:id", guardarropa::mostrar, Router.engine);

        notFound((req, res) -> {
            res.redirect("/404");
            return "{\"message\":\"Custom 404\"}";
        });

        //get("/", (req, res) -> new Inicio().inicio());

        //Que carajo hace esto?
        //Spark.after((req, res) -> {
            //PerThreadEntityManagers.closeEntityManager();
        //});
    }
}
