package activeRecord;

public class RealisateurAbsentException extends Exception{

    public RealisateurAbsentException() {
        super("Erreur pas de réalisateur");
    }
}
