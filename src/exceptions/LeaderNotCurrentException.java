package exceptions;
//an exception that is thrown upon
//trying to use the leader’s ability while the champion whose turn is running is not a leader.
public class LeaderNotCurrentException extends GameActionException {

    //Constructors
    public LeaderNotCurrentException() {
        super();
    }

    public LeaderNotCurrentException(String s) {
        super(s);
    }
}
