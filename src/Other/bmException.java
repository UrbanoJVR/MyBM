/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

/**
 *
 * @author Urbano
 */
public class bmException extends Exception {

    /**
     * Creates a new instance of <code>bmException</code> without detail
     * message.
     */
    
    //public static final String 
    
    public bmException() {
    }

    /**
     * Constructs an instance of <code>bmException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public bmException(String msg) {
        super(msg);
    }
}
