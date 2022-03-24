package exceptions;


public class GameActionExceptions {

    private String exception;

    //Constructors
    public GameActionExceptions (){
    }

    public GameActionExceptions (String s){

        this.exception = s;
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
