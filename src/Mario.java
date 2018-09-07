import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mario
{
    int x; //X position of mario
    int y; //Y position of mario
    private int prevX;
    private int prevY;

    private final int marioWidth = 60;
    private final int marioHeight = 95;

    final int marioMovementSpeed = 10;
    int marioImageIndex; //Integer that keeps track of the current mario sprite
    int marioJumpTime; //Contains how many frames it has been since Mario has been on solid ground
    private double verticalVelocity;
    boolean isGrounded;
    boolean isFacingRight; //boolean to determine if mario is facing right

    Mario()
    {
        x = 500;
        y = 500; //Initializes mario to be at y = 500
        isFacingRight = true;
        isGrounded = true;
    }

    Image[] loadMarioImages(String fileName) //Loads the mario images into a new image array and returns it
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

    void update()
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
    }

    void jump(boolean longJump)
    {
        if(marioJumpTime < 25) //Mario can only stay in the air for so long
        {
            if(isGrounded)
            {
                if(!longJump)
                    verticalVelocity = -23;
            }
            else if(longJump)
                verticalVelocity = -9;
        }
    }

    void locationOfMarioPast()
    {
        prevX = x;
        prevY = y;
    }

    void marioImageCycle()
    {
        if(marioImageIndex != 4)
            marioImageIndex++;
        else
            marioImageIndex = 0;
    }

    boolean collisionDetection(Brick b)
    {
        //This block of code will return true if and only i mario intersects with a brick
        if((x > (b.xLoc + b.wDim)))
            return false;
        if(x + marioWidth < b.xLoc)
            return false;
        if(y > b.yLoc + b.hDim)
            return false;
        if(y + marioHeight < b.yLoc)
            return false;
        collisionHandler(b);
        return true;
    }

    private void collisionHandler(Brick b)
    {
        if(x + marioWidth >= b.xLoc && prevX < b.xLoc) //Hits Right wall
            x = b.xLoc - marioWidth - 1;
        else if(x <= b.xLoc + b.wDim && prevX > b.xLoc + b.wDim) //Hits Left wall
            x = b.xLoc + b.wDim + 1;
        else if(y + marioHeight > b.yLoc && prevY < b.yLoc) //Hits top
        {
            y = b.yLoc - marioHeight;
            isGrounded = true;
            verticalVelocity = 0.0;
            marioJumpTime = 0;
        }
        else if ( y < b.yLoc + b.hDim && prevY > b.yLoc + b.hDim) //Hits bottom
            y = b.yLoc + b.hDim +1;
    }
}
