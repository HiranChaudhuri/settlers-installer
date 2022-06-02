/*
 */
package settlers.installer.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * This loadingincator can be used as a glasspane to tell the user something
 * is going on.
 * 
 * @author hiran
 */
public class LoadingIndicator2 extends JComponent {

    /**
     * Creates a new LoadingIndicator2.
     */
    public LoadingIndicator2() {
        setForeground(java.awt.Color.orange);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new java.awt.Color(0f, 0f, 0f, 0.5f));
        g.fillRect(0, 0, getWidth(), getHeight());

        String s = "Scanning GitHub...";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 24));
        Rectangle2D r = g.getFontMetrics().getStringBounds(s, g);
        int x = (int)((getWidth()-r.getWidth())/2);
        int y = (int)((getHeight()-r.getHeight())/2);
        g.setColor(getForeground());
        g.drawString(s, x, y);
    }
    
}
