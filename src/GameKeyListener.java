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
        Head head = game.getHead();
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                head.setSpeedX(0);
                head.setSpeedY(-Env.TILE_LENGTH);
                break;
            case KeyEvent.VK_DOWN:
                head.setSpeedX(0);
                head.setSpeedY(Env.TILE_LENGTH);
                break;
            case KeyEvent.VK_LEFT:
                head.setSpeedY(0);
                head.setSpeedX(-Env.TILE_LENGTH);
                break;
            case KeyEvent.VK_RIGHT:
                head.setSpeedY(0);
                head.setSpeedX(Env.TILE_LENGTH);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
