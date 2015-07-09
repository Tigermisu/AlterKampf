// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Camera;


public class Antiban extends Task<ClientContext> {
    public double frequency = 100;
    
    
    public Antiban(ClientContext ctx) {
        super(ctx);
    }
    
    public boolean activate() {
        return Math.random() * frequency <= 1;
    }
    
    public void execute() {
        System.out.println("Executing Antiban");
        ctx.camera.angle((int)(Math.random() * 360)); 
        
        
    }
    
}
