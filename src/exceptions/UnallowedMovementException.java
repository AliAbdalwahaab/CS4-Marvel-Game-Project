package exceptions;
//Is thrown when a champion is trying to move while violating
//the move regulations.
public class UnallowedMovementException extends GameActionException{

    //Constructors
    public UnallowedMovementException (){
        super();
    }

    public UnallowedMovementException (String s){
        super(s);
        //System.out.println("Illegal action, occupied or illegal standing space.\n");
    }

}
