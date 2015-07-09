// AlterKampf RSBot Autofighter for RSBot v6 - rt4 client
// Made by Tigermisu
package alterkampf;


import java.util.regex.Pattern;
import org.powerbot.script.rt4.ClientContext;

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
        int arraySize = ctx.inventory.select().size();
        String[] finalArray = new String[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalArray[i] = ctx.inventory.poll().name();
        }
        return finalArray;    
    }
    
    public String[] getMonsterNames() {
        int arraySize = ctx.npcs.select().nearest().limit(30).size();
        String[] finalArray = new String[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalArray[i] = ctx.npcs.poll().name();
        }
        return finalArray;
    }
    public int[] getFoodIDs(String[] names) {
      int[] finalarray;
        int arraySize;
        String pattern;
        if(names.length > 0) {
            pattern = names[0];
            for(int i = 1; i < names.length; i++) {
                pattern += "|" + names[i];
            }
            //ctx.backpack.select().name(Pattern.compile("(Copper|Tin) ore")).each(...)
            arraySize = ctx.inventory.select().name(Pattern.compile(pattern)).size();
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
        String pattern;
        pattern = names[0];
        for(int i = 1; i < names.length; i++) {
            pattern += "|" + names[i];
        }
        //ctx.backpack.select().name(Pattern.compile("(Copper|Tin) ore")).each(...)
        arraySize = ctx.npcs.select().name(Pattern.compile(pattern)).size();
        finalarray = new int[arraySize];
        for(int i = 0; i < arraySize; i++) {
            finalarray[i] = ctx.npcs.poll().id();
        }
        return finalarray;
    }
}