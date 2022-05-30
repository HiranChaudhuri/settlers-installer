/*
 */
package settlers.installer.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author hiran
 */
public class LoadingIndicator2 extends JComponent {

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
