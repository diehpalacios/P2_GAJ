package org.example;


import dao.*;
import exportador.*;
import metadatos.GestorMetadatos;
import modelo.*;

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
                    case 2 -> listarSocias();
                    case 3 -> listarPartidas();
                    case 4 -> apuntarseAPartida();
                    case 5 -> exportarDatos();
                    case 6 -> GestorMetadatos.mostrarMetadatosBaseDatos();
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
        System.out.println(" ASOCIACIÓN LUDIKA VITORIA-GASTEIZ - MENÚ");
        System.out.println("==================================================");
        System.out.println("1. Listar Juegos de Mesa");
        System.out.println("2. Listar Socias");
        System.out.println("3. Listar Partidas");
        System.out.println("4. Apuntarse a una Partida");
        System.out.println("5. Exportar Datos (XML / JSON)");
        System.out.println("6. Ver Metadatos de la BBDD");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void listarJuegos() throws Exception {
        List<Juego> juegos = juegoDAO.listarJuegos();
        System.out.println("\n--- LISTADO DE JUEGOS ---");
        juegos.forEach(System.out::println);
    }

    private static void listarSocias() throws Exception {
        List<Socia> socias = sociaDAO.listarSocias();
        System.out.println("\n--- LISTADO DE SOCIAS ---");
        socias.forEach(System.out::println);
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
}