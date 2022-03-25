package exceptions;

public class NotEnoughResources extends GameActionException{


    //Constructors
    public NotEnoughResources(){
        super();
    }

    public NotEnoughResources(String s){
        super(s);
        //System.out.println("Not enough resources \n");
    }

}
