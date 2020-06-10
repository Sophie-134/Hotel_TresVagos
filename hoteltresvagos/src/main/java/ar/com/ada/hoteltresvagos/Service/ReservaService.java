package ar.com.ada.hoteltresvagos.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.Huesped;
import ar.com.ada.hoteltresvagos.entities.Reserva;
import ar.com.ada.hoteltresvagos.managers.HuespedManager;
import ar.com.ada.hoteltresvagos.managers.ReservaManager;

public class ReservaService {
    public static Scanner Teclado = new Scanner(System.in);
    
    protected ReservaManager ABMReserva;
    public ReservaService(ReservaManager ABMReserva, HuespedManager ABMHuesped){
        this.ABMHuesped = ABMHuesped;
        this.ABMReserva = ABMReserva;
    }
    protected HuespedManager ABMHuesped;
    

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

}