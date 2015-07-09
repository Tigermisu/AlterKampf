// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu

package alterkampf;

// Java utils
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Powerbot
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.PaintListener;


@Script.Manifest(name = "Alter Kampfer", description = "Simple autofighter for any monster.", properties = "client=4")

public class AlterKampf extends PollingScript<ClientContext> implements PaintListener {

    //Variables used in polling/script
    List<Task> taskList = new ArrayList<Task>();
    AlterKampfGUI gui;

    //Variables used in the paint;   
    public static String status = "Waiting";
    final int x = 20, y = 400, width = 300;
    final String title = "Autokampfer Alpha Release";
    String currentTime = "00:00:00";    
    final Font font = new Font("URW Chancery L", Font.PLAIN, 11);
    final float startAttackXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_ATTACK);
    final float startStrengthXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_STRENGTH);
    final float startDefenceXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_DEFENSE);
    final float startHitpointsXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_HITPOINTS);
    float attackXp, strengthXp, defenceXp, hitpointsXp, totalXp;
    //For keeping track of time
    final long startTime = System.currentTimeMillis();
    int hours = (int) (System.currentTimeMillis() - startTime) / 3600000;
    int minutes = (int) (System.currentTimeMillis() - startTime) / 60000 - hours * 60;
    int seconds = (int) (System.currentTimeMillis() - startTime) / 1000 - hours * 3600 - minutes * 60;
    long finalTime = System.currentTimeMillis() - startTime;
    int timeElapsed = hours + minutes + seconds;
    double runTime;
    //Strings array to be painted
    String[] paintstrings = new String[]{
        "Elapsed Time:" + currentTime,
        "Status: " + status,
        "Attack xp: 0",
        "Strength xp: 0",
        "Defence xp: 0",
        "Hitpoints xp: 0",
        "Total xp: 0",
        "Total xp per hour: 0" 
    };
    final Rectangle box = new Rectangle(x, y, width, ((paintstrings.length + 1) * 12) + 2);
    
    
    @Override
    public void start() {
        //Create the Tasks and store their reference for the GUI to use.
        Kill kill = new Kill(ctx);
        Eat eat = new Eat(ctx);
        Antiban antiban = new Antiban(ctx);
        LogOut logout = new LogOut(ctx);
        //Add the tasks to the task list
        taskList.addAll(Arrays.asList(kill, eat, antiban, logout));
        System.out.println("Script version v: 0.25 Alpha Release ");
        //Invoke the gui
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new AlterKampfGUI(ctx, kill, eat);
                gui.setVisible(true);
            }
        });
    }
    
    @Override
    public void poll() {
        if(AlterKampfGUI.runScript) {
            for(Task task : taskList) {
                if(task.activate()) {
                    task.execute();
                }
            }
        }
    }
    
    @Override
    public void stop() {
        gui.dispose();
    }
    
    @Override
    public void resume() {
        gui.setVisible(true);
    }    
    
    /* BEGIN PAINT */
    //Functions for painting as defined by Ian's General PaintBox
    public int getXPositioningForString(Graphics2D g, String string) {
        return (int) g.getFontMetrics().getStringBounds(string, g).getCenterX();
    }
    
    public void drawString(Graphics2D g, String s, Rectangle container, int y) {
        g.drawString(s, (int) container.getMinX() + ((int) container.getWidth() / 2) - getXPositioningForString(g, s), y);
    }



    @Override
    public void repaint(Graphics graphics) {
        //First, calculate all the info
        currentTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        attackXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_ATTACK);
        strengthXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_STRENGTH);
        defenceXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_DEFENSE);
        hitpointsXp = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_HITPOINTS);
        totalXp = attackXp + strengthXp + defenceXp + hitpointsXp;
        runTime = (double) (System.currentTimeMillis() - startTime) / 3600000;

        //Then, set the values in the paintstrings array
        paintstrings[0] = "Elapsed time: " + currentTime;
        paintstrings[1] = "Status: " + status;
        paintstrings[2] = "Attack xp: " + (attackXp - startAttackXp);
        paintstrings[3] = "Strength xp: " + (strengthXp - startStrengthXp);
        paintstrings[4] = "Defence xp: " + (defenceXp - startDefenceXp);
        paintstrings[5] = "Hitpoints xp: " + (hitpointsXp - startHitpointsXp);
        paintstrings[6] = "Total xp: " + totalXp;
        paintstrings[7] = "Total xp per hour: " + (int)(totalXp / runTime) + "xp/h";   
        
        //Finally, do Ian's drawing magic
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.black);
        g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g.fillRect(x, y, width, ((paintstrings.length + 1) * 12) + 2);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.drawRect(x, y, width, ((paintstrings.length + 1) * 12) + 2);
        g.setColor(Color.white);
        g.drawRect(x, y - 1, width, ((paintstrings.length + 1) * 12) + 2);
        drawString(g, title, box, y + 11);
        g.drawLine(x, y + 12, x + width, y + 12);
        for (int i = 0; i < paintstrings.length; i++) {
            g.drawString(paintstrings[i], x + 4, y + 24 + (i * 12));
        }
    }
}
