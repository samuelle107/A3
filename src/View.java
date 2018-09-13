import javax.swing.JPanel;
import java.awt.*;

class View extends JPanel //The view class shows what the user will see.
{
    //Member variables
    Model model;
    Mario mario;

    View(Controller c, Model m, Mario ma)
    {
        model = m;
        mario = ma;
        c.setView(this);
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(new Background().loadImage(),-(int)(mario.x * 0.1f),0,null);

        for(int i = 0; i < model.bricks.size(); i++)
        {
            Brick b = model.bricks.get(i); //Since we have an array of bricks, we want to change the index every loop
            g.drawImage(b.loadBrickImage(), b.xLoc - (mario.x - 500), b.yLoc, b.wDim, b.hDim, null); //Draw the ith brick.  Each brick has difference properties, so we use the object, b.
        }

        g.setColor(Color.gray); //Set the color of the ground to be gray
        g.drawLine(0,596,2000,596); //Draws the ground

        //Depending on the direction of mario,
        if(mario.isFacingRight)
            g.drawImage(Mario.marioImages[mario.marioImageIndex],500, mario.y,null);
        else
            g.drawImage(Mario.reversedMarioImages[mario.marioImageIndex],500, mario.y,null);
    }
}
