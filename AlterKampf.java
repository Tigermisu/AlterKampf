package alterkampf;

import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.PaintListener;

import org.powerbot.script.MessageListener;


@Script.Manifest(name = "Alter Kampfer", description = "Simple autofighter for any monster.", properties = "client=4")

public class AlterKampf extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    //Variables used in polling/script
    List<Task> taskList = new ArrayList<Task>();
    AlterKampfGUI gui;
    //Variables used in the paint   
    public static String status = "Waiting";
    private final int BOX_POSITION_X = 5, BOX_POSITION_Y = 220, BOX_WIDTH = 300;
    private final String TITLE = "AlterKampf v1.0";
    private String currentTime = "00:00:00";    
    private final Font FONT = new Font("URW Chancery L", Font.PLAIN, 11);
    private final float START_ATTACK_XP = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_ATTACK);
    private final float START_STRENGTH_XP = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_STRENGTH);
    private final float START_DEFENCE_XP = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_DEFENSE);
    private final float START_HITPOINTS_XP = ctx.skills.experience(org.powerbot.script.rt4.Constants.SKILLS_HITPOINTS);
    private float attackXp, strengthXp, defenceXp, hitpointsXp, totalXp;
    //For keeping track of time
    final long START_TIME = System.currentTimeMillis();
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private double runTime;
    //Strings array to be painted
    private String[] paintstrings = new String[]{
        "Elapsed Time:" + currentTime,
        "Status: " + status,
        "Attack xp: 0",
        "Strength xp: 0",
        "Defence xp: 0",
        "Hitpoints xp: 0",
        "Total xp: 0",
        "Total xp per hour: 0" 
    };
    private final Rectangle BOX = new Rectangle(BOX_POSITION_X, BOX_POSITION_Y, BOX_WIDTH, ((paintstrings.length + 1) * 12) + 2);
    
    @Override
    public void start() {
        Kill kill = new Kill(ctx);
        Eat eat = new Eat(ctx);
        taskList.addAll(Arrays.asList(kill, eat));
        EventQueue.invokeLater(new Runnable() {
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
    public void suspend() {
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
        hours = (int) (System.currentTimeMillis() - START_TIME) / 3600000;
        minutes = (int) (System.currentTimeMillis() - START_TIME) / 60000 - hours * 60;
        seconds = (int) (System.currentTimeMillis() - START_TIME) / 1000 - hours * 3600 - minutes * 60;
        currentTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        attackXp = ctx.skills.experience(Constants.SKILLS_ATTACK);
        strengthXp = ctx.skills.experience(Constants.SKILLS_STRENGTH);
        defenceXp = ctx.skills.experience(Constants.SKILLS_DEFENSE);
        hitpointsXp = ctx.skills.experience(Constants.SKILLS_HITPOINTS);
        totalXp = attackXp + strengthXp + defenceXp + hitpointsXp - START_ATTACK_XP - START_STRENGTH_XP - START_DEFENCE_XP - START_HITPOINTS_XP;
        runTime = (double)(System.currentTimeMillis() - START_TIME) / 3600000;

        //Then, set the values in the paintstrings array
        paintstrings[0] = "Elapsed time: " + currentTime;
        paintstrings[1] = "Status: " + status;
        paintstrings[2] = "Attack xp: " + (attackXp - START_ATTACK_XP);
        paintstrings[3] = "Strength xp: " + (strengthXp - START_STRENGTH_XP);
        paintstrings[4] = "Defence xp: " + (defenceXp - START_DEFENCE_XP);
        paintstrings[5] = "Hitpoints xp: " + (hitpointsXp - START_HITPOINTS_XP);
        paintstrings[6] = "Total xp: " + totalXp;
        paintstrings[7] = "Total xp per hour: " + (int)(totalXp / runTime) + "xp/h";   
        
        //Finally, do Ian's drawing magic
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(FONT);
        g.setColor(Color.black);
        g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g.fillRect(BOX_POSITION_X, BOX_POSITION_Y, BOX_WIDTH, ((paintstrings.length + 1) * 12) + 2);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.drawRect(BOX_POSITION_X, BOX_POSITION_Y, BOX_WIDTH, ((paintstrings.length + 1) * 12) + 2);
        g.setColor(Color.white);
        g.drawRect(BOX_POSITION_X, BOX_POSITION_Y - 1, BOX_WIDTH, ((paintstrings.length + 1) * 12) + 2);
        drawString(g, TITLE, BOX, BOX_POSITION_Y + 11);
        g.drawLine(BOX_POSITION_X, BOX_POSITION_Y + 12, BOX_POSITION_X + BOX_WIDTH, BOX_POSITION_Y + 12);
        for (int i = 0; i < paintstrings.length; i++) {
            g.drawString(paintstrings[i], BOX_POSITION_X + 4, BOX_POSITION_Y + 24 + (i * 12));
        }
    }
    /* END PAINT */
    
    @Override
    public void messaged(MessageEvent m) {
        if (m.text().startsWith("I'm already")) {
            Kill.retaliate = true;
        }
    }
}
