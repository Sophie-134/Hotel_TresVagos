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
        System.out.println("Total de la Reserva: " + reportes.getTotalReservas() + " Total Pagado: " + reportes.getTotalPagado()+ " Total del Importe: "+ reportes.getTotalImporte()+ " Estado de la Reserva: " + reportes.getDescripcion());
    }

}