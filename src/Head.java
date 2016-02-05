/**
 * Created by clara-kang on 03/02/16.
 */
public class Head {

    int x;
    int y;
    int speedX;
    int speedY;

    public synchronized void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public synchronized void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public Head(){
        x = 0;
        y = 0;
        speedX = Env.TILE_LENGTH;
        speedY = 0;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public void update(){
        setX(getX() + speedX);
        setY(getY() + speedY);
    }

}
