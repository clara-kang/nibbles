import java.applet.Applet;
import java.awt.*;

/**
 * Created by clara-kang on 01/02/16.
 */
public class ImgLoader {

    private Image body;
    private Image background_start;
    private Image background_over;
    private Image background;

    MediaTracker tr;

    public ImgLoader(Applet applet){
        tr = new MediaTracker(applet);
        body = applet.getImage(applet.getCodeBase(), "coin50.png");
        background = applet.getImage(applet.getCodeBase(), "background.jpg");
        background_start = applet.getImage(applet.getCodeBase(), "background_start.jpg");
        background_over = applet.getImage(applet.getCodeBase(), "background_over.jpg");
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

    public Image getBackground_over() {
        return background_over;
    }

    public Image getBackground_start() {
        return background_start;
    }

}
