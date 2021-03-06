package alterkampf;

import java.util.Arrays;
import java.util.HashSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.powerbot.script.rt4.ClientContext;


public class AlterKampfGUI extends javax.swing.JFrame {

    /**
     * Creates new form AlterKampfGUI
     */
    private CtxAccess accessor;
    private Kill kill;
    private Eat eat;
    private DefaultListModel lootModel = new DefaultListModel();
    
    public AlterKampfGUI(ClientContext ctx, Kill kill, Eat eat)  {
        initComponents();
        accessor = new CtxAccess(ctx);
        fetchEnemies();
        fetchInventory();
        this.eat = eat;
        this.kill = kill;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        enemyLabel = new javax.swing.JLabel();
        enemyScrollPane = new javax.swing.JScrollPane();
        enemyList = new javax.swing.JList();
        enemyButton = new javax.swing.JButton();
        foodLabel = new javax.swing.JLabel();
        healthLabel = new javax.swing.JLabel();
        minHealth = new javax.swing.JTextField();
        foodScrollPane = new javax.swing.JScrollPane();
        foodList = new javax.swing.JList();
        inventoryButton = new javax.swing.JButton();
        lootScrollPane = new javax.swing.JScrollPane();
        lootList = new javax.swing.JList();
        lootLabel = new javax.swing.JLabel();
        lootId = new javax.swing.JTextField();
        lootButton = new javax.swing.JButton();

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        setTitle("Alterkampf Autofighter");

        startButton.setText("Start/Update Script");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause Script");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        enemyLabel.setText("Select Enemies to Attack");

        enemyList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Please find nearby guys" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        enemyList.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        enemyList.setMinimumSize(new java.awt.Dimension(110, 120));
        enemyScrollPane.setViewportView(enemyList);

        enemyButton.setText("Find Nearby");
        enemyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemyButtonActionPerformed(evt);
            }
        });

        foodLabel.setText("Select Food");

        healthLabel.setText("Enter health to eat at");

        minHealth.setText("10");
        minHealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minHealthActionPerformed(evt);
            }
        });

        foodList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "select food" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        foodList.setPreferredSize(new java.awt.Dimension(120, 120));
        foodScrollPane.setViewportView(foodList);

        inventoryButton.setText("Refresh Inventory");
        inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryButtonActionPerformed(evt);
            }
        });

        lootScrollPane.setViewportView(lootList);

        lootLabel.setText("Items to Loot:");

        lootButton.setText("Add ID");
        lootButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lootButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enemyScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(enemyLabel)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(enemyButton)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(foodScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(inventoryButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lootScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lootId, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lootButton)))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(foodLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lootLabel)
                        .addGap(72, 72, 72))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(pauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(healthLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(minHealth, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(enemyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(foodLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(foodScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lootLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lootScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enemyButton)
                    .addComponent(inventoryButton)
                    .addComponent(lootId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lootButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minHealth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(healthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(pauseButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        boolean error = false;
        error = error || setTargetMonsters();
        setFood();
        setLoot();
        eat.minhealth = Double.parseDouble(minHealth.getText());
        
        if(!error)
            runScript = true;
    }//GEN-LAST:event_startButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        runScript = false;        
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void enemyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enemyButtonActionPerformed
        fetchEnemies();      
    }//GEN-LAST:event_enemyButtonActionPerformed

    private void minHealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minHealthActionPerformed
        
    }//GEN-LAST:event_minHealthActionPerformed

    private void inventoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryButtonActionPerformed
        fetchInventory();
    }//GEN-LAST:event_inventoryButtonActionPerformed

    private void lootButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lootButtonActionPerformed
        int loot;
        try {
            loot = Integer.parseInt(lootId.getText());
            lootModel.add(lootModel.getSize(), loot);
            lootList.setModel(lootModel);  
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Please, enter only integer numbers.");
        }
              
    }//GEN-LAST:event_lootButtonActionPerformed

    private void setLoot(){
        int arrayLength = lootModel.getSize();
        if(arrayLength > 0) {
            int[] lootArray = new int[arrayLength];
            for(int i = 0; i < arrayLength; i++) {
                lootArray[i] = (int)lootModel.getElementAt(i);
            }
            Loot.loot = true;
            Loot.itemIds = lootArray;
        } else {
            Loot.loot = false;
        }
    }
    
    private void setFood(){
        // Get the index of all the selected items
        int[] selectedIx = foodList.getSelectedIndices();
        int[] foodIDs;
        String[] names = new String[selectedIx.length];
        // Get all the selected items using the indices
        for (int i = 0; i < selectedIx.length; i++) {
          names[i] = (String) foodList.getModel().getElementAt(selectedIx[i]);
        }
        foodIDs = accessor.getFoodIDs(names);
        if(foodIDs.length > 0)
            eat.foodId = foodIDs;
        else {
            JOptionPane.showMessageDialog(null, "Warning: Script will run without food");
            eat.hasFood = false;
        }
    }
    
    private boolean setTargetMonsters() {
        // Get the index of all the selected items
        int[] selectedIx = enemyList.getSelectedIndices();
        if(selectedIx.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a monster to attack");
            return true;
        }
        String[] names = new String[selectedIx.length];
        // Get all the selected items using the indices
        for (int i = 0; i < selectedIx.length; i++) {
          names[i] = (String) enemyList.getModel().getElementAt(selectedIx[i]);
        }
        kill.npcIDs = accessor.getMonsterIDs(names);
        return false;
    }
    
    private void fetchInventory(){
        DefaultListModel foodListModel = new DefaultListModel();
        String[] inventory = accessor.getInventory();
        inventory = new HashSet<String>(Arrays.asList(inventory)).toArray(new String[0]);
        
        for(String mName : inventory) {
            foodListModel.addElement(mName);
        }              
        
        foodList.setModel(foodListModel);  
    }
    
    private void fetchEnemies(){
        DefaultListModel monstersList = new DefaultListModel();
        String[] monsterNames = accessor.getMonsterNames();
        monsterNames = new HashSet<String>(Arrays.asList(monsterNames)).toArray(new String[0]);
        
        for(String mName : monsterNames) {
            monstersList.addElement(mName);
        }            
        
        enemyList.setModel(monstersList);  
    }
    
    //GUI Options
    public static boolean runScript = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlterKampfGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterKampfGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterKampfGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterKampfGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton enemyButton;
    private javax.swing.JLabel enemyLabel;
    private javax.swing.JList enemyList;
    private javax.swing.JScrollPane enemyScrollPane;
    private javax.swing.JLabel foodLabel;
    private javax.swing.JList foodList;
    private javax.swing.JScrollPane foodScrollPane;
    private javax.swing.JLabel healthLabel;
    private javax.swing.JButton inventoryButton;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton lootButton;
    private javax.swing.JTextField lootId;
    private javax.swing.JLabel lootLabel;
    private javax.swing.JList lootList;
    private javax.swing.JScrollPane lootScrollPane;
    private javax.swing.JTextField minHealth;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
