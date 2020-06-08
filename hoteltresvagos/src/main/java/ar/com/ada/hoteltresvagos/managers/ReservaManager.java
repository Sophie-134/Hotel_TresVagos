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

public class ReservaManager {
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


    public void create(Reserva reserva) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(reserva);

        session.getTransaction().commit();
        session.close();
    }

    public Reserva read(int reservaId) {
        Session session = sessionFactory.openSession();

        Reserva reserva = session.get(Reserva.class, reservaId);

        session.close();

        return reserva;
    }

    public void delete(Reserva reserva) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(reserva);

        session.getTransaction().commit();
        session.close();
    }

    public List<Reserva> buscarTodosR() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM reserva", Reserva.class);
        // query = session.createQuery("From Obse")
        List<Reserva> todos = query.getResultList();

        return todos;

        }

        public List<Reserva> buscarPorNombreHuesped(String nombre){

            Session session = sessionFactory.openSession();
    
            
            Query queryForma1 = session.createNativeQuery(
                "SELECT * FROM reserva r inner join huesped h on h.huesped_id = r.huesped_id where nombre= ?",
                Reserva.class);
        queryForma1.setParameter(1, nombre);
    
        List<Reserva> reservas = queryForma1.getResultList();
    
            return reservas;
        }
   /* public List<Reserva> buscarPorNombreHuesped(String nombre) {

        Session session = sessionFactory.openSession();

        // SQL Injection vulnerability exposed.
        // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
        // si pasamos
        // como nombre: "' or '1'='1"
        // No hacer de la manera de abajo
        // Query query = session.createNativeQuery("SELECT * FROM huesped where nombre =
        // '" + nombre + "'", Huesped.class);

        // Forma sql nativa con parametros
        Query queryForma1 = session.createNativeQuery(
                "SELECT * FROM reserva r inner join huesped h on h.huesped_id = r.huesped_id where nombre= ?",
                Reserva.class);
        queryForma1.setParameter(1, nombre);// el 1 refiere al signo de pregunta


        //Forma utilizando JPQL
        //Query queryForma2 = session.createQuery("SELECT r FROM Reserva r where r.huesped.nombre = : nombre", Reserva.class);
        //queryForma2.setParameter("nombre", nombre);
        
        List<Reserva> reservas = queryForma1.getResultList();

        return reservas;

    }*/

}