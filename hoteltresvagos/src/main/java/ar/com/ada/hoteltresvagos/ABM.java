package ar.com.ada.hoteltresvagos;

import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();

    public void iniciar() throws Exception {

        try {
            ABMReserva.setup();
            ABMHuesped.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorNombre();
                        break;
                    case 6:
                        cargarReserva();
                        break;
                    case 7:
                        eliminarReserva();
                        break;
                    case 8:
                        listarReserva();
                        break;
                    case 9:
                        listarReservaPorNombre();
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

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Huesped huesped = new Huesped();
        System.out.println("Ingrese el nombre:");
        huesped.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        huesped.setDni(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese la domicilio:");
        huesped.setDomicilio(Teclado.nextLine());

        System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            huesped.setDomicilioAlternativo(domAlternativo);

        // Actualizo todos los objectos

        ABMHuesped.create(huesped);

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Huesped generada con exito.  " + huesped.getHuespedId);
         */

        System.out.println("Huesped generada con exito.  " + huesped);

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

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la huesped a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la huesped a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(huespedEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la huesped desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    huespedEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    huespedEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMHuesped.update(huespedEncontrado);

            System.out.println("El registro de " + huespedEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Huesped no encontrado.");
        }

    }

    
    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMHuesped.delete(huespedEncontrado);
                System.out
                        .println("El registro del huesped " + huespedEncontrado.getHuespedId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una huesped. Error: " + e.getCause());
            }

        }
    }

    public void listar() {

        List<Huesped> todos = ABMHuesped.buscarTodos();
        for (Huesped c : todos) {
            mostrarHuesped(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrarHuesped(huesped);
        }
    }


    public void mostrarHuesped(Huesped huesped) {

        System.out.print("Id: " + huesped.getHuespedId() + " Nombre: " + huesped.getNombre() + " DNI: "
                + huesped.getDni() + " Domicilio: " + huesped.getDomicilio());

        if (huesped.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + huesped.getDomicilioAlternativo());
        else
            System.out.println();
    }

    public void cargarReserva() {
        
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        Reserva reserva = new Reserva();

        BigDecimal importeReserva = new BigDecimal(1000);
        reserva.setImporteReserva(importeReserva); // Forma 1

        reserva.setImporteTotal(new BigDecimal(3000)); // Forma 2

        reserva.setImportePagado(new BigDecimal(0));

        reserva.setFechaReserva(new Date()); // Fecha actual

        System.out.println("Ingrese la fecha de la Reserva (dd/MM/yy)");
        Date fechaIngreso = null;
        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        // Alternativa de leer fecha con try catch
        try {
            fechaIngreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {
            System.out.println("Fecha Invalida, vuelva a intentar");
            return;
        }

        // Alternativa de leer fecha a los golpes(puede tirar una excepcion)
        System.out.println("Ingrese la fecha de Egreso(dd/MM/yy)");

        try {
            fechaEgreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {
            System.out.println("Fecha Invalida, vuelva a intentar");
            return;
        }

        reserva.setFechaIngreso(fechaIngreso);
        reserva.setFechaEgreso(fechaEgreso);// por ahora 1 dia
        reserva.setTipoDeEstadoId(11);// En este caso 30/11 significa pagado
        reserva.setHuesped(huespedEncontrado);// Esta es la relacion bidireccional

        ABMReserva.create(reserva);
        System.out.println("Reserva generada con exito.  " + reserva.getReservaId());

    }

public void eliminarReserva(){
    System.out.println("Ingrese el nombre:");
    String nombre = Teclado.nextLine();
    System.out.println("Ingrese el ID de la reserva:");
    int reservaId = Teclado.nextInt();
    Teclado.nextLine();
    Reserva reservaEncontrada = ABMReserva.read(reservaId);

    if (reservaEncontrada == null) {
        System.out.println("Reserva no encontrada.");

    } else {

        try {

            ABMReserva.delete(reservaEncontrada);
            System.out
                    .println("La reserva numero: "+ reservaEncontrada.getReservaId()+" del huesped " + nombre + " ha sido eliminado.");
        } catch (Exception e) {
            System.out.println("Ocurrio un error al eliminar una huesped. Error: " + e.getCause());
        }

    }
}

    public void listarReserva() {
       
        List<Reserva> todos = ABMReserva.buscarTodosR();
        for (Reserva r : todos) {
            mostrarReserva(r);
        }
    
    }

    public void mostrarReserva(Reserva reserva) {
        
        System.out.println("Reserva: " + reserva.getReservaId() +" "+ reserva.getHuesped()
                + " Fecha de la reserva: " + reserva.getFechaReserva() + " Fecha de Ingreso: "
                + reserva.getFechaIngreso() + " Fecha de Egreso: " + reserva.getFechaEgreso()+"\n");

    }

    public void listarReservaPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

            List<Reserva> reserva = ABMReserva.buscarPorNombreHuesped(nombre);
            for (Reserva r : reserva) {
                mostrarReserva(r);
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
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}