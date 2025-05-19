import javax.sound.sampled.*;
import javax.swing.JLabel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class AudioPlayer {
    private Clip clip;
    public void loadSound(String filepath){
        try{
            File file = new File(filepath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
        }catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
            e.printStackTrace();
        }
    }
    public void play(){
        if(clip!=null){
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void stop(){
        if(clip!=null)
            clip.stop();
    }
    public String getDetails(){
        if (clip != null) {
            long currentTime = clip.getMicrosecondPosition() / 1_000_000; // Convert to seconds
            long totalTime = clip.getMicrosecondLength() / 1_000_000; // Convert to seconds
            if(totalTime==0)
                return "total length is 0";
            long currentseconds=currentTime;
            long currentminutes=currentTime/60;
            currentseconds%=60;
            long totalseconds=totalTime;
            long totalminutes=totalseconds/60;
            totalseconds%=60;
            return String.format("%02d:%02d/%02d:%02d", currentminutes,currentseconds,totalminutes,totalseconds);
        }
        else
            return "clip is not loaded";
    }
    public void playorpause(){
        if(clip!=null){
            if(clip.isRunning()){
                clip.stop();
            }
            else{
                clip.start();
            }
        }
    }
    public boolean isComplele(){
        long currentTime = clip.getMicrosecondPosition();
        long totalTime = clip.getMicrosecondLength();
        if(totalTime==currentTime)
            return true;
        else{
            return false;
        }
        
    }
    public void replay(){
        clip.setFramePosition(0);
        clip.start();
    }
    public int getCurrentTime() {
    if (clip != null) {
        return (int) (clip.getMicrosecondPosition() / 1_000_000);
    }
    return 0;
}
    public void setVolume(int volume) {
        if(clip!=null){
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float max = gainControl.getMaximum();
            float min = gainControl.getMinimum();
            float gain = (float) (min + (max - min) * (Math.log10(volume) / Math.log10(100)));
            gainControl.setValue(gain);
        }
    }
    public Clip getClip(){
        return clip;
    }
}

