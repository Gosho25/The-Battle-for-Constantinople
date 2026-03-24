package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();//JFrame is a "top-level" container for creating GUI applications
        //The frame container contains the title bar and border and can have menu bars.

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //defines what action occurs when a user initiates a "close" on that window
        // Exits the application entirely(JVM process shut down immediately)

        window.setResizable(false);
        //controls whether a user is allowed to manually resize a window
        //in this case, the user can't resize the window

        window.setTitle("The battle for Constantinople");
        //set the text displayed in the title bar of a window

        GamePanel gamePanel = new GamePanel();
        /*
        class from the same package as the Main class, who extends
        JPanel and implements Runnable(Game/src/Main/GamePanel)
         */
        window.add(gamePanel);//adds the gamePanel object to the JFrame container

        window.pack();
        //sizes the frame so that all its contents are at or above their preferred sizes
        //alternative: setSize(width, height), but not usable in this game

        window.setLocationRelativeTo(null);
        //used to position a window
        //"null" centers the window in the middle of the screen

        window.setVisible(true);
        //the window exists in memory but is not shown on the screen, so setVisible show(true) or hide(false) it.

        gamePanel.startGameThread();
    }
}