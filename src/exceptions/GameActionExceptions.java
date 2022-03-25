package exceptions;


public class GameActionExceptions extends Exception {

    private String exception;

    //Constructors
    public GameActionExceptions (){
        super();
    }

    public GameActionExceptions (String s){

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
