package exceptions;
//an exception that is thrown when a
//champion tries to cast an ability on an invalid target or an empty cell.
public class InvalidTargetException extends GameActionException {

    //Constructors
    public InvalidTargetException() {
        super();
    }

    public InvalidTargetException(String s) {
        super(s);
    }
}
