public class NoteAction {
 private int played;
 private long actionTime, instrumentChange;
 private boolean on;
 private int key; 
 public NoteAction(long time, int keys){
  played = 10000; 
  actionTime = time;
  key = keys;
 }
 public NoteAction(long time, long change){
  actionTime = time;
  played = 10001;
  instrumentChange = change;
 }
 public NoteAction(boolean onOff, long time, int whatPlay){
  on = onOff;
  actionTime = time;
  played = whatPlay;

}


 boolean onorOff(){
   return on;
 } 
 long actionTimer(){
  return actionTime; 
 }
 int thisEvent(){
  return played; 
 }
 public boolean isNotation(){
  return false; 
 }
  public String toString(){
    
       String end = "";
       if (played == 10000)
    end += "start" + " " + key;
  else{
       if (on || played == -1)
         end += played + " ";
       else if (played != 0 && played != 1)
         end += (-1 * played) + " ";
  else   end += (-10000 - (played * 10000)) + " ";}
       end += "Hold " + actionTimer() + " ";
       return end;
       
         
    
  }
}