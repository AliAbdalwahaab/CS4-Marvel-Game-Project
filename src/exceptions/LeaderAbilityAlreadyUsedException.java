package exceptions;

//Is thrown when the leader champion of any player
//tries to use his leader ability after it has already been used before. Recall that every leader
//can only use his leader ability once per game.
public class LeaderAbilityAlreadyUsedException extends GameActionException{

    //Constructors
    public LeaderAbilityAlreadyUsedException(){
        super();
    }

    public LeaderAbilityAlreadyUsedException(String s){
        super(s);
        //System.out.println("Illegal action, leader ability could be only used once. \n");
    }
}
