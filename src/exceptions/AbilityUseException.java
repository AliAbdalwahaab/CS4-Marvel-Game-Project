package exceptions;

public class AbilityUseException extends GameActionExceptions{



    //Constructors
    public AbilityUseException (){
        super();
    }

    public AbilityUseException (String s){
        super(s);
        //System.out.println("Illegal action, Ability isn't available for use. \n");
    }

}
