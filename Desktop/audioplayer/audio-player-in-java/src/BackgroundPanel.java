import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
    private Image backgroundImage;
    public BackgroundPanel(ImageIcon icon){
        this.backgroundImage = icon.getImage();
        setLayout(null);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(),getHeight(),this);
    }
}