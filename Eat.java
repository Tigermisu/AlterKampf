package alterkampf;

import javax.swing.JOptionPane;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Npc;

public class Eat extends Task<ClientContext> {
    public int[] foodId;
    public double minhealth;
    public boolean hasFood = true;
    
    public Eat(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        return ctx.players.local().inCombat() && hasFood && ctx.players.local().health() <= minhealth;
    }
    
    @Override
    public void execute() {
        Item food;
        System.out.println("Eating");
        AlterKampf.status = "Eating";
        if(!ctx.inventory.select().id(foodId).isEmpty())
            food = ctx.inventory.poll();
        else {
            System.out.println("Out of food");
            JOptionPane.showMessageDialog(null, "Out of food. Stopping Script");
            ctx.controller.stop();
            return;
        }       
            
        food.interact("Eat");
        //Click on the enemy to continue attacking.
        ((Npc)ctx.players.local().interacting()).interact("Attack");
        Condition.sleep(1000 + (int)(Math.random() * 1000));
    }
}
