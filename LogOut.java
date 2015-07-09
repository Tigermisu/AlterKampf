// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;


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
