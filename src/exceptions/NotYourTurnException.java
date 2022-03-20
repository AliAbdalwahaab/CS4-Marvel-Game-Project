package exceptions;

public class NotYourTurnException extends GameActionExceptions{

    private String exceptionPrintedMessage = "Illegal action, your turn hasn't started.";

    //Constructors
    public NotYourTurnException(){
        super();
    }
    public NotYourTurnException(String s){
        super(s);
        System.out.println("Illegal action, your turn hasn't started. \n");
    }

}
