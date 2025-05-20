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
        ap = new AudioPlayer();
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
        play = new JButton("play/pause");
        trackSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
        // After creating your trackSlider:
        trackSlider.setUI(new BasicSliderUI(trackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diameter = 12; // Size of the round thumb
                int x = thumbRect.x + (thumbRect.width - diameter) / 2;
                int y = thumbRect.y + (thumbRect.height - diameter) / 2;
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setColor(Color.WHITE);
                g2d.drawOval(x, y, diameter, diameter);
                g2d.dispose();
            }
            @Override
        public void paintFocus(Graphics g) {
            // Do nothing, removes the focus border
        }
        });
        volumeSlider = new JSlider(JSlider.VERTICAL,0,100,75);
        volumeSlider.setUI(new BasicSliderUI(trackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diameter = 12; // Size of the round thumb
                int x = thumbRect.x + (thumbRect.width - diameter) / 2;
                int y = thumbRect.y + (thumbRect.height - diameter) / 2;
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setColor(Color.WHITE);
                g2d.drawOval(x, y, diameter, diameter);
                g2d.dispose();
            }
            @Override
            public void paintFocus(Graphics g) {
                // Do nothing, removes the focus border
            }
        });
        play.setBounds(350/2,700/2,100,20);
        next.setBounds(550/2,700/2,100,20);
        prev.setBounds(150/2,700/2,100,20);
        volumeSlider.setBounds(370,100,60,200);
        trackSlider.setBounds(100,385,250,30);
        buttonpanel = new JPanel();
        timer = new Timer(1000, this);
        img = new ImageIcon(songs[curr].imgpath());
        Image imgscale = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        background = new ImageIcon("C:\\Users\\Goutham\\Downloads\\ChatGPT Image May 19, 2025, 07_38_53 PM.png");
        BackgroundPanel mainPanel = new BackgroundPanel(background);
        mainPanel.setPreferredSize(new Dimension(450,450));
        frame.setContentPane(mainPanel);
        //Slider styling
        volumeSlider.setOpaque(false);
        volumeSlider.setPaintTicks(false);
        volumeSlider.setPaintLabels(false);
        volumeSlider.setBackground(new Color(0,0,0,0)); // Transparent background
        volumeSlider.setForeground(new Color(60, 60, 60)); // Sleek dark gray
        InputMap sliderInputMap = volumeSlider.getInputMap(JComponent.WHEN_FOCUSED);
        sliderInputMap.put(KeyStroke.getKeyStroke("LEFT"), "none");
        sliderInputMap.put(KeyStroke.getKeyStroke("RIGHT"), "none");

        imglabel = new JLabel(new ImageIcon(imgscale));
        backgroundLabel = new JLabel(background);
        imglabel.setBounds(120,100,200,200);
        backgroundLabel.setBounds(0, 0, 450, 450);
        timerlabel = new JLabel("0.0");
        timerlabel.setFont(new Font("Open Sans",Font.BOLD,18));
        timerlabel.setBounds(150, 290, 150, 100);
        title = new JLabel(songs[curr].title());
        title.setFont(new Font("Monospaced",Font.BOLD,20));
        title.setBounds(120,10,200,100);

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
        volumeSlider.addChangeListener(e -> {
            if (ap != null) {
                ap.setVolume(volumeSlider.getValue());
            }
            // Force repaint of parent to avoid ghosting/artifacts
            volumeSlider.getParent().repaint();
        });
        prev.addActionListener(this);
        timer.start();
        InputMap ipmap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();
        ipmap.put(KeyStroke.getKeyStroke("SPACE"), "playpause");
        actionMap.put("playpause", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                play.doClick();
            }
        });
        ipmap.put(KeyStroke.getKeyStroke("RIGHT"), "next");
        actionMap.put("next", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                next.doClick();
            }
        });
        ipmap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        actionMap.put("left", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                prev.doClick();
            }
        });
        ipmap.put(KeyStroke.getKeyStroke("UP"), "volumeUp");
        actionMap.put("volumeUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = volumeSlider.getValue();
                if (value < volumeSlider.getMaximum()) {
                    volumeSlider.setValue(value + 5); // Increase by 5, adjust as needed
                }
            }
        });
        ipmap.put(KeyStroke.getKeyStroke("DOWN"), "volumeDown");
        actionMap.put("volumeDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = volumeSlider.getValue();
                if (value > volumeSlider.getMinimum()) {
                    volumeSlider.setValue(value - 5); // Decrease by 5, adjust as needed
                }
            }
        });
        ap.loadSound(songs[curr].audiopath());
        ap.setVolume(volumeSlider.getValue());
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == play){
            ap.playorpause();
        }
        if(e.getSource() == next){
            boolean wasRunning = ap.getClip().isRunning();
            if(ap!=null){
                ap.stop();
            }
            curr = (curr+1)%songs.length;
            updatesongDisplay();
            ap.loadSound(songs[curr].audiopath());
            ap.setVolume(volumeSlider.getValue());
            if(wasRunning) ap.getClip().start();
            else ap.getClip().stop();
        }
        if(e.getSource() == prev){
            if(ap!=null){
                boolean wasRunning = ap.getClip().isRunning();
                if(ap.getCurrentTime()>1){
                    ap.replay();
                }else{  
                    ap.stop();
                    curr = (curr - 1 + songs.length) % songs.length;
                    ap.loadSound(songs[curr].audiopath());
                    ap.setVolume(volumeSlider.getValue());
                    updatesongDisplay();
                    if(wasRunning) ap.getClip().start();
                    else ap.getClip().stop();
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
