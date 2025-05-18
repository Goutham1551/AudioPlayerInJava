import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoundTestGUI implements ActionListener{
    JFrame frame;
    JPanel panel,buttonpanel;
    JButton play,next,prev;
    JLabel timerlabel,title;
    AudioPlayer ap;
    Timer timer;
    ImageIcon img;
    JLabel imglabel;
    JSlider volumeSlider,trackSlider;
    Song songs[] = new Song[3];
    int curr = 0;

    SoundTestGUI(){
        songs[0] = new Song("retro game song",
            "C:\\Users\\Goutham\\Downloads\\gameaudio.wav",
            "C:\\Users\\Goutham\\Downloads\\credit_for_audio.png");
        songs[1] = new Song("unnatuindi gundey",
            "C:\\Users\\Goutham\\Downloads\\unnatundigundey.wav",
            "C:\\Users\\Goutham\\Downloads\\Ninnu-Kori-Telugu-2017-20220323073319-500x500.png");
        songs[2] = new Song("Varinche prema",
        "C:\\Users\\Goutham\\Downloads\\VarinchePrema.wav",
        "C:\\Users\\Goutham\\Downloads\\Malli-Malli-Idi-Rani-Roju-Telugu-2014-500x500.png");
        
        
        frame= new JFrame("Audio Player");
        next = new JButton("next");
        prev = new JButton("prev");
        play = new JButton("paly/pause");
        trackSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
        volumeSlider = new JSlider(JSlider.VERTICAL,0,100,75);
        play.setBounds(350/2,700/2,100,20);
        next.setBounds(550/2,700/2,100,20);
        prev.setBounds(150/2,700/2,100,20);
        volumeSlider.setBounds(370,100,60,200);
        trackSlider.setBounds(100,385,250,30);
        buttonpanel = new JPanel();
        timer = new Timer(1000, this);
        img = new ImageIcon(songs[curr].imgpath());
        
        //Slider styling
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setForeground(Color.blue);

        imglabel = new JLabel(img);
        imglabel.setBounds(120,100,200,200);
        timerlabel = new JLabel("0.0");
        timerlabel.setBounds(150, 290, 150, 100);
        title = new JLabel(songs[curr].title());
        title.setBounds(145,10,150,100);

        title.setHorizontalAlignment(SwingConstants.CENTER);
        play.setHorizontalAlignment(SwingConstants.CENTER);
        imglabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerlabel.setHorizontalAlignment(SwingConstants.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.add(volumeSlider);
        frame.add(trackSlider);
        frame.add(next);
        frame.add(prev);
        frame.add(title);
        frame.add(play);
        frame.add(imglabel);
        frame.add(timerlabel);
        frame.getContentPane().setBackground(Color.CYAN);
        play.addActionListener(this);
        next.addActionListener(this);
        volumeSlider.addChangeListener(e -> {
            if(ap!=null){
                ap.setVolume(volumeSlider.getValue());
            }
        });
        trackSlider.addChangeListener(e->{
            if(ap!=null && trackSlider.getValueIsAdjusting()){
                int value = trackSlider.getValue();
                long totalMicroseconds = ap.getClip().getMicrosecondLength();
                long toSetPosition = (long) (totalMicroseconds * (value/100.0));
                ap.getClip().setMicrosecondPosition(toSetPosition);
            }
        });
        prev.addActionListener(this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == play){
            if(ap==null){
                ap = new AudioPlayer();
                ap.loadSound(songs[curr].audiopath());
                ap.play();
            }
            else{
                ap.playorpause();
            }
        }
        if(e.getSource() == next){
            if(ap!=null){
                ap.stop();
                ap = null;
            }
            curr = (curr+1)%songs.length;
            updatesongDisplay();
            play.doClick();
        }
        if(e.getSource() == prev){
            if(ap!=null){
                if(ap.getCurrentTime()>1){
                    ap.replay();
                }else{  
                    ap.stop();
                    ap = null;
                    curr = (curr - 1 + songs.length) % songs.length;
                    updatesongDisplay();
                    play.doClick();
                }
            }
            
        }
        if(e.getSource() == timer){
            if(ap!=null){
                timerlabel.setText(ap.getDetails());
                // Update trackSlider position based on current playback
                long current = ap.getClip().getMicrosecondPosition();
                long total = ap.getClip().getMicrosecondLength();
                int percent = (total > 0) ? (int)((current * 100) / total) : 0;
                trackSlider.setValue(percent);
            }else{
                timerlabel.setText("Welcome click play to start");
                trackSlider.setValue(0);
            }
        }
    }
    void updatesongDisplay(){
        ImageIcon originalIcon = new ImageIcon(songs[curr].imgpath());
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imglabel.setIcon(scaledIcon);
        imglabel.repaint();
        title.setText(songs[curr].title());
    }
    public static void main(String[] args) {
        new SoundTestGUI();
    }
}
