import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.server.ExportException;
import java.util.ArrayList;

public class Mario
{
    static int y;
    static int x = 500;
    static final int marioWidth = 60;
    static final int marioHeight = 95;

    //These variables will keep track of the the positions of the corners of mario
    static int marioX1; //Left side of mario
    static int marioX2; //Right side of mario
    static int marioY1; //Top side of mario
    static int marioY2; //Bottom side of mario

    static int marioImageIndex; //Static integer that keeps track of the current mario sprite

    static int marioJumpTime;


    static double verticalVelocity;

    static boolean isGrounded;

    static boolean isFacingRight; //Static boolean to determine if mario is facing right

    static boolean canMoveRight;
    static boolean canMoveLeft;

    Mario()
    {
        y = 500; //Initializes mario to be at y = 500
        isFacingRight = true;
        isGrounded = true;
        canMoveLeft = true;
        canMoveRight = true;
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

        //Prevents mario from falling below the ground
        if(y > 500)
        {
            verticalVelocity = 0.0; //Will basically ground mario, so it will make the vertical velocity 0
            y = 500; // snap back to the ground
        }

        if(verticalVelocity == 0.0) //Mario is grounded
        {
            isGrounded = true;
            marioJumpTime = 0;
        }
        else
        {
            //This block will execute if mario is in the air
            isGrounded = false;
            marioJumpTime++; //Counts how many frames mario has been in the air
        }

        //Continuously update the x and y position of mario and stores in integers
        marioX1 = x;
        marioX2 = x + marioWidth;
        marioY1 = y;
        marioY2 = y + marioHeight;
    }

    public void jump(boolean longJump)
    {
        if(marioJumpTime < 25) //Mario can only stay in the air for so long
        {
            canMoveRight = true;
            canMoveLeft = true;
            if(isGrounded)
            {
                if(!longJump)
                    verticalVelocity = -23;
            }
            else if(longJump)
            {
                verticalVelocity = -9;
            }
        }
    }

    public boolean collisionDetection(Brick b)
    {
        //This block of code will return true if mario intersects with a brick
        if(marioX1 > b.xLocation + b.wDimension)
            return false;
        if(marioX2 < b.xLocation)
            return false;
        if(marioY1 > b.yLocation + b.hDimension)
            return false;
        if(marioY2 < b.yLocation)
            return false;

        //This block will execute if mario has collided
        if((marioX1 < b.xLocation) && ((marioX2 > b.xLocation) && (marioX2 < (b.xLocation + b.wDimension)))) //Mario collided with the left side
            getOut("l", b);
        if((marioX2 > (b.xLocation+b.wDimension) && (((marioX1 < (b.xLocation+b.wDimension)) && (marioX1 > b.xLocation))))) //Mario collided with the right side
            getOut("r", b);

        if(marioY1 <= b.yLocation) //Mario collided with the top side
            getOut("t", b);
        if(marioY2 >= (b.yLocation+b.hDimension)) //Mario collided with the bottom
            getOut("b", b);

        return true;
    }

    //If mario collides, this function will get him out depending on the side mario collided into
    private void getOut(String collisionSide, Brick b)
    {
        if(collisionSide.equals("r"))
            canMoveLeft = false;

        if(collisionSide.equals("l"))
            canMoveRight = false;

        else if(collisionSide.equals("t"))
        {
            if(!isGrounded)
            {
                y = b.yLocation - marioHeight;
                isGrounded = true;
                canMoveRight = true;
                canMoveLeft = true;
                verticalVelocity = 0;
                marioJumpTime = 0;
            }
        }
        else if (collisionSide.equals("b"))
        {
            if(!isGrounded)
            {
                y = b.yLocation + b.hDimension;
                canMoveLeft = true;
                canMoveRight = true;
            }
        }
    }
}
