package exceptions;


public class GameActionException extends Exception {

    private String exception;

    //Constructors
    public GameActionException (){
        super();
    }

    public GameActionException (String s){

        super(s);
    }

    //getter
    public String getExceptionString() {
        return exception;
    }

    //setter
    public void setExceptionString (String s) {
        this.exception = s;
    }


}
