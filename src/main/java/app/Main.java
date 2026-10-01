package app;

import gui.PrimaryWindow;

public class Main {
    public static void main(String[] args) {
        /**
         * Application entry point. Instantiates and executes the command-line interface.
         * Then launches the GUI.
         *
         * @param args command-line arguments (unused)
         */

        //PrimaryWindow window = new PrimaryWindow();
        //window.show();

        Cli commandline = new Cli();
        commandline.run();



    }

}