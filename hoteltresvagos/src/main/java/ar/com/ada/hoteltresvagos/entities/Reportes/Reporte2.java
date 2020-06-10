package ar.com.ada.hoteltresvagos.entities.Reportes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reporte2 {
    @Id
    @Column(name= "huesped_id")
    //no estamos agregando datos
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name= "nombre")
    private String nombre;
    @Column(name= "importe_reserva")
    private double importeReserva;
    @Column(name= "importe_pagado")
    private double importePagado;
    @Column(name= "total_importe")
    private double totalImporte;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getImporteReserva() {
        return importeReserva;
    }

    public void setImporteReserva(double importeReserva) {
        this.importeReserva = importeReserva;
    }

    public double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(double importePagado) {
        this.importePagado = importePagado;
    }

    public double getTotalImporte() {
        return totalImporte;
    }

    public void setTotalImporte(double totalImporte) {
        this.totalImporte = totalImporte;
    }

}