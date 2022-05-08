package exceptions;
//an exception that is thrown when a
//champion tries to use a normal attack while being disarmed.
public class ChampionDisarmedException extends GameActionException{

    //Constructors
    public ChampionDisarmedException() {
        super();
    }

    public ChampionDisarmedException(String s) {
        super(s);
    }
}
