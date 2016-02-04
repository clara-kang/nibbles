import java.applet.Applet;
import java.awt.*;
import java.util.*;


/**
 * Created by clara-kang on 01/02/16.
 */
public class Game extends Applet implements Runnable{

    private ImgLoader imgLoader;
    private Head head;
    private GameKeyListener gameKeyListener;
    private StartKeyListener startKeyListener;
    private LinkedList<Node> snake;
    private boolean INGAME;
    private boolean WAITTOSTART;

    public void init() {
        setSize(Env.WINDOW_SIZE, Env.WINDOW_SIZE);
        imgLoader = new ImgLoader(this);
        gameKeyListener = new GameKeyListener(this);
        startKeyListener = new StartKeyListener(this);

        head = new Head();
        snake = new LinkedList<>();
        snake.add(new Node(0,0));
        snake.add(new Node(0,0));
        snake.add(new Node(0,0));

        setWaitToStart();
    }

    public void paint(Graphics g) {
        if(WAITTOSTART){
            g.drawImage(imgLoader.getBackground(), 0, 0, this);
        }
        else if(INGAME) {
            g.drawImage(imgLoader.getBackground(), 0, 0, this);

            for (Node n : snake) {
                g.drawImage(imgLoader.getBody(), n.x, n.y, this);
            }
        }
    }

    @Override
    public void update(Graphics g) {
        Image bg = createImage(this.getWidth(), this.getHeight());
        Graphics bg_g = bg.getGraphics();
        bg_g.setColor(getBackground());
        bg_g.fillRect(0, 0, this.getWidth(), this.getHeight());
        bg_g.setColor(getForeground());
        paint(bg_g);

        g.drawImage(bg,0,0,this);
    }

    public void start() {
        Thread thrd = new Thread(this);
        thrd.start();
    }

    @Override
    public void run() {
        addKeyListener(startKeyListener);
        while (WAITTOSTART);
        removeKeyListener(startKeyListener);
        addKeyListener(gameKeyListener);
        while (INGAME) {
            head.update();
            repositionSnake();
            repaint();
            try {
                Thread.sleep(Env.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void repositionSnake() {
        snake.addFirst(new Node(head.getX(),head.getY()));
        snake.removeLast();
    }

    class Node{
        int x;
        int y;
        public Node(int x,int y){
            this.x = x;
            this.y = y;
        }
    }


    public void setWaitToStart() {
        this.WAITTOSTART = true;
        this.INGAME = false;
    }

    public void setInGame() {
        this.INGAME = true;
        this.WAITTOSTART = false;
    }

    public Head getHead() {
        return head;
    }


}

