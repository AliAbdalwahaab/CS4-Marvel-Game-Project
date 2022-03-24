package exceptions;

public class UnallowedMovementException extends GameActionExceptions{

    //Constructors
    public UnallowedMovementException (){
        super();
    }

    public UnallowedMovementException (String s){
        super(s);
        //System.out.println("Illegal action, occupied or illegal standing space.\n");
    }

}
