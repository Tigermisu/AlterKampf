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
import org.powerbot.script.rt4.Npc;

public class Kill extends Task<ClientContext> {
    public int[] npcIDs = {3029, 3030, 3031, 3032, 3033};
    private boolean attacking = false, engaged = false, clicked = false;
    private double clickcooldown = 0, tgtcooldown = 50;
    private Npc enemy;
    
    public Kill(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        //System.out.println(ctx.players.local().inCombat());
        if(clicked) {
            clickcooldown++;
            if(clickcooldown >= tgtcooldown) {
                clicked = false;
            }
        }
        return !clicked && ctx.players.local().animation() == -1 && !ctx.npcs.select().id(npcIDs).isEmpty();
    }
    
    @Override
    public void execute() {
                clickcooldown = 0;
                if(!engaged)
                    enemy = ctx.npcs.nearest().poll();
                while(!attacking) {
                    System.out.println(enemy.name() + (enemy.inCombat()? " is in combat" : " is not fighting"));
                    if (enemy.inCombat()) {
                        enemy = ctx.npcs.nearest().poll();
                    } else {
                        attacking = true;
                    }
                }
                if(attacking && enemy.inCombat() && !ctx.players.local().inCombat()) {
                    attacking = false;
                    engaged = false;
                    System.out.println("Another player engaged enemy");
                    return;
                }
                
                if(engaged) {
                    if(enemy.health() <= 0) {
                        System.out.println("Enemy dead");
                        engaged = false;
                        attacking = false;
                        return;
                    }
                }
                
                if(!engaged) {
                    if(enemy.inViewport()) {
                        enemy.interact("Attack");
                        System.out.println("Attacking " + enemy.name());
                        clicked = true;
                        engaged = true;
                        return;
                   }
                    else {
                        System.out.println("Looking for " + enemy.name());
                        ctx.movement.step(enemy);
                        ctx.camera.turnTo(enemy);
                    }
                } 
                engaged = ctx.players.local().inCombat();
                
                if(attacking && engaged && !enemy.inCombat() && ctx.players.local().inCombat()) {
                    System.out.println("Enemy attacking player");
                    enemy = (Npc)ctx.players.local().interacting();
                    engaged = true;
                    attacking = true;
                    clicked = true;
                }
                
        
    }
}

