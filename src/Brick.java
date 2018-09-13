import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Brick
{
    //Member variables for the location and dimensions of the brick
    int xLoc;
    int yLoc;
    int wDim;
    int hDim;

    static BufferedImage brickImage; //Static because the image is shared between all of the objects

    //Brick constructor
    Brick(int x, int y, int w, int h)
    {
        xLoc = x;
        yLoc = y;
        wDim = w;
        hDim = h;
    }

    // Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject(); //Makes a new JSON file and adds the parameters of the bricks to it
        ob.add("x", xLoc);
        ob.add("y", yLoc);
        ob.add("w", wDim);
        ob.add("h", hDim);
        return ob;
    }

    //Unmarhsaling constructor.  Extracts the data from the JSON file and stores it in the member variables
    Brick(Json ob)
    {
        xLoc = (int)ob.getLong("x");
        yLoc = (int)ob.getLong("y");
        wDim = (int)ob.getLong("w");
        hDim = (int)ob.getLong("h");
    }

    //Function to load the brick image
    public BufferedImage loadBrickImage()
    {
        //Prevents the image from trying to load if it is already equal to something
        if(brickImage == null)
        {
            try
            {
                brickImage = ImageIO.read(new File("brick.jpg"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return brickImage;
    }
}
