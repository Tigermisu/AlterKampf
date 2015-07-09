/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alterkampf;

import java.awt.Graphics;
import java.awt.*;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import org.powerbot.script.PaintListener;

/**
 *
 * @author Christopher
 */

@Script.Manifest(name = "Alter Kampfer", description = "Fights Monsters, eats when low on health.", properties = "client=4")

public class AlterKampf extends PollingScript<ClientContext> implements PaintListener {

    private List<Task> taskList = new ArrayList<Task>();
    AlterKampfGUI gui;
    
    @Override
    public void start() {
        Kill kill =new Kill(ctx);
        Eat eat = new Eat(ctx);
        Antiban antiban = new Antiban(ctx);
        LogOut logout = new LogOut(ctx);
        taskList.addAll(Arrays.asList(kill, eat, antiban, logout));
        System.out.println("Script version v: 0.25 Alpha Release ");
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
        if(AlterKampfGUI.runScript && !Kill.stop) {
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
    
    
    public static String status = "Waiting";
    Timer timer = new Timer();
    int x = 20, y = 400, width = 300, height = 200;
    String title = "Autokampfer Alpha Release";
    String[] strings = new String[]{"Warning: Script is in alpha version", "Status: " + status};
    
    public int getXPositioningForString(Graphics2D g, String string) {
        return (int) g.getFontMetrics().getStringBounds(string, g).getCenterX();
    }
    
    public void drawString(Graphics2D g, String s, Rectangle container, int y) {
        g.drawString(s, (int) container.getMinX() + ((int) container.getWidth() / 2) - getXPositioningForString(g, s), y);
    }
    
    @Override
    public void repaint(Graphics graphics) {
        strings[1] = "Status: " + status;
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("URW Chancery L", Font.PLAIN, 11);
        g.setFont(font);
        Rectangle box = new Rectangle(x, y, width, ((strings.length + 1) * 12) + 2);
        g.setColor(Color.black);
        g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g.fillRect(x, y, width, ((strings.length + 1) * 12) + 2);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.drawRect(x, y, width, ((strings.length + 1) * 12) + 2);
        g.setColor(Color.white);
        g.drawRect(x, y - 1, width, ((strings.length + 1) * 12) + 2);
        drawString(g, title, box, y + 11);
        g.drawLine(x, y + 12, x + width, y + 12);
        for (int i = 0; i < strings.length; i++) {
            g.drawString(strings[i], x + 4, y + 24 + (i * 12));
        }
    }
}
