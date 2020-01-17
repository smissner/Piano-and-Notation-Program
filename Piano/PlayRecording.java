import java.io.*;
import java.util.*;
import javax.sound.midi.*;
public class PlayRecording extends Thread implements Runnable{
  private MidiChannel channel,drum;
  private ArrayList<NoteAction> records;
  private int key,set;
  boolean isSeventh;
  boolean isNinth;
  int inv;
  int seventh;
  int ninth;
    public PlayRecording(MidiChannel chan,MidiChannel drummer,ArrayList<NoteAction> record
                         ,int drumset,boolean isSeventh, int inve, int place,int sseventh, boolean issNinth,int nninth){
   channel = chan;
   records = record;
   key = place;
   set = drumset;
   drum = drummer;
   inv = inve;
   seventh = sseventh;
   isNinth = issNinth;
   ninth = nninth;
  }
  public void run(){
     long sep = 0;
   for (int i = 0; i<records.size(); i++)
   {
     if (i != records.size()-1)
       sep = records.get(i+1).actionTimer() - records.get(i).actionTimer();                            
     if (records.get(i).onorOff()){
       switch (records.get(i).thisEvent()){
       case 0: channel.noteOn(key + 60, 100);
       break;
       case 1: channel.noteOn(key + 61, 100);
       break;
       case 2: channel.noteOn(key + 62, 100);
       break;
       case 3: channel.noteOn(key + 63, 100);
       break;
       case 4: channel.noteOn(key + 64, 100);
       break;
       case 5: channel.noteOn(key + 65, 100);
       break;
       case 6: channel.noteOn(key + 66, 100);
       break;
       case 7: channel.noteOn(key + 67, 100);
       break;
       case 8: channel.noteOn(key + 68, 100);
       break;
       case 9: channel.noteOn(key + 69, 100);
       break;
       case 10: channel.noteOn(key + 70, 100);
       break;
       case 11: channel.noteOn(key + 71, 100);
       break;
       case 12: channel.noteOn(key + 72, 100);
       break;
       case 13: drum.noteOn(set + 62, 100);
       break;
        case 14:  drum.noteOn(set + 63, 100); System.out.println(i);
       break;
        case 15:  drum.noteOn(set + 64, 100); System.out.println(i);
       break;
        case 16:  drum.noteOn(set + 65, 100); System.out.println(i);
       break;
        case 17:  drum.noteOn(set + 66, 100); System.out.println(i);
       break;
        case 18:  drum.noteOn(set + 67, 100); System.out.println(i);
       break;
        case 19:  drum.noteOn(set + 68, 100); System.out.println(i);
       break;
        case 20:  drum.noteOn(set + 69, 100); System.out.println(i);
       break;
        case 21:  drum.noteOn(set + 70, 100); System.out.println(i);
       break;
        case 22:  drum.noteOn(set + 71, 100); System.out.println(i);
       break;
        case 23:  drum.noteOn(set + 72, 100); System.out.println(i);
       break;
        case 24:  drum.noteOn(set + 73, 100); System.out.println(i);
       break;
       case 25: majorChord(channel,60, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 26: majorChord(channel,61, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 27: majorChord(channel,62, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 28: majorChord(channel,63, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 29: majorChord(channel,64, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 30: majorChord(channel,65, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 31: majorChord(channel,66, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 32: majorChord(channel,67, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 33: majorChord(channel,68, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 34: majorChord(channel,69, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 35: majorChord(channel,70, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 36: majorChord(channel,71, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 37: majorChord(channel,72, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
       case 38: minorChord(channel,60, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 39: minorChord(channel,61, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 40: minorChord(channel,62, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 41: minorChord(channel,63, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 42: minorChord(channel,64, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 43: minorChord(channel,65, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
       case 44: minorChord(channel,66, isSeventh, inv, key,seventh,isNinth,ninth); 
       break;
        case 45: minorChord(channel,67, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 46: minorChord(channel,68, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 47: minorChord(channel,69, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 48: minorChord(channel,70, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 49: minorChord(channel,71, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
        case 50: minorChord(channel,72, isSeventh, inv, key,seventh,isNinth,ninth);
       break;
       case 51:  dimChord(channel,60, isSeventh, inv, key,seventh,isNinth);
       break;
       case 52:  dimChord(channel,61, isSeventh, inv, key,seventh,isNinth);
       break;
       case 53:  dimChord(channel,62, isSeventh, inv, key,seventh,isNinth);
       break;
       case 54:  dimChord(channel,63, isSeventh, inv, key,seventh,isNinth);
       break;
       case 55:  dimChord(channel,64, isSeventh, inv, key,seventh,isNinth);
       break;
       case 56:  dimChord(channel,65, isSeventh, inv, key,seventh,isNinth);
       break;
       case 57:  dimChord(channel,66, isSeventh, inv, key,seventh,isNinth);
       break;
       case 58:  dimChord(channel,67, isSeventh, inv, key,seventh,isNinth);
       break;
       case 59:  dimChord(channel,68, isSeventh, inv, key,seventh,isNinth);
       break;
       case 60:  dimChord(channel,69, isSeventh, inv, key,seventh,isNinth);
       break;
       case 61:  dimChord(channel,70, isSeventh, inv, key,seventh,isNinth);
       break;
       case 62:  dimChord(channel,71, isSeventh, inv, key,seventh,isNinth);
       break;
       case 63:  dimChord(channel,72, isSeventh, inv, key,seventh,isNinth);
       break;   
       case 74: channel.noteOn(key + 73, 100);
       break;
       case 75: channel.noteOn(key + 74, 100);
       break;
        case 76: channel.noteOn(key + 75, 100);
       break;
       case 77: channel.noteOn(key + 76, 100);
       break;
       case 78: channel.noteOn(key + 77, 100);
       break;
       case 79: channel.noteOn(key + 78, 100);
       break;
       case 80: channel.noteOn(key + 79, 100);
       break;
       case 81: channel.noteOn(key + 80, 100);
       break;
       case 82: channel.noteOn(key + 81, 100);
       break;
       case 83: channel.noteOn(key + 82, 100);
       break;
       case 84: channel.noteOn(key + 83, 100);
       break;
       case 85: channel.noteOn(key + 84, 100);
       break;
       case 86: channel.noteOn(key + 85, 100);
       break;
       case 87: channel.noteOn(key + 86, 100);
       break;
       case 88: channel.noteOn(key + 87, 100);
       break;
       case 89: channel.noteOn(key + 88, 100);
       break;
       case 90: channel.noteOn(key + 89, 100);
       break;
       case 91: channel.noteOn(key +90, 100);
       break;
        case 92: channel.noteOn(key +91, 100);
       break;
        case 93: channel.noteOn(key +92, 100);
       break;
        case 94: channel.noteOn(key +93, 100);
       break;
        case 95: channel.noteOn(key +94, 100);
       break;
        case 96: channel.noteOn(key +95, 100);
       break;
        case 97: channel.noteOn(key +96, 100);
       break;
        case 98: channel.noteOn(key + 97, 100);
       break;
        case 99: key += 12;
       break;
        case 100: key -= 12;
       break;
              case 101:    if (inv == 2)
        inv = 0;
      else
        inv++; break;
                        case 102:   if (inv == 3)
        inv = 0;
      else 
        inv++; break;
                        case 103:   if (isNinth)
          isNinth = false;
        else
          isNinth = true; break;
                        case 104:  if (isSeventh)
      {isSeventh = false;
        if (inv == 3)
          inv = 0;}
      else
        isSeventh = true; break;
                        case 105:     if (seventh == -2)
           seventh = 0;
         else
           seventh--; break;
                        case 106:  if (ninth == 1)
           ninth = -1;
         else
           ninth++; break;
           case 107: key++; break;
         case 108: key--; break;
     }
     
     }else{ 
      
       switch (records.get(i).thisEvent()){
       case -1: channel.allNotesOff();
       break;
       case 0: channel.noteOff(key + 60);
       break;
       case 1: channel.noteOff(key + 61);
       break;
       case 2: channel.noteOff(key + 62);
       break;
       case 3: channel.noteOff(key + 63);
       break;
       case 4: channel.noteOff(key + 64);
       break;
       case 5: channel.noteOff(key + 65);
       break;
       case 6: channel.noteOff(key + 66);
       break;
       case 7: channel.noteOff(key + 67);
       break;
       case 8: channel.noteOff(key + 68);
       break;
       case 9: channel.noteOff(key + 69);
       break;
       case 10: channel.noteOff(key + 70);
       break;
       case 11: channel.noteOff(key + 71);
       break;
       case 12: channel.noteOff(key + 72);
       break;
        case 13: channel.noteOff(key + 73);
       break;
       case 14: channel.noteOff(key + 74);
       break;
       case 15: channel.noteOff(key + 75);
       break;
       case 16: channel.noteOff(key + 76);
       break;
       case 17: channel.noteOff(key + 77);
       break;
       case 18: channel.noteOff(key + 78);
       break;
       case 19: channel.noteOff(key + 79);
       break;
       case 20: channel.noteOff(key + 80);
       break;
       case 21: channel.noteOff(key + 81);
       break;
       case 22: channel.noteOff(key + 82);
       break;
       case 23: channel.noteOff(key + 83);
       break;
       case 24: channel.noteOff(key + 84);
       break;
       case 25: channel.noteOff(key + 85);
       break;
       case 26: channel.noteOff(key + 86);
       break;
       case 27: channel.noteOff(key + 87);
       break;
       case 28: channel.noteOff(key +88);
       break;
       case 29: channel.noteOff(key +89);
       break;
       case 30: channel.noteOff(key + 90);
       break;
       case 31: channel.noteOff(key + 91);
       break;
       case 32: channel.noteOff(key + 92);
       break;
       case 33: channel.noteOff(key + 93);
       break;
       case 34: channel.noteOff(key + 94);
       break;
       case 35: channel.noteOff(key + 95);
       break;
       case 36: channel.noteOff(key + 96);
       break;
       case 37: channel.noteOff(key + 97);
       break;
     }
    }
     
     try {  Thread.sleep(sep); } catch(InterruptedException g) {}
    
                      
    
     
     
     
     
     
   }
  }
  public void minorChord(MidiChannel chan, int root, boolean seventh, int inversion, int key,int typeOfSeventh,boolean ninth,int nt){
    if (ninth)
      chan.noteOn(key + root + 14 + nt,100);
    if (seventh)
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 3,100);
        chan.noteOn(key+ root + 7,100);
        chan.noteOn(key + root + 11 +typeOfSeventh, 100);
      }
      if (inversion == 1){
        chan.noteOn(key + root + 3, 100);
        chan.noteOn(key + root + 7, 100);
        chan.noteOn(key + root + 11+ typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 7,100);
        chan.noteOn(key + root + 11+ typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
      }
      if (inversion == 3){
        chan.noteOn(key + root + 11 +typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
        chan.noteOn(key + root + 19, 100);
      }
    }
    else
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 3,100);
        chan.noteOn(key+ root + 7,100);
      }
      if (inversion == 1){
        chan.noteOn(key + root + 3, 100);
        chan.noteOn(key + root + 7, 100);
        chan.noteOn(key + root + 12, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 7,100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
      }}}
  public static void majorChord(MidiChannel chan, int root, boolean seventh, int inversion, int key, int typeOfSeventh, boolean ninth, int nt){
    if (ninth)
      chan.noteOn(key + root + 14 + nt,100);
    if (seventh)
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 4,100);
        chan.noteOn(key+ root + 7,100);
        chan.noteOn(key + root + 11 + typeOfSeventh, 100);
      }
      if (inversion == 1){
        chan.noteOn(key + root + 4, 100);
        chan.noteOn(key + root + 7, 100);
        chan.noteOn(key + root + 11 + typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 7,100);
        chan.noteOn(key + root + 11 + typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 16, 100);
      }
      if (inversion == 3){
        chan.noteOn(key + root + 11 + typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 16, 100);
        chan.noteOn(key + root + 19, 100);
      }
    }
    else
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 4,100);
        chan.noteOn(key+ root + 7,100);
      }
      if (inversion == 1){
        chan.noteOn(key + root + 4, 100);
        chan.noteOn(key + root + 7, 100);
        chan.noteOn(key + root + 12, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 7,100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 16, 100);
      }}
  }
  public void dimChord(MidiChannel chan, int root, boolean seventh, int inversion, int key,int typeOfSeventh, boolean ninth){
     if (ninth)
      chan.noteOn(key + root + 13,100);
    if (seventh)
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 3,100);
        chan.noteOn(key+ root + 6,100);
        chan.noteOn(key + root + 11 +typeOfSeventh, 100);
      }
      if (inversion == 1){
        chan.noteOn(key + root + 3, 100);
        chan.noteOn(key + root + 6, 100);
        chan.noteOn(key + root + 11+typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 6,100);
        chan.noteOn(key + root + 11+typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
      }
      if (inversion == 3){
        chan.noteOn(key + root + 11+ typeOfSeventh, 100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
        chan.noteOn(key + root + 18, 100);
      }
    }
    else
    {
      if (inversion == 0)
      {chan.noteOn(key + root, 100);
        chan.noteOn(key + root + 3,100);
        chan.noteOn(key+ root + 6,100);
      }
      //i declare a thumb war
      if (inversion == 1){
        chan.noteOn(key + root + 3, 100);
        chan.noteOn(key + root + 6, 100);
        chan.noteOn(key + root + 9, 100);
      }
      if (inversion == 2){
        chan.noteOn(key + root + 6,100);
        chan.noteOn(key + root + 12, 100);
        chan.noteOn(key + root + 15, 100);
      }}}
  
}