package at.dotti.intellij.plugins.jazz.exceptions;

public class JazzServiceException extends Exception {
    public JazzServiceException(Exception e) {
        super(e);
    }

    public JazzServiceException(String msg) {
        super(msg);
    }
}
