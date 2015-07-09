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
import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

public class Kill extends Task<ClientContext> {
    public int[] npcIDs;
    boolean inCombat, engaged = false;
    private Npc enemy;
    public static boolean stop = false;
    
    public Kill(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        inCombat = ctx.players.local().inCombat() || !(ctx.players.local().animation() == -1);
        //I noticed that players.inCombat() took quite some seconds to update.
        //This checks if the enemy health is 0 or less, instantly disengaging the player to attack again
        if(engaged)
            engaged = enemy.health() > 0;
        return !stop && (!engaged || !inCombat) && !ctx.npcs.select().id(npcIDs).isEmpty();
    }
    
    @Override
    public void execute() {
        
         enemy = ctx.npcs.id(npcIDs).select(new Filter<Npc>() {
             @Override
             public boolean accept(Npc npc) {
                 return !npc.inCombat() && ctx.movement.reachable(ctx.players.local().tile(), npc);
             }
         }).nearest().poll();
         if(enemy.inViewport()) {
            enemy.interact("Attack");
            System.out.println("Attacking " + enemy.name());
            AlterKampf.status = "Attacking " + enemy.name();
            if(ctx.players.local().inCombat())
                Condition.sleep(1500);
            engaged = Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    return ctx.players.local().inCombat();
                }}, 500, 7);
         } else {
             ctx.camera.turnTo(enemy);
             ctx.movement.step(enemy);
             Condition.sleep(500);
         }
         
        
        
         
    }
}

