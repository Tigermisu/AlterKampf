package alterkampf;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

public class Loot extends Task<ClientContext> {
    public static boolean loot;
    public static int[] itemIds;

    public Loot(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return loot && !ctx.groundItems.select().id(itemIds).isEmpty() && ctx.inventory.size() < 28;
    }

    @Override
    public void execute() {
        for (GroundItem item : ctx.groundItems) {
            AlterKampf.status = "Picking up " + item.name();
            if (item.inViewport()) {
                pickUp(item);
            } else {
                ctx.movement.step(item);
                ctx.camera.turnTo(item);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    return item.inViewport();
                    }}, 500, 5);
                pickUp(item);
            }
        }                
    }
    
    private void pickUp(GroundItem item){
        item.interact("Pick Up");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
            return !item.inViewport();
            }
        }, 500, 10);
    }
}
