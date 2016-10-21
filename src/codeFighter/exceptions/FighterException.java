package codeFighter.exceptions;

/**
 *
 * @author carlosrodf
 */
public class FighterException extends Exception {

    public FighterException(){
        
    }
    
    public FighterException(String message){
        super(message);
    }
    
    public FighterException(Throwable cause){
        super(cause);
    }
    
    public FighterException(String message, Throwable cause){
        super(message,cause);
    }
    
}
