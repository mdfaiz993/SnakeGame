
    import javax.swing.*;

    public class frame extends JFrame{

        frame(){
            this.add(new panel());

            //set the title of window/frame
            this.setTitle("Snake Game");

            //user cannot resize the window
            this.setResizable(false);

            //make screen preferable---change size screen according to pc and OS resolution
            this.pack();

            /* make window/frame visible... by default it is false */
            this.setVisible(true);
        }

    }
