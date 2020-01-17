import java.io.*;
import java.util.*;
import javax.sound.midi.*;
public class ThreadedNotation extends Thread implements Runnable{
 
  private MidiChannel channel;
  private ArrayList<Notation> notate;
  private String inst;
  private ArrayList<Integer> vols;
  public ThreadedNotation(MidiChannel chan, ArrayList arr, String arrr, ArrayList vol){
    
    notate = arr;
    channel = chan;
    inst= arrr;
    vols = vol;
    
  }
  public void run(){
    
   for (int i = 0; i <notate.size();i++){
     Scanner scan = new Scanner(inst);
     channel.programChange(scan.nextInt(), scan.nextInt());
        for (int j = 0; j<notate.get(i).getActions().length; j++){
       if (notate.get(i).getActions()[j])
         channel.noteOn( j, vols.get(i));
      }try{ Thread.sleep((long)notate.get(i).time());
        channel.allNotesOff();
      } catch (InterruptedException g){}}} 
  }
  
  
  
