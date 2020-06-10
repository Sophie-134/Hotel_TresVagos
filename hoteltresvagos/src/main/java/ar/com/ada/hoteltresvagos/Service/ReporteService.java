package ar.com.ada.hoteltresvagos.Service;

import java.util.List;

import ar.com.ada.hoteltresvagos.entities.Reportes.*;
import ar.com.ada.hoteltresvagos.managers.ReporteManager;

public class ReporteService {
    //para imprimir los reportes
    protected ReporteManager ABMReporte;
    public ReporteService(ReporteManager ABMReporte){
        this.ABMReporte = ABMReporte;
    }

    public void EstadoDeLasReservas(){
        List<Reporte1> todos = ABMReporte.buscarEstadoReservas();
    for (Reporte1 r : todos) {
        mostrarEstadoR(r);
    }
}

    public void mostrarEstadoR(Reporte1 reportes){
        System.out.println("Id de la reserva: "+ reportes.getId()+ " Estado de la Reserva: " + reportes.getDescripcion() + " Total de la Reserva: " + reportes.getTotalReservas() + " Total Pagado: " + reportes.getTotalPagado()+ " Total del Importe: "+ reportes.getTotalImporte());
    }

    public void EstadoDeLosHuespedes(){
        List<Reporte2> todos = ABMReporte.buscarEstadoHuesped();
    for (Reporte2 r : todos) {
        mostrarEstadoH(r);
    }
}

    public void mostrarEstadoH(Reporte2 reportes){
        System.out.println("Id del huesped: "+ reportes.getId()+ " Nombre: " + reportes.getNombre() + " Importe de la Reserva: " + reportes.getImporteReserva() + " Importe Pagado: " + reportes.getImportePagado()+ " Total del Importe: "+ reportes.getTotalImporte());
    }

}