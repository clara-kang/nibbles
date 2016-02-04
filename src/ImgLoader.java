import java.applet.Applet;
import java.awt.*;

/**
 * Created by clara-kang on 01/02/16.
 */
public class ImgLoader {

    private Image body;
    private Image apple;



    private Image background;

    MediaTracker tr;

    public ImgLoader(Applet applet){
        tr = new MediaTracker(applet);
        body = applet.getImage(applet.getCodeBase(), "coin50.png");
        background = applet.getImage(applet.getCodeBase(), "background.jpg");
        tr.addImage(body,0);
        try{
            tr.waitForAll();
        }catch (InterruptedException e){e.printStackTrace();}
    }

    public Image getBackground() {
        return background;
    }
    public Image getBody() {
        return body;
    }

    public Image getApple() {
        return apple;
    }



}
