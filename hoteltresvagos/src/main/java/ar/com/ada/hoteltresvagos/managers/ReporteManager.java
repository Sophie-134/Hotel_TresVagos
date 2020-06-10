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

            Query query = session.createNativeQuery("SELECT t.id, t.descripcion, sum(r.importe_reserva) total_reservas, sum(r.importe_pagado) total_pagado, sum(r.importe_total) total_importe FROM reserva r inner join tipo_de_estado t on r.estado2 = t.id GROUP BY t.id", Reporte1.class);
            //query.setParameter(1, descripcion);

        List<Reporte1> reportes = query.getResultList();

        return reportes;

        }

        public List<Reporte2> buscarEstadoHuesped() {

            Session session = sessionFactory.openSession();
    
                Query query = session.createNativeQuery("select h.huesped_id, h.nombre, sum(r.importe_reserva) importe_reserva, sum(r.importe_pagado) importe_pagado, sum(r.importe_total) total_importe from huesped h inner join reserva r on h.huesped_id = r.huesped_id group by h.huesped_id, h.nombre;", Reporte2.class);
                //query.setParameter(1, descripcion);
    
            List<Reporte2> reportes = query.getResultList();
    
            return reportes;
    
            }

    }