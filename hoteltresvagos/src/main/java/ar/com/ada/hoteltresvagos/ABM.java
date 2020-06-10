package ar.com.ada.hoteltresvagos;

import java.util.Scanner;

import ar.com.ada.hoteltresvagos.Service.HuespedService;
import ar.com.ada.hoteltresvagos.Service.ReporteService;
import ar.com.ada.hoteltresvagos.Service.ReservaService;
import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.excepciones.HuespedDNIException;
import ar.com.ada.hoteltresvagos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();
    protected ReporteManager ABMReporte = new ReporteManager();

    protected ReporteService reporteService = new ReporteService(ABMReporte);
    protected HuespedService huespedService = new HuespedService(ABMHuesped);
    protected ReservaService reservaService = new ReservaService(ABMReserva, ABMHuesped);

    public void iniciar() throws Exception {

        try {
            ABMReserva.setup();
            ABMHuesped.setup();
            ABMReporte.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            huespedService.alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        huespedService.baja();
                        break;

                    case 3:
                    huespedService.modifica();
                        break;

                    case 4:
                    huespedService.listar();
                        break;

                    case 5:
                    huespedService.listarPorNombre();
                        break;
                    case 6:
                    reservaService.cargarReserva();
                        break;
                    case 7:
                    reservaService.eliminarReserva();
                        break;
                    case 8:
                    reservaService.listarReserva();
                        break;
                    case 9:
                    reservaService.listarReservaPorNombre();
                        break;
                    case 10:
                        reporteService.EstadoDeLasReservas();
                        break;
                    case 11:
                        reporteService.EstadoDeLosHuespedes();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMHuesped.exit();
            ABMReserva.exit();
            ABMReporte.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    


    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Huesped:");
        int dni = Teclado.nextInt();
        Huesped huespedEncontrado = ABMHuesped.readByDNI(dni);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {
            ABMHuesped.delete(huespedEncontrado);
            System.out.println("El registro del DNI " + huespedEncontrado.getDni() + " ha sido eliminado.");
        }
    }

   

  

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un huesped.");
        System.out.println("2. Para eliminar un huesped.");
        System.out.println("3. Para modificar un huesped.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un huesped por nombre especifico(SQL Injection)).");
        System.out.println("6. Para hacer una reserva.");
        System.out.println("7. Para eliminar una reserva.");
        System.out.println("8. Para ver la lista de reservas.");
        System.out.println("9. Buscar una reserva por nombre de huesped.");
        System.out.println("10. Ver el estado de las reservas.");
        System.out.println("11. Resumen de los huespedes y sus importes.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}