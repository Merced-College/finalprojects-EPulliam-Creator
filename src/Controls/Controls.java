import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel; // Or whatever component you are using

public class Controls extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Controls(Player player) {
        // This logic must be inside a constructor or method
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.moveRight();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    player.jump();
                }
            }
        });
    }
}