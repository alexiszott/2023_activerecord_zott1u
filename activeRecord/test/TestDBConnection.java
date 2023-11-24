import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDBConnection {

    //4. Écrire une classe de test qui vérifie le bon fonctionnement de votre implémentation de ce patron.
    // Cette classe de test doit vérifier qu'il n'existe qu'un seul objet connexion vers la base même si la connexion est
    //demandée à plusieurs reprises. Vous ferez attention à ce que le type de la connexion soit bien
    //java.sql.Connection. Vous testerez aussi qu'il est possible de changer la base fournie par la classe
    //DBConnection (pour cela il faut créer une seconde base).

    @Test
    public void testDBConnection_single() throws Exception {
        Connection co = DBConnection.getConnection();
        Connection co2 = DBConnection.getConnection();

        assertEquals(DBConnection.getConnection(), co);
        assertEquals(co, co2);
    }

    @Test
    public void testDBConnection_db() throws Exception {
        Connection co = DBConnection.getConnection();
        DBConnection.setNomDB("test_personne");
        Connection co2 = DBConnection.getConnection();

        assertNotEquals(co, co2);
    }


}
