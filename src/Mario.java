import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mario
{
    static int y;
    static int x = 500;
    static final int marioWidth = 60;
    static final int marioHeight = 95;

    static int marioX1; //Left side of mario
    static int marioX2; //Right side of mario
    static int marioY1; //Top side of mario
    static int marioY2; //Bottom side of mario

    static int marioImageIndex; //Static integer that keeps track of the current mario sprite
    static int marioJumpTime;

    static double verticalVelocity;

    static boolean isFacingRight; //Static boolean to determine if mario is facing right
    static boolean isGrounded;

    static boolean canMoveLeft;
    static boolean canMoveRight;

    Mario()
    {
        y = 500;
        isFacingRight = true;
        isGrounded = true;
        canMoveLeft = true;
        canMoveLeft = true;
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

        if(verticalVelocity != 0)
            marioJumpTime++;
        else
        {
            marioJumpTime = 0;
            isGrounded = true;
        }


        //Continuously update the x and y position of mario and stores in integers
        marioX1 = x;
        marioX2 = x + marioWidth;
        marioY1 = y;
        marioY2 = y + marioHeight;

    }

    public void jump()
    {
        verticalVelocity = -20; // How high mario will jump;
    }

    public boolean collisionDetection(Brick brick)
    {
        //This block of code will return false if mario has not collided
        if(marioX1 > brick.xLocation + brick.wDimension) //Left side detection
        {
            return false; //If no left side detection, return false
        }
        if(marioX2 < brick.xLocation) //Right side detection
        {
            return false; //If no right side detection, return false
        }
        if(marioY1 > brick.yLocation + brick.hDimension) //Top side detection
        {
            return false; //If no top side detection, return false
        }
        if(marioY2 < brick.yLocation) //Bottom side detection
        {
            return false; //If no bottom side detection, return false
        }



        return true;
    }
}
