package org.example;

import dao.AsociacionDAO;
import dao.JuegoDAO;
import dao.PartidaDAO;
import dao.SociaDAO;
import exportador.ExportadorJSON;
import exportador.ExportadorXML;
import metadatos.GestorMetadatos;
import modelo.Asociacion;
import modelo.Juego;
import modelo.Partida;
import modelo.Socia;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static final JuegoDAO juegoDAO = new JuegoDAO();
    private static final SociaDAO sociaDAO = new SociaDAO();
    private static final PartidaDAO partidaDAO = new PartidaDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenuPrincipal();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> menuJuegos();
                    case 2 -> menuSocias();
                    case 3 -> menuPartidas();
                    case 4 -> exportarDatos();
                    case 5 -> GestorMetadatos.mostrarMetadatosBaseDatos();
                    case 6 -> mostrarInfoAsociacion();
                    case 0 -> System.out.println("¡Gracias por utilizar la aplicación!");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n==================================================");
        System.out.println("   ASOCIACIÓN LUDIKA VITORIA-GASTEIZ - MENÚ");
        System.out.println("==================================================");
        System.out.println("1. Gestión de Juegos ");
        System.out.println("2. Gestión de Socias ");
        System.out.println("3. Gestión de Partidas ");
        System.out.println("4. Exportar Datos (XML / JSON)");
        System.out.println("5. Ver Metadatos de la BBDD");
        System.out.println("6. Ver Información de la Asociación");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // --- SUBMENÚ JUEGOS ---
    private static void menuJuegos() throws Exception {
        System.out.println("\n--- GESTIÓN DE JUEGOS ---");
        System.out.println("1. Listar juegos");
        System.out.println("2. Insertar nuevo juego");
        System.out.println("3. Editar juego");
        System.out.println("4. Eliminar juego (Baja lógica)");
        System.out.print("Opción: ");
        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1 -> listarJuegos();
            case 2 -> registrarNuevoJuego();
            case 3 -> editarJuego();
            case 4 -> bajaJuego();
        }
    }

    // --- SUBMENÚ SOCIAS ---
    private static void menuSocias() throws Exception {
        System.out.println("\n--- GESTIÓN DE SOCIAS ---");
        System.out.println("1. Listar socias");
        System.out.println("2. Registrar nueva socia");
        System.out.println("3. Editar socia");
        System.out.println("4. Dar de baja socia");
        System.out.print("Opción: ");
        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1 -> listarSocias();
            case 2 -> registrarNuevaSocia();
            case 3 -> editarSocia();
            case 4 -> darDeBajaSocia();
        }
    }

    // --- SUBMENÚ PARTIDAS ---
    private static void menuPartidas() throws Exception {
        System.out.println("\n--- GESTIÓN DE PARTIDAS ---");
        System.out.println("1. Listar partidas");
        System.out.println("2. Crear nueva partida");
        System.out.println("3. Editar partida");
        System.out.println("4. Apuntarse a una partida");
        System.out.println("5. Cancelar partida");
        System.out.print("Opción: ");
        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1 -> listarPartidas();
            case 2 -> crearPartida();
            case 3 -> editarPartida();
            case 4 -> apuntarseAPartida();
            case 5 -> cancelarPartida();
        }
    }

    // --- MÉTODOS EDITAR ---
    private static void editarJuego() {
        try {
            System.out.print("ID del juego a editar: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nuevo Nombre: "); String nom = scanner.nextLine();
            System.out.print("Nuevos Autores: "); String aut = scanner.nextLine();
            System.out.print("Nueva Descripción: "); String desc = scanner.nextLine();
            System.out.print("Min Jugadores: "); int min = Integer.parseInt(scanner.nextLine());
            System.out.print("Max Jugadores: "); int max = Integer.parseInt(scanner.nextLine());
            System.out.print("Duración estimada (min): "); int dur = Integer.parseInt(scanner.nextLine());
            System.out.print("Nueva Temática: "); String tem = scanner.nextLine();
            System.out.print("Nuevo Tipo: "); String tipo = scanner.nextLine();
            System.out.print("Nueva Ubicación: "); String ubi = scanner.nextLine();
            System.out.print("Nuevo Estado: "); String est = scanner.nextLine();
            System.out.print("Tipo Propietario (asociacion/socia): "); String propTipo = scanner.nextLine();

            String propDni = null;
            if (propTipo.equalsIgnoreCase("socia")) {
                System.out.print("DNI Socia Propietaria: ");
                propDni = scanner.nextLine();
            }

            Juego j = new Juego(id, nom, aut, desc, min, max, dur, tem, tipo, ubi, est, propTipo, propDni, new Date(System.currentTimeMillis()));
            juegoDAO.editarJuego(j);
            System.out.println("¡Juego editado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al editar el juego: " + e.getMessage());
        }
    }

    private static void editarSocia() {
        try {
            System.out.print("DNI de la socia a editar: "); String dni = scanner.nextLine();
            System.out.print("Nuevo Nombre: "); String nom = scanner.nextLine();
            System.out.print("Nuevos Apellidos: "); String ape = scanner.nextLine();
            System.out.print("Nuevo Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Nueva Edad: "); int edad = Integer.parseInt(scanner.nextLine());
            System.out.print("Nuevo Email: "); String em = scanner.nextLine();

            Socia s = new Socia(dni, nom, ape, tel, edad, em);
            sociaDAO.editarSocia(s);
            System.out.println("¡Socia editada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al editar la socia: " + e.getMessage());
        }
    }

    private static void editarPartida() {
        try {
            System.out.print("ID de la partida a editar: "); int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nueva Fecha (YYYY-MM-DD): "); Date f = Date.valueOf(scanner.nextLine());
            System.out.print("Nueva Hora (HH:MM:SS): "); Time h = Time.valueOf(scanner.nextLine());
            System.out.print("Nuevos Jugadores deseados: "); int des = Integer.parseInt(scanner.nextLine());
            System.out.print("Ganadoras (o dejar en blanco): "); String gan = scanner.nextLine();

            partidaDAO.editarPartida(id, f, h, des, gan);
            System.out.println("¡Partida editada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al editar la partida: " + e.getMessage());
        }
    }

    // --- OTROS MÉTODOS AUXILIARES ---
    private static void listarJuegos() throws Exception {
        List<Juego> juegos = juegoDAO.listarJuegos();
        System.out.println("\n--- LISTADO DE JUEGOS ---");
        juegos.forEach(System.out::println);
    }

    private static void registrarNuevoJuego() {
        try {
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Autores: "); String aut = scanner.nextLine();
            System.out.print("Descripción: "); String desc = scanner.nextLine();
            System.out.print("Min Jugadores: "); int min = Integer.parseInt(scanner.nextLine());
            System.out.print("Max Jugadores: "); int max = Integer.parseInt(scanner.nextLine());
            System.out.print("Duración estimada (min): "); int dur = Integer.parseInt(scanner.nextLine());
            System.out.print("Temática: "); String tem = scanner.nextLine();
            System.out.print("Tipo: "); String tipo = scanner.nextLine();
            System.out.print("Ubicación: "); String ubi = scanner.nextLine();
            System.out.print("Estado: "); String est = scanner.nextLine();
            System.out.print("Tipo Propietario (asociacion/socia): "); String propTipo = scanner.nextLine();
            String propDni = propTipo.equalsIgnoreCase("socia") ? scanner.nextLine() : null;

            Juego j = new Juego(0, nom, aut, desc, min, max, dur, tem, tipo, ubi, est, propTipo, propDni, new Date(System.currentTimeMillis()));
            juegoDAO.insertarJuego(j);
            System.out.println("¡Juego insertado con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bajaJuego() {
        try {
            System.out.print("ID del juego a dar de baja: "); int id = Integer.parseInt(scanner.nextLine());
            juegoDAO.bajaLogicaJuego(id);
            System.out.println("¡Juego dado de baja con éxito!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void listarSocias() throws Exception {
        List<Socia> socias = sociaDAO.listarSocias();
        System.out.println("\n--- LISTADO DE SOCIAS ---");
        socias.forEach(System.out::println);
    }

    private static void registrarNuevaSocia() {
        try {
            System.out.print("DNI: "); String dni = scanner.nextLine();
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Apellidos: "); String ape = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Edad: "); int edad = Integer.parseInt(scanner.nextLine());
            System.out.print("Email: "); String email = scanner.nextLine();

            Socia s = new Socia(dni, nom, ape, tel, edad, email);
            sociaDAO.insertarSocia(s);
            System.out.println("¡Socia registrada!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void darDeBajaSocia() {
        try {
            System.out.print("DNI de la socia: "); String dni = scanner.nextLine();
            sociaDAO.bajaSocia(dni);
            System.out.println("¡Socia dada de baja!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void listarPartidas() throws Exception {
        List<Partida> partidas = partidaDAO.listarPartidas();
        System.out.println("\n--- LISTADO DE PARTIDAS ---");
        partidas.forEach(p -> {
            System.out.println(p);
            if (p.getJugadorasInscritas() != null && !p.getJugadorasInscritas().isEmpty()) {
                System.out.println("   Jugadoras: " + String.join(", ", p.getJugadorasInscritas()));
            }
        });
    }

    private static void crearPartida() {
        try {
            System.out.print("ID del Juego: "); int idJ = Integer.parseInt(scanner.nextLine());
            System.out.print("Fecha (YYYY-MM-DD): "); Date f = Date.valueOf(scanner.nextLine());
            System.out.print("Hora (HH:MM:SS): "); Time h = Time.valueOf(scanner.nextLine());
            System.out.print("Jugadores deseados: "); int des = Integer.parseInt(scanner.nextLine());

            partidaDAO.crearPartida(idJ, f, h, des);
            System.out.println("¡Partida creada!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void apuntarseAPartida() {
        try {
            System.out.print("ID de la partida: "); int idP = Integer.parseInt(scanner.nextLine());
            System.out.print("DNI de la socia: "); String dni = scanner.nextLine();
            partidaDAO.inscribirSociaEnPartida(idP, dni);
            System.out.println("¡Inscripción realizada!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void cancelarPartida() {
        try {
            System.out.print("ID de la partida: "); int idP = Integer.parseInt(scanner.nextLine());
            partidaDAO.cancelarPartida(idP);
            System.out.println("¡Partida cancelada!");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void exportarDatos() throws Exception {
        List<Juego> juegos = juegoDAO.listarJuegos();
        List<Socia> socias = sociaDAO.listarSocias();
        List<Partida> partidas = partidaDAO.listarPartidas();

        ExportadorXML.exportarJuegosXML(juegos, "juegos.xml");
        ExportadorJSON.exportarAJSON(juegos, "juegos.json");
        ExportadorJSON.exportarAJSON(socias, "socias.json");
        ExportadorJSON.exportarAJSON(partidas, "partidas.json");

        System.out.println("\n¡Archivos XML y JSON generados con éxito!");
    }

    private static void mostrarInfoAsociacion() throws Exception {
        Asociacion asoc = AsociacionDAO.obtenerDatosAsociacion();
        if (asoc != null) {
            System.out.println("\n--- INFORMACIÓN DE LA ASOCIACIÓN ---");
            System.out.println(asoc);
        } else {
            System.out.println("No se encontraron registros.");
        }
    }
}