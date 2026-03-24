package Main;

import entity.Player;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //JPanel is a container used for drawing graphics and placing components inside a window(JFrame)
    //extends JPanel, which means GamePanel inherits the behavior of JPanel
    //allows to draw graphics and place the panel inside the window

    //implements Runnable means this class can be executed by a Thread
    //requires the run() method which contains the game loop

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tiles default title of character, NPCs, map (standart -> 16)
    final int scale = 3;//it looks smaller, so 16x3 = 48

    public final int tileSize = originalTileSize * scale; //48x48
    //resolution of the screen
    final int maxScreenCol = 16;//18; the best is 37; default 16
    final int maxScreenRow = 12; //16 x 12 screen; default 12; the best is 20
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels


    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    //used for:
    //1. Detecting when the player presses a key
    //2. Detecting when the player releases a key
    //3. Updating the player's movement or actions accordingly

    Thread gameThread;
    //variable
    //Thread = a class used to run code in parallel with the main program(code for the game)
    //in this case it runs the game loop (run method) so the game can update and draw frames continuously

    Player player = new Player(this, keyH);


    //The constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //sets the size of the panel (drawing area) according to screenWidth and screenHeight
        //used instead of setSize() so JFrame.pack() works correctly

        this.setBackground(Color.black);
        //sets the background color of the panel

        this.setDoubleBuffered(true);
        //enables double buffering to reduce flickering when redrawing graphics
        //draws frames off-screen first, then displays them

        this.addKeyListener(keyH);
        //adds the KeyHandler object to listen for keyboard input
        //handles movement like W, A, S, D and so on for the player

        this.setFocusable(true);
        //allows the panel to receive focus so it can detect key presses
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        //creates a new Thread object
        //Thread = a separate path of execution that can run code independently from the main program
        //used in games so the game loop can run continuously without freezing the window
        gameThread.start();
        //starts the thread and calls the run() method automatically
        //because this class implements Runnable
    }

    @Override
    public  void run(){
        //run() is automatically executed when the thread starts
        //contains the main game loop
        //controls updating the game state and drawing frames at a constant FPS

        double drawInterval = 1_000_000_000/FPS;
        //time between frames in nanoseconds
        // 1 second = 1_000_000_000 ns, divided by FPS//0.01666 seconds

        double delta = 0;
        //keeps track of how much "frame time" has passed
        //used to determine when to update and draw the next frame

        long lastTime = System.nanoTime();
        //.nanoTime() returns the current time in nanoseconds(1 second = 1_000_000_000 ns)
        //used to measure very precise time intervals between frames for smooth FPS calculation

        long currentTime;
        //will store the current time in each loop iteration

        long timer = 0;
        //accumulates time to measure 1 second intervals
        //used to calculate and print FPS every second

        int drawCount = 0;
        //counts how many frames were drawn in the current 1-second period
        //used for FPS tracking



        while(gameThread != null){
            //the loop keeps running as long as the gameThread exists
            //when the thread is stopped or set to null, the loop ends and the game stops updating
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1){
                update();
                /*
                 calls the update() method of GamePanel
                 updates the **game state**, e.g.:
                 - checks which keys are pressed (via keyH)
                 - moves the player or other objects
                 - handles collisions, events, or any game logic
                 */
                repaint();
                /*
               tells Swing to redraw the panel
               automatically calls paintComponent(Graphics g)
               draws the player, background, and other graphics on the screen
               does NOT draw immediately; Swing schedules it efficiently
                 */
                delta--;
                drawCount++;
            }
            if(timer >= 1_000_000_000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public  void update(){
        player.update();
        //calls the update() method of the Player object
        //checks which keys are pressed (via keyH)
        //updates player position, movement, and any other player-related logic
        //this is where the game state changes each frame before drawing
    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        //calls the parent JPanel's paintComponent method
        //clears the screen and prepares it for drawing new graphics

        Graphics2D g2 = (Graphics2D) g;
        //casts Graphics to Graphics2D for more advanced drawing features
        //allows smoother drawing, scaling, rotations, and better control

        player.draw(g2);
        //calls the draw() method of the Player object
        //draws the player on the screen at its current position

        g2.dispose();
        //releases system resources used by Graphics2D object
        //good practice to prevent memory leaks in continuous drawing
    }
}

