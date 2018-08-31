import java.awt.event.*;

class Controller implements MouseListener, KeyListener
{
    //Member variables
    View view;
    Model model;
    Mario mario;

    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;
    boolean keySpace;

    int preXLocation; //X coordinate on mouse press
    int preYLocation; //Y coordinate on mouse press
    int postXLocation; //X coordinate on mouse release
    int postYLocation; //Y coordinate on mouse release

    static int movementSpeed = 15;

    Controller(Model m, Mario ma) //Constructor
    {
        model = m; //I pass in the model object to this constructor and call it "m".  It is then assigned to model in this class.
        mario = ma;
    }

    void setView(View v)
    {
        view = v;
    }

    public void keyPressed(KeyEvent e)
    {
         //Movement speed of the camera

        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
            {
                keyRight = true;
                Mario.isFacingRight = true; //When the player moves right, Mario is facing right
                //if(Mario.canMoveRight)
                    model.hCamPos += movementSpeed; //Moves x pixels to the right.

                //This will cycle through an integer from 0 to 4.  Changes the mario image
                if(Mario.marioImageIndex != 4)
                    Mario.marioImageIndex++;
                else
                    Mario.marioImageIndex = 0;
            }
            break;
            case KeyEvent.VK_LEFT:
            {
                keyLeft = true;
                Mario.isFacingRight = false;
                if(Mario.canMoveLeft)
                    model.hCamPos -= movementSpeed; //Moves x pixels to the left.
                if(Mario.marioImageIndex != 4)
                    Mario.marioImageIndex++;
                else
                    Mario.marioImageIndex = 0;
            }
            break;
            case KeyEvent.VK_UP: keyUp = true; break;
            case KeyEvent.VK_DOWN: keyDown = true; break;
            case KeyEvent.VK_L: //When the user presses L, unmarshal the JSON
            {
                Json j = Json.load("maps.json"); //
                model.unMarshal(j);
                System.out.println("You loaded the map");
            }
            break;
            case KeyEvent.VK_S: //When the user presses S, marshal the JSON
            {
                model.marshal().save("maps.json");
                System.out.println("You saved the map");
            }
            break;
            case KeyEvent.VK_SPACE:
            {
                keySpace = true;
                if(Mario.marioJumpTime < 20 && Mario.isGrounded)
                    mario.jump();
            }
            break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = false;  break;
            case KeyEvent.VK_LEFT: keyLeft = false;   break;
            case KeyEvent.VK_UP: keyUp = false; break;
            case KeyEvent.VK_DOWN: keyDown = false; break;
            case KeyEvent.VK_SPACE: keySpace = false; Mario.isGrounded = false; break;
        }
    }

    public void keyTyped(KeyEvent e)
    {

    }

    void update() //This function updates every few ms and updates the model's location based on the keypress
    {
        if(keyRight) model.dest_x++;
        if(keyLeft) model.dest_x--;
        if(keyDown) model.dest_y++;
        if(keyUp) model.dest_y--;
    }

    public void mousePressed(MouseEvent e)
    {
        //Getting the preCoordinates of the brick
        preXLocation = e.getX();
        preYLocation = e.getY();
    }

    public void mouseReleased(MouseEvent e)
    {
        int xFinal; //The final value for the x coordinate
        int yFinal; //The final value for the y coordinate
        int w; //Magnitude of the difference of the pre and post x coordinates
        int h; //Magnitude of the difference of the pre and post y coordinates

        //Getting the postCoordinates of the brick
        postXLocation = e.getX();
        postYLocation = e.getY();

        //Calculating the absolute width and height
        w = Math.abs(postXLocation - preXLocation);
        h = Math.abs(postYLocation - preYLocation);

        //Determines the final location of the brick based on the way the box is drawn.  This is because the image is always drawn in the left corner.
        if(postYLocation > preYLocation)
        {
            if(postXLocation > preXLocation)
            {
                xFinal = preXLocation;
                yFinal = preYLocation;
            }
            else
            {
                xFinal = preXLocation - w;
                yFinal = postYLocation - h;
            }
        }
        else
        {
            if(postXLocation > preXLocation)
            {
                xFinal = postXLocation - w;
                yFinal = preYLocation - h;
            }
            else
            {
                xFinal = postXLocation;
                yFinal = postYLocation;
            }
        }

        model.addTube(xFinal + model.hCamPos, yFinal,w,h); //Adds the tube to the array
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseClicked(MouseEvent e)
    {

    }
}
