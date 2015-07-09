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
import javax.swing.JOptionPane;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

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
        if(clicked) {
            clickcooldown++;
            if(clickcooldown >= tgtcooldown) {
                clicked = false;
            }
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
            JOptionPane.showMessageDialog(null, "Out of food");
            hasFood = false;
            AlterKampf.status = "idling (no food)";
            Kill.stop = true;
            return;
        }       
            
        food.interact("Eat");
        clickcooldown = 0;
        tgtcooldown = 4 + Math.random() * 2;
        clicked = true;
    }
}
