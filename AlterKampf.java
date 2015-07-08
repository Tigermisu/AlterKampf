/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alterkampf;

import org.powerbot.script.Filter;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Christopher
 */

@Script.Manifest(name = "Alter Kampfer", description = "Fights Monsters, eats when low on health.", properties = "client=4")

public class AlterKampf extends PollingScript<ClientContext> {
    private int monsterIds[] = {3029, 3031, 3032, 3033, 3034};
    private int foodId = 329;

    private List<Task> taskList = new ArrayList<Task>();
    
    @Override
    public void start() {
        Kill kill =new Kill(ctx);
        Eat eat = new Eat(ctx);
        Antiban antiban = new Antiban(ctx);
        taskList.addAll(Arrays.asList(kill, eat, antiban));
        System.out.println("Script version v: 0.20 Alpha Release ");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterKampfGUI(ctx, kill, eat).setVisible(true);
            }
        });
    }
    
    @Override
    public void poll() {
        if(AlterKampfGUI.runScript) {
            for(Task task : taskList)
                if(task.activate())
                    task.execute();
        }
    }
}
