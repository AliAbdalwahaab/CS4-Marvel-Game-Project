package exceptions;

public class LeaderAbilityAlreadyUsedExcecption extends GameActionExceptions{

    //Constructors
    public LeaderAbilityAlreadyUsedExcecption (){
        super();
    }

    public LeaderAbilityAlreadyUsedExcecption (String s){
        super(s);
        //System.out.println("Illegal action, leader ability could be only used once. \n");
    }
}
