// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;


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
        //Basically, the player is in combat if he is not idle or he is in combat.
        inCombat = ctx.players.local().inCombat() || !(ctx.players.local().animation() == -1);
        //I noticed that players.inCombat() took quite some seconds to update.
        //This checks if the enemy health is 0 or less, instantly disengaging the player to attack again
        if(engaged)
            engaged = enemy.health() > 0;
        //Return the proper condition
        return (!engaged || !inCombat) && !ctx.npcs.select().id(npcIDs).isEmpty();
    }
    
    @Override
    public void execute() {
        
        //Select the nearest reachable NPCs out of combat
        enemy = ctx.npcs.id(npcIDs).select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return !npc.inCombat() && ctx.movement.reachable(ctx.players.local().tile(), npc);
            }
        }).nearest().poll();

        //If the enemy is in viewport, attack him.
        if(enemy.inViewport()) {
            enemy.interact("Attack");
            System.out.println("Attacking " + enemy.name());
            AlterKampf.status = "Attacking " + enemy.name();
            //If the player has just disengaged another enemy, wait a bit before continuing
            //(Prevents spam clicking)
            if(ctx.players.local().inCombat())
                Condition.sleep(1500);
            //Check if engage was successful
            //3.5s timeout
            engaged = Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    return ctx.players.local().inCombat();
                }}, 500, 7);
         } else {
            //if we cannot see the enemy, get close to it.
            ctx.camera.turnTo(enemy);
            ctx.movement.step(enemy);
            Condition.sleep(500);
         }
         
        
        
         
    }
}

