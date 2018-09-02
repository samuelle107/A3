import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Brick
{
    //Member variables for the location and dimensions of the brick
    int xLocation;
    int yLocation;
    int wDimension;
    int hDimension;

    static BufferedImage brickImage; //Static because the image is shared between all of the objects

    //Brick constructor
    Brick(int x, int y, int w, int h)
    {
        xLocation = x;
        yLocation = y;
        wDimension = w;
        hDimension = h;
    }

    // Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject(); //Makes a new JSON file and adds the parameters of the bricks to it
        ob.add("x", xLocation);
        ob.add("y", yLocation);
        ob.add("w", wDimension);
        ob.add("h", hDimension);
        return ob;
    }

    //Unmarhsaling constructor.  Extracts the data from the JSON file and stores it in the member variables
    Brick(Json ob)
    {
        xLocation =  (int)ob.getLong("x");
        yLocation =  (int)ob.getLong("y");
        wDimension = (int)ob.getLong("w");
        hDimension = (int)ob.getLong("h");
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
