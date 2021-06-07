import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @author sk.z
 */
public class DrawDemo extends JApplet {

    private static final Random random = new Random();

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Draw Demo");

        JApplet applet = new DrawDemo();
        jFrame.getContentPane().add("Center", applet);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        jFrame.setSize(new Dimension(800, 600));
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public void init() {
        setBackground(Color.white);
        setForeground(Color.black);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Dimension dimension = getSize();
        g2.setPaint(Color.blue);
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                g2.setPaint(randomColor());
                drawPoint(g2, x, y);
            }
        }
    }

    private void drawPoint(Graphics2D graphics, int x, int y) {
        graphics.drawLine(x, y, x, y + 1);
    }

    private Color randomColor() {
        int red = 37 + random.nextInt(7);
        int green = 100 + random.nextInt(7);
        int blue = 32 + random.nextInt(7);
        return new Color(red, green, blue);
    }
}
