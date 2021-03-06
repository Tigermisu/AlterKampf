// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;


import java.util.Arrays;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Npc;

public class CtxAccess extends Task<ClientContext> {
  
    public CtxAccess(ClientContext ctx) {
        super(ctx);
    }
    
    @Override
    public boolean activate() {
        return false;
    }
    
    @Override
    public void execute() {}
    
    public String[] getInventory() {
        //refresh the query.
        ctx.inventory.select();
        int arraySize = ctx.inventory.select(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return Arrays.asList(item.actions()).contains("Eat");
            }
        }).size();
        String[] finalArray = new String[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalArray[i] = ctx.inventory.poll().name();
        }
        return finalArray;    
    }
    
    public String[] getMonsterNames() {
        //refresh the query.
        ctx.npcs.select();
        int arraySize = ctx.npcs.select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return Arrays.asList(npc.actions()).contains("Attack");
            }
        }).nearest().limit(30).size();
        String[] finalArray = new String[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalArray[i] = ctx.npcs.poll().name();
        }
        return finalArray;
    }
    public int[] getFoodIDs(String[] names) {
      int[] finalarray;
        int arraySize;
        if(names.length > 0) {
            arraySize = ctx.inventory.select().name(names).size();
            finalarray = new int[arraySize];
            for(int i = 0; i < arraySize; i++) {
                finalarray[i] = ctx.inventory.poll().id();
            }
        } else
            finalarray = new int[]{};
        return finalarray;  
    }
    
    public int[] getMonsterIDs(String[] names) {
        int[] finalarray;
        int arraySize;
        arraySize = ctx.npcs.select().name(names).size();
        finalarray = new int[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalarray[i] = ctx.npcs.poll().id();
        }
        return finalarray;
    }
}