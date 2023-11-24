package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String n, String p) throws SQLException {
        this.id = -1;
        this.nom = n;
        this.prenom = p;
    }

    public static List<Personne> findAll() throws SQLException {
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Personne;");
        // s'il y a un resultat
        List<Personne> listP = new ArrayList<Personne>();
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int idPersonne = rs.getInt("id");
            Personne p = new Personne(nom,prenom);
            p.id = idPersonne;
            listP.add(p);
        }
        return listP;
    }

    public static Personne findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM Personne WHERE id = (?);";
        PreparedStatement prep1 = (DBConnection.getConnection()).prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne p;
        if(rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int idPersonne = rs.getInt("id");
            p = new Personne(nom,prenom);
            p.id = idPersonne;
        } else {
            p = null;
        }
        return p;
    }

    public static Personne findByName(String name) throws SQLException {
        String SQLPrep = "SELECT * FROM Personne WHERE nom LIKE ?;";
        PreparedStatement prep1 = (DBConnection.getConnection()).prepareStatement(SQLPrep);
        prep1.setString(1,name);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne p;
        if(rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int idPersonne = rs.getInt("id");
            p = new Personne(nom,prenom);
            p.id = idPersonne;
        } else {
            p = null;
        }
        return p;
    }

    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE Personne (ID INTEGER  AUTO_INCREMENT, NOM varchar(40) NOT NULL, PRENOM varchar(40) NOT NULL, PRIMARY KEY (ID));";
        Statement stmt = (DBConnection.getConnection()).createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne;";
        Statement stmt = (DBConnection.getConnection()).createStatement();
        stmt.executeUpdate(drop);
    }

    public void save() throws SQLException {
        if(this.id == -1){
            saveNew();
        }  else {
            update();
        }
    }

    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = (DBConnection.getConnection()).prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1,this.nom);
        prep.setString(2,this.prenom);
        prep.executeUpdate();
        ResultSet generatedKeys = prep.getGeneratedKeys();
        if (generatedKeys.next()) {
            this.id = generatedKeys.getInt(1);
        }
    }

    private void update() throws SQLException {
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = (DBConnection.getConnection()).prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
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
    public void delete() throws SQLException {
        String SQLPrep = "DELETE * FROM Personne WHERE id = (?);";
        PreparedStatement prep1 = (DBConnection.getConnection()).prepareStatement(SQLPrep);
        prep1.setInt(1,this.id);
        prep1.execute();
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
