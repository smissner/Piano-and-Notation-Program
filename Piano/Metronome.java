import java.io.*;
import java.util.*;
import javax.sound.midi.*;
public class Metronome extends Thread implements Runnable{
  double tempo;
  public Metronome(double speed){
   tempo = speed; 
  }
  Synthesizer synth;
  {try { synth = MidiSystem.getSynthesizer(); 
    {  synth.open(); }} catch (MidiUnavailableException g) {}}
   MidiChannel drum  = synth.getChannels()[9];
   
   public void run(){
     while (true){
    drum.noteOn(56,100);
    try{
      Thread.sleep(((long)((double)60/tempo) * 1 * 1000));
    }catch (InterruptedException g) {}}
    
    
  }
   public void update(double speed){
    tempo = speed; 
   }
  
  
  
  
  
}