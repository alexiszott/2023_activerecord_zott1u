import activeRecord.DBConnection;
import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilm {

    public Personne p1;
    public Personne p2;
    public Personne p3;
    public Personne p4;
    public Film f1;
    public Film f2;
    public Film f3;
    public Film f4;
    public Film f5;
    public Film f6;
    public Film f7;


    @BeforeEach
    public void initEach() throws SQLException, RealisateurAbsentException {
        DBConnection.setNomDB("test_personne");
        Personne.createTable();
        p1 = new Personne("Spielberg", "Steven");
        p1.save();
        p2 = new Personne("Scott", "Jacque");
        p2.save();
        p3 = new Personne("Kubrick", "Ridley");
        p3.save();
        p4 = new Personne("Fincher", "David");
        p4.save();;
        Film.createTable();
        f1 = new Film("Arche perdue", p1);
        f1.save();
        f2 = new Film("Alien", p2);
        f2.save();
        f3 = new Film("Temple Maudit", p1);
        f3.save();
        f4 = new Film("Blade Runner", p2);
        f4.save();
        f5 = new Film("Alien3", p4);
        f5.save();
        f6 = new Film("Fight Club", p4);
        f6.save();
        f7 = new Film("Orange Mecanique", p3);
        f7.save();
    }

    @AfterEach
    public void after() throws SQLException {
        Personne.deleteTable();
        Film.deleteTable();
    }

    @Test
    public void testFilm_findById() throws Exception {
        Film f = Film.findById(2);
        assertEquals("Alien",f.getTitre());
    }

    @Test
    public void testFilm_findByRealisateur() throws Exception {
        ArrayList<Film> l = Film.findByRealisateur(p1);
        assertEquals(2,l.size());
    }

    @Test
    public void testFilm_getRealisateur() throws Exception {
        Personne p = f1.getRealisateur();
        assertEquals(p1.getNom(),p.getNom());
    }

    @Test
    public void testFilm_saveWrongRealId() throws Exception {
        Film f = new Film("Avatar", new Personne("James", "Cameron"));
        RealisateurAbsentException exception = assertThrows(RealisateurAbsentException.class, () -> {
            f.save();
        });

        String expectedMessage = "Erreur pas de réalisateur";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
