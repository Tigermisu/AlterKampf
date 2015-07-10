// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;


import javax.swing.JOptionPane;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Npc;

public class Eat extends Task<ClientContext> {
    public int[] foodId = {0};
    public double minhealth = 3;
    private double clickcooldown, tgtcooldown = 5;
    private boolean clicked = false;
    public boolean hasFood = true;
    
    public Eat(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        //Prevent spam clicking
        if(clicked) {
            clickcooldown++;
            clicked = clickcooldown < tgtcooldown;
        }
        return ctx.players.local().inCombat() && hasFood && ctx.players.local().health() <= minhealth && !clicked;
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
        clickcooldown = 0;
        tgtcooldown = 6 + Math.random() * 2;
        clicked = true;
    }
}
