/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alterkampf;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Camera;

/**
 *
 * @author Christopher
 */
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
