import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestPersonne {

    @BeforeEach
    public void initEach() throws SQLException {
        DBConnection.setNomDB("test_personne");
        Personne.createTable();
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.save();
        Personne p2 = new Personne("Scott", "Jacque");
        p2.save();
        Personne p3 = new Personne("Kubrick", "Ridley");
        p3.save();
        Personne p4 = new Personne("Fincher", "David");
        p4.save();
    }

    @AfterEach
    public void after() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void testPersonne_findById() throws Exception {
        Personne p = Personne.findById(2);
        assertEquals("Jean", p.getNom());
        assertEquals("Jacque", p.getPrenom());

    }

    @Test
    public void testPersonne_findByName() throws Exception {
        Personne p = Personne.findByName("Alexis");
        assertEquals("Zott", p.getPrenom());
        assertEquals("Alexis", p.getNom());
    }

}
