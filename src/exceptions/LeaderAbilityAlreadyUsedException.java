package exceptions;

public class LeaderAbilityAlreadyUsedException extends GameActionExceptions{

    //Constructors
    public LeaderAbilityAlreadyUsedException(){
        super();
    }

    public LeaderAbilityAlreadyUsedException(String s){
        super(s);
        //System.out.println("Illegal action, leader ability could be only used once. \n");
    }
}
