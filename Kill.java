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
    public static boolean retaliate = false;
    
    public Kill(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        inCombat = ctx.players.local().inCombat() || !(ctx.players.local().animation() == -1);
        if(engaged)
            engaged = enemy.health() > 0;
        return (!engaged || !inCombat) && !ctx.npcs.select().id(npcIDs).isEmpty();
    }
    
    @Override
    public void execute() {
        if(!retaliate) {
        enemy = ctx.npcs.id(npcIDs).select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return !npc.inCombat() && ctx.movement.reachable(ctx.players.local().tile(), npc);
            }
        }).nearest().poll();
        } else {
            retaliate = false;
            engaged = ctx.players.local().inCombat();
            enemy = (Npc)ctx.players.local().interacting();
            enemy.interact("Attack");
            System.out.println("Retaliating");
            AlterKampf.status = "Retaliating " + enemy.name();
            Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    return !ctx.players.local().inCombat();
                }}, 500, 8);            
            return;
        }
        
        if(enemy.inViewport()) {
            enemy.interact("Attack");
            System.out.println("Attacking " + enemy.name());
            AlterKampf.status = "Attacking " + enemy.name();
            if(ctx.players.local().inCombat())
                Condition.sleep(1500 + (int)(Math.random() * 1000));
            engaged = Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    return ctx.players.local().inCombat();
                }}, 500, 8);
        } else {
            ctx.camera.turnTo(enemy);
            ctx.movement.step(enemy);
            Condition.sleep(500 + (int)(Math.random() * 1000));
        }
         
        
        
         
    }
}

