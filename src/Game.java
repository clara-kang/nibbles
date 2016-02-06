import java.applet.Applet;
import java.awt.*;
import java.util.*;


/**
 * Created by clara-kang on 01/02/16.
 */
public class Game extends Applet implements Runnable {

    private ImgLoader imgLoader;
    private Head head;
    private Node next;
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
        snake.add(new Node(0, 0, null));

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

            g.drawImage(imgLoader.getBody(), next.x, next.y, this);
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
        generateNext();
        while (WAIT_TO_START) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        removeKeyListener(startKeyListener);
        addKeyListener(gameKeyListener);
        while (IN_GAME) {
            head.update();
            repositionSnake();

            if (detectCollision(snake.getFirst())) {
                System.out.println("gameover");
                break;
            }

            if (detectReachNext()) {
                addNext();
                generateNext();
            }

            repaint();
            try {
                Thread.sleep(Env.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (GAME_OVER) ;
    }

    private void repositionSnake() {
        snake.addFirst(new Node(head.getX(), head.getY(), head.getDirection()));
        snake.removeLast();
    }

    private void generateNext() {
        do {
            Random r = new Random();
            int x = r.nextInt(Env.NUM_OF_TILES) * Env.TILE_LENGTH;
            int y = r.nextInt(Env.NUM_OF_TILES) * Env.TILE_LENGTH;
            next = new Node(x,y, null);
            System.out.println("next.x " + next.x);
            System.out.println("next.y " + next.y);
            System.out.println("generate");
        } while (detectOverlap(next));

    }

    private void addNext() {
        next.direction = head.getDirection();
        snake.addFirst(next);

        head.setX(next.x);
        head.setY(next.y);
    }

    private boolean detectReachNext() {
        Node h = snake.getFirst();

        if ((h.direction == Direction.UP && h.x == next.x && h.y == next.y + Env.TILE_LENGTH)
                || (h.direction == Direction.DOWN && h.x == next.x && h.y == next.y - Env.TILE_LENGTH)
                || (h.direction == Direction.RIGHT && h.y == next.y && h.x == next.x - Env.TILE_LENGTH)
                || (h.direction == Direction.LEFT && h.y == next.y && h.x == next.x + Env.TILE_LENGTH)) {
            System.out.println("reached next");
            return true;
        }
        return false;
    }

    private boolean detectCollision(Node h) {
        if (h.x < Env.BORDER_LEFT ||
                h.x >= Env.BORDER_RIGHT || h.x < Env.BORDER_UP || h.y >= Env.BORDER_DOWN) {
            setGameOver();
            return true;
        }

        for (int i = 1; i < snake.size(); i++) {
            Node n = snake.get(i);
            if (n.x == h.x && n.y == h.y) {
                setGameOver();
                System.out.println("collide with oneself");
                return true;
            }
        }
        return false;
    }

    private boolean detectOverlap(Node h) {
        for (Node n : snake){
            if (h.x == n.x && h.y == n.y) {
                return true;
            }
        }
        if((head.getDirection() == Direction.DOWN || head.getDirection() == Direction.UP )
                && h.x == head.getX()){
            return true;
        }
        if((head.getDirection() == Direction.LEFT || head.getDirection() == Direction.RIGHT )
        && h.y == head.getY()){
            return true;
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

    class Node {
        private int x;
        private int y;
        private Direction direction;

        public Node(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
}

