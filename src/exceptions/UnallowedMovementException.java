package exceptions;

public class UnallowedMovementException extends GameActionExceptions{

    private String exceptionPrintedMessage = "Illegal action, occupied or illegal standing space.";

    //Constructors
public UnallowedMovementException (){
    super();
}

public UnallowedMovementException (String s){
    super(s);
}

}
