package org.example;


import dao.*;
import exportador.*;
import metadatos.GestorMetadatos;
import modelo.*;

import java.sql.Time;
import java.util.Date;
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
                    case 1 -> listarJuegos();
                    case 2 -> registrarNuevoJuego();
                    case 3 -> cambiarEstadoJuego();
                    case 4 -> bajaJuego();
                    case 5 -> listarSocias();
                    case 6 -> registrarNuevaSocia();
                    case 7 -> actualizarSocia();
                    case 8 -> darDeBajaSocia();
                    case 9 -> listarPartidas();
                    case 10 -> crearPartida();
                    case 11 -> apuntarseAPartida();
                    case 12 -> registrarGanadorasPartida();
                    case 13 -> cancelarPartida();
                    case 14 -> exportarDatos();
                    case 15 -> GestorMetadatos.mostrarMetadatosBaseDatos();
                    case 16 -> mostrarInfoAsociacion();
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
        System.out.println("--- GESTIÓN DE JUEGOS ---");
        System.out.println(" 1. Listar juegos");
        System.out.println(" 2. Insertar nuevo juego");
        System.out.println(" 3. Actualizar estado de juego");
        System.out.println(" 4. Dar de baja un juego");
        System.out.println("--- GESTIÓN DE SOCIAS ---");
        System.out.println(" 5. Listar socias");
        System.out.println(" 6. Registrar nueva socia");
        System.out.println(" 7. Actualizar teléfono/email de socia");
        System.out.println(" 8. Dar de baja una socia");
        System.out.println("--- GESTIÓN DE PARTIDAS ---");
        System.out.println(" 9. Listar partidas");
        System.out.println("10. Crear nueva partida");
        System.out.println("11. Apuntarse a una partida");
        System.out.println("12. Registrar ganadoras de partida");
        System.out.println("13. Cancelar partida");
        System.out.println("--- EXPORTACIÓN Y METADATOS ---");
        System.out.println("14. Exportar Datos (XML / JSON)");
        System.out.println("15. Ver Metadatos de la BBDD");
        System.out.println("16. Ver Información de la Asociación");
        System.out.println(" 0. Salir");
        System.out.print("Seleccione una opción: ");
    }


    // ACCIONES JUEGO

    private static void listarJuegos() throws Exception {
        List<Juego> juegos = juegoDAO.listarJuegos();
        System.out.println("\n--- LISTADO DE JUEGOS ---");
        juegos.forEach(System.out::println);
    }

    private static void registrarNuevoJuego() {
        try {
            System.out.print("Nombre: ");
            String nom = scanner.nextLine();
            System.out.print("Autores: ");
            String aut = scanner.nextLine();
            System.out.print("Descripción: ");
            String desc = scanner.nextLine();
            System.out.print("Min Jugadores: ");
            int min = Integer.parseInt(scanner.nextLine());
            System.out.print("Max Jugadores: ");
            int max = Integer.parseInt(scanner.nextLine());
            System.out.print("Duración estimada (min): ");
            int dur = Integer.parseInt(scanner.nextLine());
            System.out.print("Temática: ");
            String tem = scanner.nextLine();
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine();
            System.out.print("Ubicación: ");
            String ubi = scanner.nextLine();
            System.out.print("Estado (disponible/prestado): ");
            String est = scanner.nextLine();
            System.out.print("Tipo Propietario (asociacion/socia): ");
            String propTipo = scanner.nextLine();

            String propDni = null;
            if (propTipo.equalsIgnoreCase("socia")) {
                System.out.print("DNI Socia Propietaria: ");
                propDni = scanner.nextLine();
            }

            Juego j = new Juego(0, nom, aut, desc, min, max, dur, tem, tipo, ubi, est, propTipo, propDni, new Date(System.currentTimeMillis()));
            juegoDAO.insertarJuego(j);
            System.out.println("¡Juego insertado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al insertar el juego: " + e.getMessage());
        }
    }

    private static void cambiarEstadoJuego() {
        try {
            System.out.print("ID del juego: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nuevo estado: ");
            String est = scanner.nextLine();
            juegoDAO.actualizarEstadoJuego(id, est);
            System.out.println("¡Estado actualizado!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bajaJuego() {
        try {
            System.out.print("ID del juego a dar de baja: ");
            int id = Integer.parseInt(scanner.nextLine());
            juegoDAO.bajaLogicaJuego(id);
            System.out.println("¡Juego dado de baja con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ACCIONES SOCIA

    private static void listarSocias() throws Exception {
        List<Socia> socias = sociaDAO.listarSocias();
        System.out.println("\n--- LISTADO DE SOCIAS ---");
        socias.forEach(System.out::println);
    }

    private static void registrarNuevaSocia() {
        try {
            System.out.println("\n--- REGISTRO DE NUEVA SOCIA ---");
            System.out.print("DNI: ");
            String dni = scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Edad: ");
            int edad = Integer.parseInt(scanner.nextLine());
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Socia nuevaSocia = new Socia(dni, nombre, apellidos, telefono, edad, email);
            sociaDAO.insertarSocia(nuevaSocia);
            System.out.println("¡Socia registrada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al registrar la socia: " + e.getMessage());
        }
    }

    private static void actualizarSocia() {
        try {
            System.out.print("DNI de la socia: ");
            String dni = scanner.nextLine();
            System.out.print("Nuevo teléfono: ");
            String tel = scanner.nextLine();
            System.out.print("Nuevo email: ");
            String em = scanner.nextLine();
            sociaDAO.actualizarSocia(dni, tel, em);
            System.out.println("¡Socia actualizada con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void darDeBajaSocia() {
        try {
            System.out.print("DNI de la socia a dar de baja: ");
            String dni = scanner.nextLine();
            sociaDAO.bajaSocia(dni);
            System.out.println("¡Socia dada de baja con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ACCIONES PARTIDA

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
            System.out.print("ID del Juego: ");
            int idJ = Integer.parseInt(scanner.nextLine());
            System.out.print("Fecha (YYYY-MM-DD): ");
            java.sql.Date f = java.sql.Date.valueOf(scanner.nextLine());
            System.out.print("Hora (HH:MM:SS): ");
            Time h = Time.valueOf(scanner.nextLine());
            System.out.print("Jugadores deseados: ");
            int des = Integer.parseInt(scanner.nextLine());
            partidaDAO.crearPartida(idJ, f, h, des);
            System.out.println("¡Partida creada con éxito!");
        } catch (Exception e) {
            System.out.println("Error al crear partida: " + e.getMessage());
        }
    }

    private static void apuntarseAPartida() {
        try {
            System.out.print("Introduce el ID de la partida: ");
            int idPartida = Integer.parseInt(scanner.nextLine());
            System.out.print("Introduce el DNI de la socia: ");
            String dniSocia = scanner.nextLine();

            partidaDAO.inscribirSociaEnPartida(idPartida, dniSocia);
            System.out.println("¡Inscripción realizada con éxito!");
        } catch (Exception e) {
            System.out.println("No se pudo realizar la inscripción: " + e.getMessage());
        }
    }

    private static void registrarGanadorasPartida() {
        try {
            System.out.print("ID de la partida: ");
            int idP = Integer.parseInt(scanner.nextLine());
            System.out.print("Ganadoras: ");
            String gan = scanner.nextLine();
            partidaDAO.registrarGanadoras(idP, gan);
            System.out.println("¡Ganadoras registradas!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelarPartida() {
        try {
            System.out.print("ID de la partida a cancelar: ");
            int idP = Integer.parseInt(scanner.nextLine());
            partidaDAO.cancelarPartida(idP);
            System.out.println("¡Partida cancelada con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // OTROS

    private static void exportarDatos() throws Exception {
        List<Juego> juegos = juegoDAO.listarJuegos();
        List<Socia> socias = sociaDAO.listarSocias();
        List<Partida> partidas = partidaDAO.listarPartidas();

        ExportadorXML.exportarJuegosXML(juegos, "juegos.xml");
        ExportadorJSON.exportarAJSON(juegos, "juegos.json");
        ExportadorJSON.exportarAJSON(socias, "socias.json");
        ExportadorJSON.exportarAJSON(partidas, "partidas.json");

        System.out.println("¡Datos exportados con éxito a archivos XML y JSON!");
    }

    private static void mostrarInfoAsociacion() throws Exception {
        Asociacion asoc = AsociacionDAO.obtenerDatosAsociacion();
        System.out.println("\n--- INFORMACIÓN INSTITUCIONAL ---");
        if (asoc != null) {
            System.out.println("Nombre: " + asoc.getNombre());
            System.out.println("CIF: " + asoc.getCif());
            System.out.println("Razón Social: " + asoc.getRazonSocial());
            System.out.println("Dirección: " + asoc.getDireccion());
            System.out.println("Teléfono: " + asoc.getTelefono());
            System.out.println("Email: " + asoc.getEmail());
            System.out.println("Fecha de Apertura: " + asoc.getFechaApertura());
        } else {
            System.out.println("No se encontraron los datos de la asociación.");
        }
    }
}