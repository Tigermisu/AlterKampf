/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alterkampf;

/**
 *
 * @author Christopher
 */
import org.powerbot.script.rt4.ClientContext;

public class LogOut extends Task<ClientContext> {
    public static boolean execute = false; 
    
    public LogOut(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        return execute;
    }
    
    @Override
    public void execute() {
        /*
         LOG OUT
        */
    }
}
