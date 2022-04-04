package lv.datuskola;

import lv.datuskola.jpa.Foo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class MainApp {

    public static void main(String[] args) {
        try {
            var inputStream = MainApp.class.getResourceAsStream("/persistence-h2.properties");
            Properties p = new Properties();
            p.load(inputStream);

            Configuration cfg = new Configuration()
                    .setProperties(p);

            cfg.addAnnotatedClass(Foo.class);

            SessionFactory sessions = cfg.buildSessionFactory();
            Session session = sessions.openSession();

            var foo = new Foo();
            foo.setName("name");

            var em = session.getEntityManagerFactory().createEntityManager();
            var t = em.getTransaction();
            t.begin();
            em.persist(foo);

            var list = em.createQuery("SELECT a FROM Foo a", Foo.class).getResultList();
            System.out.println(list.size());

            t.rollback();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}