import java.applet.Applet;
import java.awt.*;
import java.util.*;


/**
 * Created by clara-kang on 01/02/16.
 */
public class Game extends Applet implements Runnable {

    private ImgLoader imgLoader;
    private Head head;
    private GameKeyListener gameKeyListener;
    private StartKeyListener startKeyListener;
    private LinkedList<Node> snake;
    private boolean IN_GAME;
    private boolean WAIT_TO_START;
    private boolean GAME_OVER;

    public void init() {
        setSize(Env.WINDOW_SIZE, Env.WINDOW_SIZE);
        imgLoader = new ImgLoader(this);
        gameKeyListener = new GameKeyListener(this);
        startKeyListener = new StartKeyListener(this);

        head = new Head();
        snake = new LinkedList<>();
        snake.add(new Node(0, 0));
        snake.add(new Node(0, 30));
        snake.add(new Node(0, 60));

        setWaitToStart();
    }

    public void paint(Graphics g) {
        if (WAIT_TO_START) {
            g.drawImage(imgLoader.getBackground(), 0, 0, this);
        } else if (IN_GAME) {
            g.drawImage(imgLoader.getBackground(), 0, 0, this);

            for (Node n : snake) {
                g.drawImage(imgLoader.getBody(), n.x, n.y, this);
            }
        } else if (GAME_OVER) {
            g.drawImage(imgLoader.getBackground(), 0, 0, this);
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

        g.drawImage(bg, 0, 0, this);
    }

    public void start() {
        Thread thrd = new Thread(this);
        thrd.start();
    }

    @Override
    public void run() {
        addKeyListener(startKeyListener);
        while (WAIT_TO_START){
            try{Thread.sleep(50);} catch (InterruptedException e){}
        }
        removeKeyListener(startKeyListener);
        addKeyListener(gameKeyListener);
        while (IN_GAME) {
            head.update();
            repositionSnake();
            if(detectCollision()){
                System.out.println("gameover");
                break;
            }
            repaint();
            try {
                Thread.sleep(Env.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(GAME_OVER);
    }

    private void repositionSnake() {
        snake.addFirst(new Node(head.getX(), head.getY()));
        snake.removeLast();
    }

    class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean detectCollision() {

        Node h = snake.getFirst();
        if( h.x < Env.BORDER_LEFT ||
                h.x >= Env.BORDER_RIGHT || h.y < Env.BORDER_UP || h.y >= Env.BORDER_DOWN){
            setGameOver();
            return true;
        }

        for(int i = 2; i < snake.size(); i++){
            Node n = snake.get(i);
            if (n.x == h.x && n.y == h.y) {
                System.out.println("x: "+n.x);
                System.out.println("y: "+n.y);
                setGameOver();
                System.out.println("collide with oneself");
                return true;
            }

        }
        return false;
    }


    public void setWaitToStart() {
        this.WAIT_TO_START = true;
        this.IN_GAME = false;
        this.GAME_OVER = false;
    }

    public synchronized void setInGame() {
        this.IN_GAME = true;
        this.WAIT_TO_START = false;
        this.GAME_OVER = false;
    }

    public synchronized void setGameOver() {
        this.IN_GAME = false;
        this.WAIT_TO_START = false;
        this.GAME_OVER = true;
    }

    public Head getHead() {
        return head;
    }


}

