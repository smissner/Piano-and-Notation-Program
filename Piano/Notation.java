public class Notation{
  
  private boolean[] noteAct;
  private double noteTime;
  
  
  public Notation(boolean[] note, double time){
   noteAct = new boolean[note.length];
   for (int i = 0; i<note.length;i++)
     noteAct[i] = note[i];
   noteTime = time;
  }
  
  public boolean[] getActions(){
   return noteAct; 
  }
  
  public double time(){
   return noteTime; 
  }
  public boolean isNotation(){
   return true; 
  }
  public String toString(){
       String end = "";
    for (int j = 0; j<noteAct.length; j++){
       if (noteAct[j])
         end += j + " ";
  }
         end += "Hold " + (int) time() + " ";
       return end;
       
         
    
  }
  
  
  
  
  
  }