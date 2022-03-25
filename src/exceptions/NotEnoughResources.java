package exceptions;

public class NotEnoughResources extends GameActionExceptions{


    //Constructors
    public NotEnoughResources(){
        super();
    }

    public NotEnoughResources(String s){
        super(s);
        //System.out.println("Not enough resources \n");
    }

}
