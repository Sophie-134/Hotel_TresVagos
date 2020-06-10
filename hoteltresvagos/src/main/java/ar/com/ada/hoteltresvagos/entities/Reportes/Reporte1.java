package ar.com.ada.hoteltresvagos.entities.Reportes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import ar.com.ada.hoteltresvagos.entities.Huesped;
import ar.com.ada.hoteltresvagos.entities.Reserva;

@Entity

public class Reporte1 {
 
    @Id
    @Column(name= "id")
    //no estamos agregando datos
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name= "descripcion")
    private String descripcion;
    @Column(name= "total_reservas")
    private double totalReservas;
    @Column(name= "total_pagado")
    private double totalPagado;
    @Column(name= "total_importe")
    private double totalImporte;
    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(int totalReservas) {
        this.totalReservas = totalReservas;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(int totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getTotalImporte() {
        return totalImporte;
    }

    public void setTotalImporte(int totalImporte) {
        this.totalImporte = totalImporte;
    }

   

   

}
