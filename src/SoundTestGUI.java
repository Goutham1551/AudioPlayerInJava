import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;



public class SoundTestGUI implements ActionListener{
    JFrame frame;
    JPanel panel,buttonpanel;
    JButton play,next,prev;
    JLabel timerlabel,title;
    AudioPlayer ap;
    Timer timer;
    ImageIcon img,background;
    JLabel imglabel,backgroundLabel;
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
        // After creating your trackSlider:
        trackSlider.setUI(new BasicSliderUI(trackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diameter = 16; // Size of the round thumb
                int x = thumbRect.x + (thumbRect.width - diameter) / 2;
                int y = thumbRect.y + (thumbRect.height - diameter) / 2;
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setColor(Color.WHITE);
                g2d.drawOval(x, y, diameter, diameter);
                g2d.dispose();
            }
        });
        volumeSlider = new JSlider(JSlider.VERTICAL,0,100,75);
        play.setBounds(350/2,700/2,100,20);
        next.setBounds(550/2,700/2,100,20);
        prev.setBounds(150/2,700/2,100,20);
        volumeSlider.setBounds(370,100,60,200);
        trackSlider.setBounds(100,385,250,30);
        buttonpanel = new JPanel();
        timer = new Timer(1000, this);
        img = new ImageIcon(songs[curr].imgpath());
        background = new ImageIcon("C:\\Users\\Goutham\\Downloads\\Free Pixel Art Forest\\Free Pixel Art Forest\\Preview\\Background.png");
        BackgroundPanel mainPanel = new BackgroundPanel(background);
        mainPanel.setPreferredSize(new Dimension(450,450));
        frame.setContentPane(mainPanel);
        //Slider styling
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setForeground(Color.blue);

        imglabel = new JLabel(img);
        backgroundLabel = new JLabel(background);
        imglabel.setBounds(120,100,200,200);
        backgroundLabel.setBounds(0, 0, 450, 450);
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
        frame.getLayeredPane().add(backgroundLabel,new Integer(Integer.MIN_VALUE));
        frame.add(volumeSlider);
        mainPanel.add(trackSlider);
        mainPanel.add(next);
        mainPanel.add(prev);
        mainPanel.add(title);
        mainPanel.add(play);
        mainPanel.add(imglabel);
        mainPanel.add(timerlabel);
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
