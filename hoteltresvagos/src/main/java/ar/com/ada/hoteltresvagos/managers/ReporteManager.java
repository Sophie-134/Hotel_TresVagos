package ar.com.ada.hoteltresvagos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.entities.Reportes.*;

public class ReporteManager {

    protected SessionFactory sessionFactory;

        public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }
    public void exit() {
        sessionFactory.close();
    }

    public List<Reporte1> buscarEstadoReservas() {

        Session session = sessionFactory.openSession();

            Query query = session.createNativeQuery("SELECT t.descripcion, sum(r.importe_reserva) total_reservas, sum(r.importe_pagado) total_pagado, sum(r.importe_total) total_importe FROM reserva r inner join tipo_de_estado t on r.estado2 = t.id GROUP BY t.descripcion", Reporte1.class);
            //query.setParameter(1, descripcion);

        List<Reporte1> reportes = query.getResultList();

        return reportes;

        }
    }