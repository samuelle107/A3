import java.util.ArrayList;
class Model
{
    Mario mario;

    static int hCamPos;

    ArrayList<Brick> bricks;

    Model(Mario m)
    {
        mario = m;
        bricks = new ArrayList<Brick>();
    }

    public void addTube(int x, int y, int w, int h)
    {
        //Create a new Brick object called b and adds it to the bricks array
        Brick b = new Brick(x, y, w, h);
        bricks.add(b);
    }

    public void update()
    {
        mario.update();
        Mario.x = hCamPos +500; //Keeps track of the x position of Mario.  Adding 500 because Mario starts at x = 500;

        if(bricks.size() != 0)
            for(int i = 0; i < bricks.size(); i++)
            {
                if(mario.collisionDetection(bricks.get(i))) //This will turn off left movement for all the bricks
                {

                }

            }
    }

    //Marshall Method
    Json marshal()
    {
        Json ob = Json.newObject();
        Json brickList = Json.newList();
        ob.add("bricks", brickList);
        for(int i = 0; i < bricks.size(); i++)
            brickList.add(bricks.get(i).marshal());
        return ob;
    }

    //Unmarshal method
    void unMarshal (Json ob)
    {
        bricks.clear();
        Json jsonList = ob.get("bricks");
        for (int i = 0; i < jsonList.size(); i++)
            bricks.add(new Brick(jsonList.get(i)));
    }
}
