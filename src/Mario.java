import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mario
{
    int y;

    static int marioImageIndex; //Static integer that keeps track of the current mario sprite

    double verticalVelocity;

    static boolean isFacingRight; //Static boolean to determine if mario is facing right

    Mario()
    {
        y = 500;
        isFacingRight = true;
    }

    public Image[] loadMarioImages(String fileName) //Loads the mario images into a new image array and returns it
    {
        Image[] marioImages = new Image[5];

        for(int i = 0; i < 5; i++)
        {
            try
            {
                marioImages[i] = ImageIO.read(new File(fileName + Integer.toString(i+1) + ".png"));
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return marioImages;
    }

    public void update()
    {
        verticalVelocity += 1.2;
        y += verticalVelocity;

        if(y > 500)
        {
            verticalVelocity = 0.0;
            y = 500;
        }
    }

    public void jump()
    {
        verticalVelocity = -20; // How high mario will jump;
    }


}
