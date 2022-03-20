package exceptions;

public class NotYourTurnException extends GameActionExceptions{

    //Constructors
    public NotYourTurnException(){
        super();
    }
    public NotYourTurnException(String s){
        super(s);
    }

}
