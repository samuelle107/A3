import javax.swing.JPanel;
import java.awt.*;

class View extends JPanel //The view class shows what the user will see.
{
    //Member variables
    Model model;
    Mario mario;

    Image[] marioImages;
    Image[] reversedMarioImages;

    View(Controller c, Model m, Mario ma)
    {
        model = m;
        mario = ma;
        c.setView(this);

        marioImages = mario.loadMarioImages("mario");
        reversedMarioImages = mario.loadMarioImages("rmario");
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(128, 255, 255)); //Sets the color of the background to be cyan.
        g.fillRect(0,0,this.getWidth(), this.getHeight()); //Fills the specified rectangle

        //This for loop will loop through all of the brick objects
        for(int i = 0; i < model.bricks.size(); i++)
        {
            Brick b = model.bricks.get(i); //Since we have an array of bricks, we want to change the index every loop
            b.loadBrickImage(); //Calls the loadBrickImage function to load the image in the brick class.  This will store the image into a static variable.
            g.drawImage(Brick.brickImage, b.xLocation - model.hCamPos, b.yLocation, b.wDimension, b.hDimension, null); //Draw the ith brick.  Each brick has difference properties, so we use the object, b.
        }

        g.setColor(Color.gray); //Set the color of the ground to be gray
        g.drawLine(0,596,2000,596); //Draws the ground

        //Depending on the direction of mario,
        if(Mario.isFacingRight)
            g.drawImage(marioImages[Mario.marioImageIndex],500,mario.y,null);
        else
            g.drawImage(reversedMarioImages[Mario.marioImageIndex],500,mario.y,null);
    }
}
