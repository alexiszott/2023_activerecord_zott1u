package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Film {

    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne p) {
        this.titre = titre;
        this.id = -1;
        this.id_real = p.getId();
    }

    private Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static Film findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM Film WHERE id = (?);";
        PreparedStatement prep = (DBConnection.getConnection()).prepareStatement(SQLPrep);
        prep.setInt(1,id);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        // s'il y a un resultat
        Film f;
        if(rs.next()) {
            String titre = rs.getString("titre");
            int real = rs.getInt("id_real");
            int id_f = rs.getInt("id");
            f = new Film(titre,id_f,real);
        } else {
            f = null;
        }
        return f;
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {
        String SQLPrep = "SELECT * FROM Film WHERE id_real = (?);";
        PreparedStatement prep1 = (DBConnection.getConnection()).prepareStatement(SQLPrep);
        prep1.setInt(1,p.getId());
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Film> listF = new ArrayList<Film>();
        while(rs.next()) {
            String titre = rs.getString("titre");
            int real = rs.getInt("id_real");
            int id_f = rs.getInt("id");
            Film f = new Film(titre,id_f,real);
            listF.add(f);
        }
        return listF;
    }

    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE `Film` (ID INTEGER AUTO_INCREMENT, TITRE varchar(40) NOT NULL, ID_REAL INTEGER NULL, PRIMARY KEY (ID));";
        Statement stmt = (DBConnection.getConnection()).createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Film;";
        Statement stmt = (DBConnection.getConnection()).createStatement();
        stmt.executeUpdate(drop);
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if(this.id_real != -1){
            if(this.id == -1){
                saveNew();
            }  else {
                update();
            }
        } else {
            throw new RealisateurAbsentException();
        }
    }

    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
        PreparedStatement prep = (DBConnection.getConnection()).prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1,this.titre);
        prep.setInt(2,this.id_real);
        prep.executeUpdate();
        ResultSet generatedKeys = prep.getGeneratedKeys();
        if (generatedKeys.next()) {
            this.id = generatedKeys.getInt(1);
        }
    }

    private void update() throws SQLException {
        String SQLprep = "update Film set titre=?, id_real=? where id=?;";
        PreparedStatement prep = (DBConnection.getConnection()).prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.execute();
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }
}
