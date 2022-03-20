package exceptions;

public class AbilityUseException extends GameActionExceptions{

    private String exceptionPrintedMessage = "Illegal action, Ability isn't available for use.";

    //Constructors
    public AbilityUseException (){
        super();
    }

    public AbilityUseException (String s){
        super(s);
        System.out.println("Illegal action, Ability isn't available for use. \n");
    }

}
