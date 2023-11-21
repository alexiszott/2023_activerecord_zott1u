package activeRecord;

import java.sql.*;

public class Personne {
    private int id;
    private String nom;
    private String prenom;


    public Personne(String n, String p) {
        this.id = -1;
        this.nom = n;
        this.prenom = p;
    }

    // creation de la table Personne
    {
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        System.out.println("1) creation table Personne\n");
    }

    public void addPersonne() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, "Scott");
        prep.setString(2, "Ridley");
        prep.executeUpdate();
        System.out.println("3) ajout Ridley Scott");

        // recuperation de la derniere ligne ajoutee (auto increment)
        // recupere le nouvel id
        int autoInc = -1;
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        System.out.print("  ->  id utilise lors de l'ajout : ");
        System.out.println(autoInc);
        System.out.println();
    }

    public static void findAllPersonne() throws SQLException {
        Connection connect = DBConnection.getConnection();
        System.out.println("4) Recupere les personnes de la table Personne");
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }
        System.out.println();
    }

    public void removePersonneById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement("DELETE FROM Personne WHERE id=?");
        prep.setInt(1, id);
        prep.execute();
        System.out.println("5) Suppression personne id 1 (Spielberg)");
        System.out.println();
    }

    // recuperation de la seconde personne + affichage
    {
        System.out.println("6) Recupere personne d'id 2");
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, 2);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }
        System.out.println();
    }

    // met a jour personne 2
    public void update(int i, String nom, String prenom) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, nom);
        prep.setString(2, prenom);
        prep.setInt(3, 2);
        prep.execute();
        System.out.println("7) Effectue modification Personne id 2");
        System.out.println();
    }


    // suppression de la table personne
    {
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("9) Supprime table Personne");
    }

}
