import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.sound.midi.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class KeyListenerSynth extends JFrame implements KeyListener, ItemListener, ActionListener, ChangeListener, Cloneable, WindowListener {
 @Override
  public void windowOpened(WindowEvent e)
  {}
  @Override
  public void windowClosing(WindowEvent e)
  {}

  @Override
  public void windowClosed(WindowEvent e)
  {
    dispose();
    System.exit(0);
  }
  @Override
  public void windowActivated(WindowEvent e)
  {}
  @Override
  public void windowDeactivated(WindowEvent e)
  {}    
  @Override
  public void windowDeiconified(WindowEvent e)
  {}
  @Override
  public void windowIconified(WindowEvent e)
  {}
  Scanner scan = new Scanner(System.in);
  JCheckBox cCheck, csCheck, dCheck, dsCheck, eCheck, fCheck, fsCheck, gCheck, gsCheck, aCheck, asCheck, bCheck
    ,quarter,eighth,sixteenth,half,whole, dotted, customLength;
    boolean[] noteAct = new boolean[127];
    boolean dot, met = false, full = false,good = true;
    int noteKey = 60;
    String currentNotes = "";
    JTextField whatNotes, whatRhythm;
    JSlider volume = new JSlider(JSlider.HORIZONTAL, 0 , 100,100);
    public static Integer bankChange, instChange;
    { volume.setMajorTickSpacing(10);
    volume.setMinorTickSpacing(1);
    volume.setPaintTicks(true);
    volume.setPaintLabels(true);}
    double noteType, tempo = 60, customLengthVal;
   JButton go, up, down, add, up1,up5,up10,up100, down1,down5,down10,down100,clearAll,clearLast, playLayers, addInLayer, addLoop, addLoopIgnore, changeCustomLength, saveSong, playSave, change;
  int bank, instrument, key;
  ArrayList<String> layerInst = new ArrayList<String>();{
    dot = false;
   key = 0;
   bank = 0;
  instrument = 0;
  }

  ArrayList<Integer> currentVols = new ArrayList<Integer>();
  ArrayList<ArrayList> allVols = new ArrayList<ArrayList>();
  
 
  
 int[] pre = new int[38];
 boolean[] prePlaying = new boolean[38];
 {for (int i = 0; i< prePlaying.length; i++)
   prePlaying[i] = false;}
  boolean isSeventh = false;
  boolean isNinth = false;
  int inv = 0;
  int set = -26;
  int seventh = 0;
  int ninth = 0;
  Synthesizer synth;
  MidiChannel channel, drum,channels;
  boolean recording = false;
  ArrayList<NoteAction> record = new ArrayList<NoteAction>();
  ArrayList<Notation> notate = new ArrayList<Notation>();
  ArrayList<ArrayList> layers = new ArrayList<ArrayList>();
  ArrayList<Boolean> isNotation = new ArrayList<Boolean>();
  Metronome nome = new Metronome(tempo);
   Thread jew = new Thread(nome);
  
 
    public void itemStateChanged(ItemEvent e){
      Object n =e.getItemSelectable();
      if (e.getStateChange() == ItemEvent.DESELECTED){
      if (n == cCheck) noteAct[0 + noteKey] = false; 
        if (n == csCheck) noteAct[1  + noteKey] = false; 
        if (n == dCheck) noteAct[2 + noteKey] = false; 
        if (n == dsCheck) noteAct[3 + noteKey] = false; 
        if (n == eCheck) noteAct[4 + noteKey] = false; 
        if (n == fCheck) noteAct[5 + noteKey] = false; 
        if (n == fsCheck) noteAct[6 + noteKey] = false; 
      if (n == gCheck) noteAct[7 + noteKey] = false; 
       if (n == gsCheck) noteAct[8 + noteKey] = false; 
       if (n == aCheck) noteAct[9 + noteKey] = false; 
      if (n == asCheck) noteAct[10 + noteKey] = false; 
      if (n == bCheck) noteAct[11 + noteKey] = false; 
      if (n == dotted){ dot = false;
          if (noteType == (60/tempo) * 6 * 1000)
          noteType = (60/tempo) *  4 * 1000;
        else if (noteType==(60/tempo) * 3 * 1000)
           noteType = (60/tempo) * 2 * 1000;
        else if (noteType==(60/tempo) * 1.5 * 1000)
          noteType=(60/tempo) * 1 * 1000;
        else if (noteType==(60/tempo) * .75 * 1000)
          noteType=(60/tempo) * .5 * 1000;
        else if ( noteType==(60/tempo) * .375 * 1000)
          noteType=(60/tempo) * .25 * 1000; }
      
    }
      else{  if (n == cCheck) noteAct[0 + noteKey] = true; 
        if (n == csCheck) noteAct[1 + noteKey] = true; 
        if (n == dCheck) noteAct[2 + noteKey] = true; 
        if (n == dsCheck) noteAct[3 + noteKey] = true; 
        if (n == eCheck) noteAct[4 + noteKey] = true; 
        if (n == fCheck) noteAct[5 + noteKey] = true; 
        if (n == fsCheck) noteAct[6 + noteKey] = true; 
      if (n == gCheck) noteAct[7 + noteKey] = true; 
       if (n == gsCheck) noteAct[8 + noteKey] = true; 
       if (n == aCheck) noteAct[9 + noteKey] = true; 
      if (n == asCheck) noteAct[10 + noteKey] = true; 
      if (n == bCheck) noteAct[11 + noteKey] = true;
      if (n == dotted){ customLength.setSelected(false);
        dot = true;
        if (noteType == (60/tempo) * 4 * 1000)
          noteType = (60/tempo) *  6 * 1000;
        else if (noteType==(60/tempo) * 2 * 1000)
           noteType = (60/tempo) * 3 * 1000;
        else if (noteType==(60/tempo) * 1 * 1000)
          noteType=(60/tempo) * 1.5 * 1000;
        else if (noteType==(60/tempo) * .5 * 1000)
          noteType=(60/tempo) * .75 * 1000;
        else if ( noteType==(60/tempo) * .25 * 1000)
          noteType=(60/tempo) * .375 * 1000; }
          
        if (n== whole){
          if (dot == false)
          noteType = (60/tempo) * 4 * 1000;
          else 
            noteType = (60/tempo) *  6 * 1000;
       half.setSelected(false);
       quarter.setSelected(false);
       eighth.setSelected(false);
       sixteenth.setSelected(false);
        customLength.setSelected(false);
      }
        if (n == half){
          if (!dot)
          noteType=(60/tempo) * 2 * 1000;
          else 
            noteType = (60/tempo) * 3 * 1000;
       whole.setSelected(false);
       quarter.setSelected(false);
       eighth.setSelected(false);
       sixteenth.setSelected(false);
        customLength.setSelected(false);
      }
        if (n==quarter){
          if (!dot)
          noteType=(60/tempo) * 1 * 1000;
          else
            noteType=(60/tempo) * 1.5 * 1000;
       half.setSelected(false);
       whole.setSelected(false);
       eighth.setSelected(false);
       sixteenth.setSelected(false);
        customLength.setSelected(false);
      } 
         if (n== eighth){
           if (!dot)
           noteType=(60/tempo) * .5 * 1000;
           else
             noteType=(60/tempo) * .75 * 1000;
             
       half.setSelected(false);
       quarter.setSelected(false);
       whole.setSelected(false);
       sixteenth.setSelected(false);
        customLength.setSelected(false);
      }
          if (n== sixteenth){
            if (!dot)
            noteType=(60/tempo) * .25 * 1000;
            else
             noteType=(60/tempo) * .375 * 1000; 
       half.setSelected(false);
       quarter.setSelected(false);
       eighth.setSelected(false);
       whole.setSelected(false);
       customLength.setSelected(false);
      }
          if (n == customLength){
           noteType = customLengthVal;
       dotted.setSelected(false);
       half.setSelected(false);
       quarter.setSelected(false);
       eighth.setSelected(false);
       whole.setSelected(false);
       sixteenth.setSelected(false);
          }

          
        }
      currentNotes = "";
      for (int i = 0; i<noteAct.length;i++)
      {
       if (noteAct[i])
         switch ((i-((i/12) * 12))){
         case 0: currentNotes += "C" + (i/12) + "|";
         break;
         case 1: currentNotes += "C#/Db" + (i/12)+ "|";
         break;
         case 2: currentNotes += "D" + (i/12)+ "| ";
         break;
         case 3: currentNotes += "D#/Eb" + (i/12)+ "| ";
         break;
         case 4: currentNotes += "E" + (i/12)+ "| ";
         break;
         case 5: currentNotes += "F" + (i/12)+ "| ";
         break;
         case 6: currentNotes += "F#/Gb" + (i/12)+ "| ";
         break;
         case 7: currentNotes += "G" + (i/12)+ "| ";
         break;
         case 8: currentNotes += "G#/Ab" + (i/12)+ "| ";
         break;
         case 9: currentNotes += "A" + (i/12)+ "| ";
         break;
         case 10: currentNotes += "A#/Bb" + (i/12)+ "| ";
         break;
         case 11: currentNotes += "B" + (i/12)+ "| ";
         break;
         
       }
       whatNotes.setText(currentNotes);
      }
        }
    
  
    public void actionPerformed(ActionEvent e){
      if ("go".equals(e.getActionCommand())){
     
      for (int i = 0; i <notate.size();i++){
        for (int j = 0; j<notate.get(i).getActions().length; j++){
       if (notate.get(i).getActions()[j])
         channels.noteOn( j,(int)currentVols.get(i));
      }try{ Thread.sleep((long)notate.get(i).time());
        channels.allNotesOff();
      } catch (InterruptedException g){}}}
      
     else if ("up".equals(e.getActionCommand())){
       noteKey+=12;
      if (noteKey> 127)
        noteKey = 127;
       if (!noteAct[noteKey+0])
       cCheck.setSelected(false);
       else
            cCheck.setSelected(true);
        if (!noteAct[noteKey+1])
       csCheck.setSelected(false);
        else
            csCheck.setSelected(true);
         if (!noteAct[noteKey+2])
     dCheck.setSelected(false);
         else
            dCheck.setSelected(true);
          if (!noteAct[noteKey+3])
     dsCheck.setSelected(false);
          else
            dsCheck.setSelected(true);
           if (!noteAct[noteKey+4])
     eCheck.setSelected(false);
           else
            eCheck.setSelected(true);
            if (!noteAct[noteKey+5])
     fCheck.setSelected(false);
            else
            fCheck.setSelected(true);
             if (!noteAct[noteKey+6])
     fsCheck.setSelected(false);
             else
            fsCheck.setSelected(true);
              if (!noteAct[noteKey+7])
     gCheck.setSelected(false);
              else
            gCheck.setSelected(true);
               if (!noteAct[noteKey+8])
     gsCheck.setSelected(false);
               else
            gsCheck.setSelected(true);
                if (!noteAct[noteKey+9])
     aCheck.setSelected(false);
                else
            aCheck.setSelected(true);
                 if (!noteAct[noteKey+10])
     asCheck.setSelected(false);
                 else
            asCheck.setSelected(true);
                  if (!noteAct[noteKey+11])
     bCheck.setSelected(false);
     else
            bCheck.setSelected(true);}
       else if ("down".equals(e.getActionCommand())){
          noteKey-=12;
          if (noteKey < 0)
            noteKey  = 0;
         if (!noteAct[noteKey+0])
          cCheck.setSelected(false);
         else
            cCheck.setSelected(true);
         if (!noteAct[noteKey+1])
       csCheck.setSelected(false);
         else
            csCheck.setSelected(true);
         if (!noteAct[noteKey+2])
     dCheck.setSelected(false);
         else
            dCheck.setSelected(true);
         if (!noteAct[noteKey+3])
     dsCheck.setSelected(false);
         else
            dsCheck.setSelected(true);
         if (!noteAct[noteKey+4])
     eCheck.setSelected(false);
         else
            eCheck.setSelected(true);
         //smoooc
         if (!noteAct[noteKey+5])
     fCheck.setSelected(false);
         else
            fCheck.setSelected(true);
         if (!noteAct[noteKey+6])
     fsCheck.setSelected(false);
         else
            fsCheck.setSelected(true);
         if (!noteAct[noteKey+7])
     gCheck.setSelected(false);
         else
            gCheck.setSelected(true);
         if (!noteAct[noteKey+8])
     gsCheck.setSelected(false);
         else
            gsCheck.setSelected(true);
         if (!noteAct[noteKey+9])
     aCheck.setSelected(false);
         else
            aCheck.setSelected(true);
         if (!noteAct[noteKey+10])
     asCheck.setSelected(false);
         else
            asCheck.setSelected(true);
         if (!noteAct[noteKey+11])
     bCheck.setSelected(false);
         else
            bCheck.setSelected(true);
      
         
       } else if ("Add".equals(e.getActionCommand())){ 
        notate.add(new Notation(noteAct, noteType));
        cCheck.setSelected(false);
        csCheck.setSelected(false);
        dCheck.setSelected(false);
        dsCheck.setSelected(false);
        eCheck.setSelected(false);
        fCheck.setSelected(false);
        fsCheck.setSelected(false);
        gCheck.setSelected(false);
        gsCheck.setSelected(false);
        aCheck.setSelected(false);
        asCheck.setSelected(false);
        bCheck.setSelected(false);
        for (int i = 0; i<noteAct.length;i++)
          noteAct[i] = false;
        whatNotes.setText("");
        currentVols.add(vol);
                   
       }
       else if ("1^".equals(e.getActionCommand())){
         tempo += 1; noteType -= noteType * (60/tempo);}
       else if ("5^".equals(e.getActionCommand())){
         tempo += 5;noteType -= noteType * (60/tempo);}
       else if ("10^".equals(e.getActionCommand())){
         tempo += 10;noteType -= noteType * (60/tempo);}
       else if ("100^".equals(e.getActionCommand())){
         tempo += 100;noteType -= noteType * (60/tempo);}
       else if ("1-".equals(e.getActionCommand())){
         tempo -= 1;noteType += noteType * (60/tempo);}
       else if ("5-".equals(e.getActionCommand())){
         tempo -= 5;noteType += noteType * (60/tempo);}
       else if ("10-".equals(e.getActionCommand())){
         tempo -= 10;noteType += noteType * (60/tempo);}
       else if ("100-".equals(e.getActionCommand())){
         tempo -= 100;noteType += noteType * (60/tempo);}
       else if ("All".equals(e.getActionCommand())){
         notate.clear(); currentVols.clear();}
       else if ("Last".equals(e.getActionCommand())){
         notate.remove(notate.size()-1);
         currentVols.remove(currentVols.size() - 1);}
        else if ("LayerIn".equals(e.getActionCommand())){ 
         ArrayList<Notation> temp = new ArrayList<Notation>();
          ArrayList<Integer> templ = new ArrayList<Integer>();
          for (int i = 0; i<notate.size(); i++){
            temp.add(notate.get(i));
          templ.add(currentVols.get(i));}
          layers.add(temp); 
          allVols.add(templ);
          currentVols.clear();
          notate.clear();
          isNotation.add(new Boolean(true));
          channels.programChange(0,0);
          layerInst.add(new String(bank + " " + instrument));
          bank = 0;
          instrument = 0;

        
        }
        else if ("LayerPlay".equals(e.getActionCommand())){try{ 
      
           for (int i = 0; i < layers.size(); i++)
           {
             Synthesizer sy = MidiSystem.getSynthesizer();
                 sy.open();
             if (isNotation.get(i).booleanValue()){
                
               new Thread(new ThreadedNotation(sy.getChannels()[0], layers.get(i), layerInst.get(i), allVols.get(i))).start();} 
             else
               new Thread(new PlayRecording(sy.getChannels()[0],drum,(ArrayList<NoteAction>)layers.get(i),set,isSeventh, inv, key,seventh,isNinth,ninth)).start();
             } 
           } catch (MidiUnavailableException po) {}}
           else if ("AddLoop".equals(e.getActionCommand())){ 
           ArrayList<Notation> temp = new ArrayList<Notation>();
            ArrayList<Integer> templ = new ArrayList<Integer>();
           for (int i = 0; i<100; i++){
             for (int j = 0; j<notate.size();j++){
                temp.add(notate.get(j));
               templ.add(currentVols.get(i));}
           }
            allVols.add(templ);
             layers.add(temp);
             notate.clear();
             currentVols.clear();
          isNotation.add(new Boolean(true));
          channels.programChange(0,0);
          layerInst.add(bank + " " + instrument);
          bank = 0;
          instrument = 0;
           }
                else if ("AddLoopIgnoreInitial".equals(e.getActionCommand())){
                   ArrayList<Notation> temp = new ArrayList<Notation>();
                   ArrayList<Integer> templ = new ArrayList<Integer>();
         for (int i = 0; i<notate.size(); i++)
           temp.add(notate.get(i));
          isNotation.add(new Boolean(true));
           boolean initial = true;
           for (int i = 0; i<100; i++){
             initial = false;
             for (int j = 0; j<notate.size();j++){
               for (int k = 0; k<notate.get(j).getActions().length; k++){
                 if (notate.get(j).getActions()[k])
                   initial = true;}
               if (initial)
               temp.add(notate.get(j));
             templ.add(currentVols.get(i));}
           }
            allVols.add(templ);
             layers.add(temp);
             notate.clear();
             currentVols.clear();
             channels.programChange(0,0);
          layerInst.add(bank + " " + instrument);
          bank = 0;
          instrument = 0;
           
         }
           else if ("Change".equals(e.getActionCommand())){ 
              String cusLen = JOptionPane.showInputDialog("Enter the amount of quarter notes your note takes up:");
            customLengthVal = (60/tempo) * Double.parseDouble(cusLen) * 1000;
            }
           else if ("File".equals(e.getActionCommand())){
             try{             
              File newSong = new File(JOptionPane.showInputDialog("Enter the name of your song")+ ".txt");
               PrintWriter pw  = new PrintWriter(newSong);
              if (! newSong.exists())
                newSong.createNewFile();
              int k = 0;
              int l = 0;
             for (int i = 0;  i < layers.size(); i++){
               if (isNotation.get(i).booleanValue()){
                 
                 pw.println("isNotation " + layerInst.get(k));
    
                 for (int j = 0; j<layers.get(i).size(); j++){
                   pw.println( layers.get(i).get(j).toString().substring(0,layers.get(i).get(j).toString().indexOf("Hold")) + " " + allVols.get(l).get(j) + " " +layers.get(i).get(j).toString().substring( layers.get(i).get(j).toString().indexOf("Hold")));
               }l++;}
               else{
                pw.println("notNotation ");
               for (int j = 0; j<layers.get(i).size(); j++)
                 pw.println( layers.get(i).get(j).toString());}
     
             }
             pw.close();
             } catch (IOException g) {}
      layers.clear();
           }
           else if ("save".equals(e.getActionCommand())){
             try{   
        File folder = new File(new java.io.File( "." ).getCanonicalPath());
File[] listOfFiles = folder.listFiles();
int n = 0;
for (int i = 0; i < listOfFiles.length; i++) {
  if (listOfFiles[i].isFile()) {
    if (listOfFiles[i].getName().contains(".txt"))
      n++;}}
         String[] choices = new String[n];
         n=0;
    for (int i = 0; i < listOfFiles.length; i++) {
  if (listOfFiles[i].isFile()) {
    if (listOfFiles[i].getName().contains(".txt")) {
      choices[n] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf(".txt")) ; n++;} }}
    String input = (String) JOptionPane.showInputDialog(null, "Which Song",  "SongList", JOptionPane.QUESTION_MESSAGE, null,   choices,  choices[0]);
    try{ try{
      Synthesizer sy = MidiSystem.getSynthesizer();
      sy.open();
      new Thread(new PlaySongFile(input + ".txt",sy,"top",0,drum)).start();} catch (MidiUnavailableException po) {}} catch (IOException g) {} } catch (IOException g) {} }
       else if ("change".equals(e.getActionCommand())){
        bank = Integer.parseInt(JOptionPane.showInputDialog("Enter the bank your instrument comes from"));
       instrument =  Integer.parseInt(JOptionPane.showInputDialog("Enter the instrument value"));
       channels.programChange(bank,instrument);}}
  public KeyListenerSynth() {
    
   {
     ImageIcon icon = new ImageIcon("pianoGuide.png");
  
  JLabel label = new JLabel("Click here to play");
  JLabel l = new JLabel(icon);
     add(l);
     
    addKeyListener(this);
    setSize(1300, 800);
    setVisible(true);
   
    
   }}
  int vol = 100;
  public void stateChanged(ChangeEvent e) {
 JSlider source = (JSlider)e.getSource();
 vol = source.getValue();
}
 
  {try
   {synth = MidiSystem.getSynthesizer(); 
   {  synth.open(); }
 
     Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
      channel  = synth.getChannels()[0];
      drum  = synth.getChannels()[9];
      channels  = synth.getChannels()[1];
       
     
    
  } catch (MidiUnavailableException g){}}
  
  
  @Override
  public void keyTyped(KeyEvent e) {
  }
  
  @Override
  public void keyPressed(KeyEvent e) { if (!full){
    final long time = System.currentTimeMillis();

    
    good = true;
    for (int i = 0; i<pre.length; i++)
      if (pre[i] == e.getKeyCode()&&prePlaying[i])
        good = false;
    
    if (good){

      if (pre[0] ==KeyEvent.VK_ENTER)
      {
        
        if (e.getKeyCode() == KeyEvent.VK_Q) {
          prePlaying[0] = true;
          prePlaying[4] = true;
          prePlaying[7] = true;
          if (isSeventh)
            prePlaying[11 + seventh] = true;
           if (recording)
            record.add(new NoteAction(true, time, 25));
          majorChord(channel,60, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_2) {
                    prePlaying[1] = true;
          prePlaying[5] = true;
          prePlaying[8] = true;
              if (recording)
            record.add(new NoteAction(true,time, 26));
  
          majorChord(channel,61, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_W) {
                    prePlaying[2] = true;
          prePlaying[6] = true;
          prePlaying[9] = true;
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 27));
       
          majorChord(channel,62, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_3) {
                    prePlaying[3] = true;
          prePlaying[7] = true;
          prePlaying[10] = true;
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 28));

          majorChord(channel,63, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_E) {          prePlaying[4] = true;
          prePlaying[8] = true;
          prePlaying[11] = true;
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 29));

          majorChord(channel,64, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_R) {          prePlaying[5] = true;
          prePlaying[9] = true;
          prePlaying[12] = true;
          if (isSeventh)
            prePlaying[11] = true;
          majorChord(channel,65, isSeventh, inv, key,seventh,isNinth,ninth);
            if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 30));}
        if (e.getKeyCode() == KeyEvent.VK_5) {          prePlaying[6] = true;
          prePlaying[10] = true;
    if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 31));
          majorChord(channel,66, isSeventh, inv, key,seventh,isNinth,ninth);}
        if (e.getKeyCode() == KeyEvent.VK_T) {          prePlaying[7] = true;
          prePlaying[11] = true;

    if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 32));
          majorChord(channel,67, isSeventh, inv, key,seventh,isNinth,ninth);
        }if (e.getKeyCode() == KeyEvent.VK_6){           prePlaying[8] = true;
          prePlaying[12] = true;
    if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 33));
          majorChord(channel,68, isSeventh, inv, key,seventh,isNinth,ninth);
        }if (e.getKeyCode() == KeyEvent.VK_Y){           prePlaying[9] = true;
    if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 34));
          majorChord(channel,69, isSeventh, inv, key,seventh,isNinth,ninth);
        }  if (e.getKeyCode() == KeyEvent.VK_7){           prePlaying[10] = true;
             if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 35));
          majorChord(channel,70, isSeventh, inv, key,seventh,isNinth,ninth);
        } if (e.getKeyCode() == KeyEvent.VK_U) {          prePlaying[11] = true;
          majorChord(channel,71, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 36));
        } if (e.getKeyCode() == KeyEvent.VK_I) {          prePlaying[12] = true;
          majorChord(channel,72, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 37));
        }} else if (pre[0]==KeyEvent.VK_SHIFT)
      {
        if (e.getKeyCode() == KeyEvent.VK_Q) {          prePlaying[0] = true;
          prePlaying[3] = true;
          prePlaying[7] = true;
          if (isSeventh)
            prePlaying[11 + seventh] = true;
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 38));
          minorChord(channel,60, isSeventh, inv, key,seventh,isNinth,ninth);
        } if (e.getKeyCode() == KeyEvent.VK_2) {
             prePlaying[1] = true;
          prePlaying[4] = true;
          prePlaying[8] = true;
          minorChord(channel,61, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 39));
        } if (e.getKeyCode() == KeyEvent.VK_W) {
             prePlaying[2] = true;
          prePlaying[5] = true;
          prePlaying[9] = true;
          minorChord(channel,62, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 40));
        } if (e.getKeyCode() == KeyEvent.VK_3) {
             prePlaying[3] = true;
          prePlaying[6] = true;
          prePlaying[10] = true;
          minorChord(channel,63, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 41));
        } if (e.getKeyCode() == KeyEvent.VK_E) {
             prePlaying[4] = true;
          prePlaying[7] = true;
          prePlaying[11] = true;
          minorChord(channel,64, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 42));
        } if (e.getKeyCode() == KeyEvent.VK_R) {
             prePlaying[5] = true;
          prePlaying[8] = true;
          prePlaying[12] = true;
          minorChord(channel,65, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 43));
        } if (e.getKeyCode() == KeyEvent.VK_5) {
             prePlaying[6] = true;
          prePlaying[9] = true;
  
          minorChord(channel,66, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 44));
        } if (e.getKeyCode() == KeyEvent.VK_T) {
             prePlaying[7] = true;
          prePlaying[10] = true;
         minorChord(channel,67, isSeventh, inv, key,seventh,isNinth,ninth);
             if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 45));
        }  if (e.getKeyCode() == KeyEvent.VK_6) {
             prePlaying[8] = true;
          prePlaying[11] = true;
          minorChord(channel,68, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 46));
        } if (e.getKeyCode() == KeyEvent.VK_Y) {
             prePlaying[9] = true;
          prePlaying[12] = true;
          minorChord(channel,69, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 47));
        } if (e.getKeyCode() == KeyEvent.VK_7) {
             prePlaying[10] = true;
                 if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 48));

          minorChord(channel,70, isSeventh, inv, key,seventh,isNinth,ninth);
        } if (e.getKeyCode() == KeyEvent.VK_U) {
             prePlaying[11] = true;
        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(),49));
          minorChord(channel,71, isSeventh, inv, key,seventh,isNinth,ninth);
        } if (e.getKeyCode() == KeyEvent.VK_I) {
             prePlaying[12] = true;
          minorChord(channel,72, isSeventh, inv, key,seventh,isNinth,ninth);
              if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 50));
        }}
      else if (pre[0] ==KeyEvent.VK_BACK_SLASH)
      {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
                    prePlaying[0] = true;
          prePlaying[3] = true;
          prePlaying[6] = true;
          if (isSeventh)
            prePlaying[9] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 51));
          dimChord(channel,60, isSeventh, inv, key,seventh,isNinth);
        }  if (e.getKeyCode() == KeyEvent.VK_2) {
                     prePlaying[1] = true;
          prePlaying[4] = true;
          prePlaying[7] = true;
          if (isSeventh)
            prePlaying[10] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 52));
          dimChord(channel,61, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_W) {
                     prePlaying[2] = true;
          prePlaying[5] = true;
          prePlaying[8] = true;
          if (isSeventh)
            prePlaying[11] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 53));
          dimChord(channel,62, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_3) {
                     prePlaying[3] = true;
          prePlaying[6] = true;
          prePlaying[9] = true;
          if (isSeventh)
            prePlaying[12] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 54));
          dimChord(channel,63, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_E) {
                     prePlaying[4] = true;
          prePlaying[7] = true;
          prePlaying[10] = true;
                  if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 55));
          dimChord(channel,64, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_R) {
                      prePlaying[5] = true;
          prePlaying[8] = true;
          prePlaying[11] = true;
          dimChord(channel,65, isSeventh, inv, key,seventh,isNinth);
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 56));
        } if (e.getKeyCode() == KeyEvent.VK_5) {
                      prePlaying[6] = true;
          prePlaying[9] = true;
          prePlaying[12] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 57));
          dimChord(channel,66, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_T) {
                               prePlaying[7] = true;
          prePlaying[10] = true;
          dimChord(channel,67, isSeventh, inv, key,seventh,isNinth);
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 58));
        } if (e.getKeyCode() == KeyEvent.VK_6) {
                               prePlaying[8] = true;
          prePlaying[11] = true;
                        if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 59));
          dimChord(channel,68, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_Y) {
                               prePlaying[9] = true;
                                             if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 60));
          prePlaying[12] = true;
          dimChord(channel,69, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_7) {
                                if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 61));
                prePlaying[10] = true;
          dimChord(channel,70, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_U) {
                                if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 62));
                prePlaying[11] = true;
          dimChord(channel,71, isSeventh, inv, key,seventh,isNinth);
        } if (e.getKeyCode() == KeyEvent.VK_I) {
                                if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 63));
                prePlaying[12] = true;
          dimChord(channel,72, isSeventh, inv, key,seventh,isNinth);
        }}
      else{
      
        if (e.getKeyCode() == KeyEvent.VK_Q&&!prePlaying[0]) {
                prePlaying[0] = true;
          channel.noteOn(key + 60, 100);
          if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 0));
        }
        if (e.getKeyCode() == KeyEvent.VK_2&&!prePlaying[1]) {
                prePlaying[1] = true;
          channel.noteOn(key + 61,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 1));
        }
        if (e.getKeyCode() == KeyEvent.VK_W&&!prePlaying[2]) {
                prePlaying[2] = true;
          channel.noteOn(key + 62,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 2));
        }
        if (e.getKeyCode() == KeyEvent.VK_3&&!prePlaying[3]) {
                prePlaying[3] = true;
          channel.noteOn(key + 63,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 3));
        }
        if (e.getKeyCode() == KeyEvent.VK_E&&!prePlaying[4]) {
                prePlaying[4] = true;
          channel.noteOn(key + 64,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 4));
        }
        if (e.getKeyCode() == KeyEvent.VK_R&&!prePlaying[5]) {
                prePlaying[5] = true;
          channel.noteOn(key + 65,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 5));
        }
        if (e.getKeyCode() == KeyEvent.VK_5&&!prePlaying[6]) {
                prePlaying[6] = true;
          channel.noteOn(key + 66,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 6));
        }
        if (e.getKeyCode() == KeyEvent.VK_T&&!prePlaying[7]) {
                prePlaying[7] = true;
          channel.noteOn(key + 67,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 7));
        }
        if (e.getKeyCode() == KeyEvent.VK_6&&!prePlaying[8]) {
                prePlaying[8] = true;
          channel.noteOn(key + 68,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 8));
        }
        if (e.getKeyCode() == KeyEvent.VK_Y&&!prePlaying[9]) {
                prePlaying[9] = true;
          channel.noteOn(key + 69,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 9));
        }
        if (e.getKeyCode() == KeyEvent.VK_7&&!prePlaying[10]) {
                prePlaying[10] = true;
          channel.noteOn(key + 70,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 10));
        }
        if (e.getKeyCode() == KeyEvent.VK_U&&!prePlaying[11]) {
                prePlaying[11] = true;
          channel.noteOn(key + 71,100);
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 11));
        }
        if (e.getKeyCode() == KeyEvent.VK_I&&!prePlaying[12]) {
                
          channel.noteOn(key + 72,100);
          prePlaying[12] = true;
           if (recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 12));
        }}
      if (e.getKeyCode() == KeyEvent.VK_F1){
        prePlaying[13] = true;
              drum.noteOn(set + 62, 100);
              if (recording)
                record.add(new NoteAction(true, System.currentTimeMillis(), 13));}
      if (e.getKeyCode() == KeyEvent.VK_F2){
        prePlaying[14] = true;
              drum.noteOn(set + 63,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 14));}
      if (e.getKeyCode() == KeyEvent.VK_F3){
        prePlaying[15] = true;
              drum.noteOn(set + 64,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 15));}
      if (e.getKeyCode() == KeyEvent.VK_F4){
        prePlaying[16] = true;
              drum.noteOn(set + 65,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 16));}
      if (e.getKeyCode() == KeyEvent.VK_F5){
        prePlaying[17] = true;
              drum.noteOn(set + 66,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 17));}
      if (e.getKeyCode() == KeyEvent.VK_F6){
        prePlaying[18] = true;
              drum.noteOn(set + 67,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 18));}
      if (e.getKeyCode() == KeyEvent.VK_F7){
        prePlaying[19] = true;
             drum.noteOn(set + 68,100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(), 19));}
      if (e.getKeyCode()==KeyEvent.VK_F8){
        prePlaying[20] = true;
             drum.noteOn(set + 69, 100);
            if (recording)
              record.add(new NoteAction(true, System.currentTimeMillis(),20));}
      if (e.getKeyCode()==KeyEvent.VK_F9){
        prePlaying[21] = true;
             drum.noteOn(set + 70, 100);
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 21));}
      if (e.getKeyCode()==KeyEvent.VK_F10){
        prePlaying[22] = true;
             drum.noteOn(set + 71, 100);
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 22));}
      if (e.getKeyCode()==KeyEvent.VK_F11){
        prePlaying[23] = true;
             drum.noteOn(set + 72, 100);
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 23));}
      if (e.getKeyCode()==KeyEvent.VK_F12){
        prePlaying[24] = true;
         drum.noteOn(set + 73, 100);
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 24));}
       for (int i = 0; i<= 8; i++)
       pre[i] = pre[i+1];
     pre[0] = e.getKeyCode();
   }
    if (e.getKeyCode() == KeyEvent.VK_UP){
      key+=12;  if (recording)
        record.add(new NoteAction(true,System.currentTimeMillis(), 99));}
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
          key -= 12; if (recording)
        record.add(new NoteAction(true,System.currentTimeMillis(), 100));}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
          set += 12;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
          set -= 12;
      
      
      
        if (e.getKeyCode() == KeyEvent.VK_SLASH){
        if (isSeventh)
      {isSeventh = false;
        if (inv == 3)
          inv = 0;}
      else
        isSeventh = true;
          if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),104));}
      if (e.getKeyCode() == KeyEvent.VK_N){
        if (isNinth)
          isNinth = false;
        else
          isNinth = true;
        if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),103));}
        if (e.getKeyCode() == KeyEvent.VK_V && isSeventh){
        if (inv == 3)
        inv = 0;
      else 
        inv++;
           if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),102));}
      else if (e.getKeyCode() == KeyEvent.VK_V){
        if (inv == 2)
        inv = 0;
      else
        inv++;
      if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),101));}
      if (e.getKeyCode() == KeyEvent.VK_0)
        System.out.println("Inversion: " + inv + "\nSeventh: " + isSeventh + "\nNinth: " + isNinth);
       if (e.getKeyCode() == KeyEvent.VK_MINUS)
       {
         if (seventh == -2)
           seventh = 0;
         else
           seventh--;
           if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),105));}
       
        if (e.getKeyCode() == KeyEvent.VK_EQUALS)
       {
         if (ninth == 1)
           ninth = -1;
         else
           ninth++;
           if (recording)
      record.add(new NoteAction(true,System.currentTimeMillis(),106));}
       
        if (e.getKeyCode() == KeyEvent.VK_Z){
          recording = true; record.clear(); isSeventh = false; isNinth = false; seventh = 0; inv = 0;
        record.add(new NoteAction( System.currentTimeMillis(),key + 60)); }
        if (e.getKeyCode() == KeyEvent.VK_A){
          ArrayList<NoteAction> temp = new ArrayList<NoteAction>();
          for (int i = 0; i<record.size();i++)
            temp.add(record.get(i));
         layers.add(temp);
         record.clear();
         isNotation.add(Boolean.FALSE);
         
        }
        if (e.getKeyCode() == KeyEvent.VK_M){
         nome.update(tempo);
          if (!met){
           jew = new Thread(nome);
             met = true;jew.start();}
          else{ 
            jew.stop(); met = false;}}
        if (e.getKeyCode() == KeyEvent.VK_X)
          recording = false;
        if (e.getKeyCode() == KeyEvent.VK_P){
          PlayRecording play = new PlayRecording(channel,drum,record,set,isSeventh, inv, key,seventh,isNinth,ninth);
          new Thread(play).start();}
        if (e.getKeyCode() ==KeyEvent.VK_C){
          JFrame f = new JFrame();
         JPanel p = new JPanel();    
    go = new JButton("Play this Layer");
    go.addActionListener(this);
    go.setBounds(200,0,400,50);
    go.setActionCommand("go");
    addInLayer = new JButton("Add in a layer");
    addInLayer.addActionListener(this);
    addInLayer.setActionCommand("LayerIn");
    addInLayer.setBounds(200,50,400,50);
    playLayers = new JButton("play all layers");
    playLayers.addActionListener(this);
    playLayers.setActionCommand("LayerPlay");
    playLayers.setBounds(200,200,400,50);
    addLoop = new JButton("Make this layer a loop");
    addLoop.addActionListener(this);
    addLoop.setActionCommand("AddLoop");
    addLoop.setBounds(200,150,400,50);
      addLoopIgnore = new JButton("Make a loop waiting the initial rests");
    addLoopIgnore.addActionListener(this);
    addLoopIgnore.setActionCommand("AddLoopIgnoreInitial");
    addLoopIgnore.setBounds(200,100,400,50);
    saveSong = new JButton("Save this Song");
         saveSong.addActionListener(this);
         saveSong.setActionCommand("File");
         saveSong.setBounds(0,150,150,50);
         f.add(saveSong);
    playSave = new JButton("Play a saved song");
         playSave.addActionListener(this);
         playSave.setActionCommand("save");
        playSave.setBounds(0,200,150,50);
         f.add(playSave);
    up1 = new JButton("Tempo up 1");
    up1.addActionListener(this);
    up1.setSize(100,100);
    up1.setActionCommand("1^");
     up5 = new JButton("Tempo up 5");
    up5.addActionListener(this);
    up5.setSize(100,100);
    up5.setActionCommand("5^");
     up10 = new JButton("Tempo up 10");
    up10.addActionListener(this);
    up10.setSize(100,100);
    up10.setActionCommand("10^");
     up100 = new JButton("Tempo up 100");
    up100.addActionListener(this);
    up100.setSize(100,100);
    up100.setActionCommand("100^");
     down1 = new JButton("Tempo down 1");
    down1.addActionListener(this);
    down1.setSize(100,100);
    down1.setActionCommand("1-");
     down5 = new JButton("Tempo down 5");
    down5.addActionListener(this);
    down5.setSize(100,100);
    down5.setActionCommand("5-");
     down10 = new JButton("Tempo down 10");
    down10.addActionListener(this);
    down10.setSize(100,100);
    down10.setActionCommand("10-");
     down100 = new JButton("Tempo down 100");
    down100.addActionListener(this);
    down100.setSize(100,100);
    down100.setActionCommand("100-");
    up1.setBounds(850,0,200,50);
    up5.setBounds(850,50,200,50);
    up10.setBounds(850,100,200,50);
    up100.setBounds(850,150,200,50);
    down1.setBounds(850,200,200,50);
    down5.setBounds(850,250,200,50);
    down10.setBounds(850,300,200,50);
    down100.setBounds(850,350,200,50);
    change = new JButton("Change the instrument for the current layer(Piano is default)");
    change.addActionListener(this);
    change.setBounds(500,550,500,50);
    change.setActionCommand("change");
    whatNotes = new JTextField(currentNotes,3);
    whatNotes.setBounds(0,600,1300,60);
    whatNotes.setEditable(false);
    whatRhythm = new JTextField(3);
    whatRhythm.setBounds(600,800,1300,60);
    whatRhythm.setEditable(false);
    add = new JButton("Add Note");
    add.addActionListener(this);
    add.setBounds(0,0,150,50);
    add.setActionCommand("Add");
     clearAll = new JButton("Clear All");
    clearAll.addActionListener(this);
    clearAll.setBounds(0,100,150,50);
    clearAll.setActionCommand("All");
     clearLast = new JButton("Clear Last");
    clearLast.addActionListener(this);
    clearLast.setBounds(0,50,150,50);
    clearLast.setActionCommand("Last");
    up = new JButton("Up Octave");
    up.addActionListener(this);
    up.setBounds(0,250,150,50);
    up.setActionCommand("up");
    down = new JButton("Down Octave");
    down.addActionListener(this);
    down.setActionCommand("down");
   down.setBounds(0,300,150,50);
   quarter = new JCheckBox("Quarter Notes");
   quarter.addItemListener(this);
   quarter.setSelected(false);
   quarter.setBounds(700,0, 100,50);
   half = new JCheckBox("Half Notes");
   half.addItemListener(this);
   half.setSelected(false);
   half.setBounds(700,50, 100,50);
   whole = new JCheckBox("Whole Notes");
   whole.addItemListener(this);
   whole.setSelected(false);
   whole.setBounds(700,100, 100,50);
   eighth = new JCheckBox("Eight Notes");
   eighth.addItemListener(this);
   eighth.setSelected(false);
   eighth.setBounds(700,150, 100,50);
   sixteenth = new JCheckBox("Sixteenth Notes");
   sixteenth.addItemListener(this);
   sixteenth.setSelected(false);
   sixteenth.setBounds(700,200, 100,50);
   dotted = new JCheckBox("Dotted");
   dotted.addItemListener(this);
   dotted.setSelected(false);
   dotted.setBounds(700,250,100,50);
   customLength = new JCheckBox("Custom");
   customLength.addItemListener(this);
   customLength.setSelected(false);
   customLength.setBounds(700,300,100,50);
   cCheck = new JCheckBox("C");
  cCheck.setSelected(false);
   cCheck.addItemListener(this);
  cCheck.setBounds(150,0, 50,50);
   csCheck = new JCheckBox("C#");
  csCheck.setSelected(false);
  csCheck.setBounds(150,50, 50,50);
  csCheck.addItemListener(this);
   dCheck = new JCheckBox("D");
   dCheck.addItemListener(this);
  dCheck.setSelected(false);
  dCheck.setBounds(150,100, 50,50);
   dsCheck = new JCheckBox("D#");
  dsCheck.setSelected(false);
  dsCheck.setBounds(150,150, 50,50);
   dsCheck.addItemListener(this);
   eCheck = new JCheckBox("E");
  eCheck.setSelected(false);
  eCheck.setBounds(150,200, 50,50);
   eCheck.addItemListener(this);
   fCheck = new JCheckBox("F");
  fCheck.setSelected(false);
  fCheck.setBounds(150,250, 50,50);
   fCheck.addItemListener(this);
   fsCheck = new JCheckBox("F#");
  fsCheck.setSelected(false);
  fsCheck.setBounds(150,300, 50,50);
   fsCheck.addItemListener(this);
   gCheck = new JCheckBox("G");
  gCheck.setSelected(false);
  gCheck.setBounds(150,350, 50,50);
   gCheck.addItemListener(this);
   gsCheck = new JCheckBox("G#");
  gsCheck.setSelected(false);
  gsCheck.setBounds(150,400, 50,50);
  gsCheck.addItemListener(this);
   aCheck = new JCheckBox("A");
  aCheck.setSelected(false);
  aCheck.addItemListener(this);
  aCheck.setBounds(150,450, 50,50);
   asCheck = new JCheckBox("A#");
  asCheck.setSelected(false);
  asCheck.addItemListener(this);
  asCheck.setBounds(150,500, 50,50);
   bCheck = new JCheckBox("B");
  bCheck.setSelected(false);
   bCheck.addItemListener(this);
  bCheck.setBounds(150,550, 50,50);
  changeCustomLength = new JButton("Change custom length");
  changeCustomLength.addActionListener(this);
  changeCustomLength.setActionCommand("Change");
  changeCustomLength.setBounds(675,350,175,50);
  volume.addChangeListener(this);
  volume.setBounds(600,450,400,50);
  JTextField volumesays = new JTextField("Volume");
  volumesays.setBounds(775,425,75,15);
  volumesays.setEditable(false);
  f.add(volumesays);
  f.add(volume);
  f.add(cCheck);
  f.add(csCheck);
  f.add(dCheck);
  f.add(dsCheck);
  f.add(fCheck);
  f.add(fsCheck);
  f.add(gCheck);
  f.add(gsCheck);
  f.add(aCheck);
  f.add(asCheck);
  f.add(bCheck);
  f.add(whole);
  f.add(half);
  f.add(quarter);
  f.add(eighth);
  f.add(sixteenth);
  f.add (go);
  f.add(add);
  f.add(dotted);
  f.add(customLength);
  f.add(up1);
  f.add(up5);
  f.add(up10);
  f.add(up100);
  f.add(down1);
  f.add(down5);
  f.add(down10);
  f.add(down100);
  f.add(addInLayer);
  f.add(playLayers);
  f.add(change);
  f.add(addLoop);
  f.add(whatNotes);
    f.add(clearAll);
    f.add(addLoopIgnore);
  f.add(clearLast);
  f.add(changeCustomLength);
  go.setVisible(true);
  f.add(up);
  f.add(down);
  f.add(eCheck);
  f.getContentPane().add(p);
  f.setSize(2000,2000);
  f.setVisible(true);
  f.toFront();
         
          
        }
       
       
        if (e.getKeyCode() == KeyEvent.VK_SEMICOLON){
          key++;
          if(recording)
            record.add(new NoteAction(true, System.currentTimeMillis(), 107));}
      if (e.getKeyCode() == 222){
        key--;
        if (recording)
         record.add(new NoteAction(true, System.currentTimeMillis(), 108));
      }
        if (e.getKeyCode() == KeyEvent.VK_F)
          full = true;
  else {
    good = true;
    for (int j = 0; j<pre.length; j++)
    for (int i = 0; i<prePlaying.length; i++)
       if (pre[j] == e.getKeyCode()&&prePlaying[i])
         good = false;
     for (int i = 0; i<= pre.length-2; i++)
       pre[i] = pre[i+1];
     pre[0] = e.getKeyCode();
  }}
  else 
    if (good ){

    switch (e.getKeyCode()){
      case KeyEvent.VK_Q: if (!prePlaying[0]){channel.noteOn(60 + key, 100); 
             prePlaying[0] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 0));}
      break;
      case KeyEvent.VK_2:if (!prePlaying[1]){ channel.noteOn(61 + key, 100);
       prePlaying[1] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 1));}
      break;
      case KeyEvent.VK_W: if (!prePlaying[2]){channel.noteOn(62 + key, 100);
       prePlaying[2] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 2));}
      break;
      case KeyEvent.VK_3:if (!prePlaying[3]){ channel.noteOn(63 + key, 100);
       prePlaying[3] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 3));}
      break;
      case KeyEvent.VK_E: if (!prePlaying[4]){channel.noteOn(64 + key, 100);
       prePlaying[4] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 4));}
      break;
      case KeyEvent.VK_R:if (!prePlaying[5]){ channel.noteOn(65 + key, 100);
       prePlaying[5] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 5));}
      break;
      case KeyEvent.VK_5: if (!prePlaying[6]){channel.noteOn(66 + key, 100);
       prePlaying[6] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 6));}
      break;
      case KeyEvent.VK_T:if (!prePlaying[7]){ channel.noteOn(67 + key, 100);
       prePlaying[7] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 7));}
      break;
      case KeyEvent.VK_6:if (!prePlaying[8]){ channel.noteOn(68 + key, 100);
       prePlaying[8] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 8));}
      break;
      case KeyEvent.VK_Y: if (!prePlaying[9]){channel.noteOn(69 + key, 100);
       prePlaying[9] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 9));}
      break;
      case KeyEvent.VK_7: if (!prePlaying[10]){channel.noteOn(70 + key, 100);
       prePlaying[10] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 10));}
      break;
      case KeyEvent.VK_U:if (!prePlaying[11]){ channel.noteOn(71 + key, 100);
       prePlaying[11] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 11));}
      break;
      case KeyEvent.VK_I:if (!prePlaying[12]){ channel.noteOn(72 + key, 100);
       prePlaying[12] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 12));}
      break;
      case KeyEvent.VK_9: if (!prePlaying[13]){channel.noteOn(73 + key, 100);
       prePlaying[13] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 74));}
      break;
      case KeyEvent.VK_O: if (!prePlaying[14]){channel.noteOn(74 + key, 100);
       prePlaying[14] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 75));}
      break;
      case KeyEvent.VK_0: if (!prePlaying[15]){channel.noteOn(75 + key, 100);
       prePlaying[15] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 76));}
      break;
      case KeyEvent.VK_P: if (!prePlaying[16]){channel.noteOn(76 + key, 100);
       prePlaying[16] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 77));}
      break;
      case KeyEvent.VK_OPEN_BRACKET: if (!prePlaying[17]){channel.noteOn(77 + key, 100);
       prePlaying[17] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 78));}
      break;
      case KeyEvent.VK_EQUALS:if (!prePlaying[18]){ channel.noteOn(78 + key, 100);
       prePlaying[18] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 79));}
      break;
      case KeyEvent.VK_CLOSE_BRACKET:if (!prePlaying[19]){ channel.noteOn(79 + key, 100);
       prePlaying[19] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 80));}
      break;
      case KeyEvent.VK_BACK_SPACE: if (!prePlaying[20]){channel.noteOn(80 + key, 100);
       prePlaying[20] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 81));}
      break;
      case KeyEvent.VK_BACK_SLASH:if (!prePlaying[21]){ channel.noteOn(81 + key, 100);
             prePlaying[21] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 82));}
      break;
      case KeyEvent.VK_A: if (!prePlaying[22]){channel.noteOn(82 + key, 100);
             prePlaying[22] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 83));}
      break;
      case KeyEvent.VK_Z: if (!prePlaying[23]){channel.noteOn(83 + key, 100);
             prePlaying[23] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 84));}
      break;
      case KeyEvent.VK_X:if (!prePlaying[24]){ channel.noteOn(84 + key, 100);
             prePlaying[24] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 85));}
      break;
      case KeyEvent.VK_D:if (!prePlaying[25]){ channel.noteOn(85 + key, 100);
             prePlaying[25] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 86));}
      break;
      case KeyEvent.VK_C: if (!prePlaying[26]){channel.noteOn(86 + key, 100);
             prePlaying[26] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 87));}
      break;  
      case KeyEvent.VK_F: if (!prePlaying[27]){channel.noteOn(87 + key, 100);
             prePlaying[27] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 88));}
      break;
      case KeyEvent.VK_V: if (!prePlaying[28]){channel.noteOn(88 + key, 100);
             prePlaying[28] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 89));}
      break;
      case KeyEvent.VK_B:if (!prePlaying[29]){ channel.noteOn(89 + key, 100);
             prePlaying[29] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 90));}
      break;
      case KeyEvent.VK_H:if (!prePlaying[30]){ channel.noteOn(90 + key, 100);
             prePlaying[30] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 91));}
      break;
      case KeyEvent.VK_N: if (!prePlaying[31]){channel.noteOn(91 + key, 100);
        prePlaying[31] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 92));}
      break;
      case KeyEvent.VK_J:if (!prePlaying[32]){ channel.noteOn(92 + key, 100);
        prePlaying[32] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 93));}
      break;
      case KeyEvent.VK_M:if (!prePlaying[33]){ channel.noteOn(93 + key, 100);
        prePlaying[33] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 94));}
      break;
      case KeyEvent.VK_K:if (!prePlaying[34]){ channel.noteOn(94 + key, 100);
        prePlaying[34] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 95));}
      break;
      case KeyEvent.VK_COMMA:if (!prePlaying[35]){ channel.noteOn(95 + key, 100);
        prePlaying[35] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 96));}
      break;
      case KeyEvent.VK_PERIOD: if (!prePlaying[36]){channel.noteOn(96 + key, 100);
        prePlaying[36] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 97));}
      break;
      case KeyEvent.VK_SEMICOLON:if (!prePlaying[37]){ channel.noteOn(97 + key, 100);
        prePlaying[37] = true;
           if (recording)
             record.add(new NoteAction(true, System.currentTimeMillis(), 98));}
      break;
      case KeyEvent.VK_S: {full = false; recording = false;}
          break;  
      case KeyEvent.VK_1: if (recording) recording = false; else {recording = true; record.clear();}
          break;  
      case KeyEvent.VK_UP: key+=12;
      if (recording)
        record.add(new NoteAction(true,System.currentTimeMillis(), 99));
      break;
      case KeyEvent.VK_DOWN: key -=12;
                    if (recording)
        record.add(new NoteAction(true,System.currentTimeMillis(),100));
      break;

        
    
    }
  }
  }
  
  
  
  @Override
  public  void keyReleased(KeyEvent e) {
    final long time = System.currentTimeMillis();
    if (pre[1] == KeyEvent.VK_SHIFT || pre[1] == KeyEvent.VK_ENTER ||pre[1] == KeyEvent.VK_BACK_SLASH){
    channel.allNotesOff();
       if (recording)
            record.add(new NoteAction(false,time, -1));
    for (int i = 0; i<prePlaying.length; i++)
      prePlaying[i] = false;
    }
    else {
       if (e.getKeyCode() == KeyEvent.VK_Q) {
               prePlaying[0] = false;
          channel.noteOff(key + 60);
           if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 0));
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
           prePlaying[1] = false;
          channel.noteOff(key + 61);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 1));
          
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
           prePlaying[2] = false;
          channel.noteOff(key + 62);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(),2));
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
           prePlaying[3] = false;
          channel.noteOff(key + 63);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(),3));
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
           prePlaying[4] = false;
          channel.noteOff(key + 64);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 4));
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
           prePlaying[5] = false;
          channel.noteOff(key + 65);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 5));
        }
        if (e.getKeyCode() == KeyEvent.VK_5) {
           prePlaying[6] = false;
          channel.noteOff(key + 66);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 6));
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
           prePlaying[7] = false;
          channel.noteOff(key + 67);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 7));
        }
        if (e.getKeyCode() == KeyEvent.VK_6) {
           prePlaying[8] = false;
          channel.noteOff(key + 68);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 8));
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
           prePlaying[9] = false;
          channel.noteOff(key + 69);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 9));
        }
        if (e.getKeyCode() == KeyEvent.VK_7) {
           prePlaying[10] = false;
          channel.noteOff(key + 70);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 10));
        }
        if (e.getKeyCode() == KeyEvent.VK_U) {
           prePlaying[11] = false;
          channel.noteOff(key + 71);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 11));
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {
           prePlaying[12] = false;
          channel.noteOff(key + 72);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 12));
        }
        if (e.getKeyCode() == KeyEvent.VK_9) {
           prePlaying[13] = false;
          channel.noteOff(key + 73);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 13));
        }
        if (e.getKeyCode() == KeyEvent.VK_O) {
           prePlaying[14] = false;
          channel.noteOff(key + 74);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 14));
        }
        if (e.getKeyCode() == KeyEvent.VK_0) {
           prePlaying[15] = false;
          channel.noteOff(key + 75);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 15));
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
           prePlaying[16] = false;
          channel.noteOff(key + 76);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 16));
        }
        if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
           prePlaying[17] = false;
          channel.noteOff(key + 77);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 17));
        }
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
           prePlaying[18] = false;
          channel.noteOff(key + 78);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 18));
        }
        if (e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
           prePlaying[19] = false;
          channel.noteOff(key + 79);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 19));
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
           prePlaying[20] = false;
          channel.noteOff(key + 80);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 20));
        }
         if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
           prePlaying[21] = false;
          channel.noteOff(key + 81);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 21));
        }
          if (e.getKeyCode() == KeyEvent.VK_A) {
           prePlaying[22] = false;
          channel.noteOff(key + 82);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 22));
        }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
           prePlaying[23] = false;
          channel.noteOff(key + 83);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 23));
        }
              if (e.getKeyCode() == KeyEvent.VK_X) {
           prePlaying[24] = false;
          channel.noteOff(key + 84);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 24));
        }
                if (e.getKeyCode() == KeyEvent.VK_D) {
           prePlaying[25] = false;
          channel.noteOff(key + 85);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 25));
        }
                  if (e.getKeyCode() == KeyEvent.VK_C) {
           prePlaying[26] = false;
          channel.noteOff(key + 86);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 26));
        }
                    if (e.getKeyCode() == KeyEvent.VK_F) {
           prePlaying[27] = false;
          channel.noteOff(key + 87);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 27));
        }
                      if (e.getKeyCode() == KeyEvent.VK_V) {
           prePlaying[28] = false;
          channel.noteOff(key + 88);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 28));
        }
                        if (e.getKeyCode() == KeyEvent.VK_B) {
           prePlaying[29] = false;
          channel.noteOff(key + 89);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 29));
        }
                          if (e.getKeyCode() == KeyEvent.VK_H) {
           prePlaying[30] = false;
          channel.noteOff(key + 90);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 30));
        }
               if (e.getKeyCode() == KeyEvent.VK_N) {
           prePlaying[31] = false;
          channel.noteOff(key + 91);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 31));
        }
         if (e.getKeyCode() == KeyEvent.VK_J) {
           prePlaying[32] = false;
          channel.noteOff(key + 92);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 32));
        }
         if (e.getKeyCode() == KeyEvent.VK_M) {
           prePlaying[33] = false;
          channel.noteOff(key + 93);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 33));
        }
                 if (e.getKeyCode() == KeyEvent.VK_K) {
           prePlaying[34] = false;
          channel.noteOff(key + 94);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 34));
        }
                  if (e.getKeyCode() == KeyEvent.VK_COMMA) {
           prePlaying[35] = false;
          channel.noteOff(key + 95);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 35));
        }
                                     if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
           prePlaying[36] = false;
          channel.noteOff(key + 96);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 36));
        }
                  if (e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
           prePlaying[37] = false;
          channel.noteOff(key + 97);
          if (recording)
            record.add(new NoteAction(false, System.currentTimeMillis(), 37));
        }
    
        
        
     

    }}
  
 

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
   public static void main(String[] args) {
    new KeyListenerSynth();
    

  }
  }
