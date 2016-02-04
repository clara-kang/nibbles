import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by clara-kang on 04/02/16.
 */
public class StartKeyListener implements KeyListener {

    private Game game;

    public StartKeyListener(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            game.setInGame();
            System.out.println("pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
