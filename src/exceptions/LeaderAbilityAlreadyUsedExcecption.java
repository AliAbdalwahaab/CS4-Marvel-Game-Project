package exceptions;

public class LeaderAbilityAlreadyUsedExcecption extends GameActionExceptions{

    private String exceptionPrintedMessage = "Illegal action, leader ability could be only used once.";

    //Constructors
    public LeaderAbilityAlreadyUsedExcecption (){
        super();
    }

    public LeaderAbilityAlreadyUsedExcecption (String s){
        super(s);
        System.out.println("Illegal action, leader ability could be only used once. \n");
    }
}
