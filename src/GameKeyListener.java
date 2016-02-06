import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by clara-kang on 03/02/16.
 */
public class GameKeyListener implements KeyListener {


    private Game game;

    public GameKeyListener(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                game.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                game.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                game.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                game.setDirection(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
