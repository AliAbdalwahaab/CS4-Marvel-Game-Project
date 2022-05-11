package exceptions;
//Is thrown when a champion is trying to cast an ability on an out
//of range target or being in a condition that prevents him from casting an ability.
public class AbilityUseException extends GameActionException{

    //Constructors
    public AbilityUseException (){
        super();
    }

    public AbilityUseException (String s){
        super(s);
    }

}
