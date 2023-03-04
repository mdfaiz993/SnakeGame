import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.*;


public class panel extends JPanel implements ActionListener {

    static int width=1200;
    static int height=600;
    //grid unit size
    static int unit=50;

    int totalunit=(width*height)/unit;

    int score;
    //location of food i.e x and y coordinate
    int fx,fy;

    //Initial length and direction of snake
    int length=3;
    char dir='R';
    //true if game is running else false
    boolean flag=false;

    //for spawning food randomly on screen
    Random random;
    Timer timer;
    static int DELAY = 160;

    int xsnake[] =new int[totalunit]; // x coordinate
    int ysnake[]=new int[totalunit]; // y coordinate

    panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);//set background panel to black
        this.addKeyListener(new MyKey());//to listen to key to perform action after pressing keyboard

        //get rapid from user(keyboard) to program.. directly get input from keyboard to java program
        //enable keyboard input to the application..detect by program
        this.setFocusable(true);
        random=new Random();

        gamestart();
    }
    public void gamestart(){
        flag=true;
        spawnfood();

        //timer to check on the game state in each 160ms
        timer=new Timer(DELAY,this);
        timer.start();

    }

    public void spawnfood(){
        //calculate x coordinate that is multiple of unit
        fx= random.nextInt((int)width/unit)*unit;

        //calculate y coordinate that is multiple of unit
        fy= random.nextInt((int)height/unit)*unit;

    }
    //is implicitly called when panel is created
    public void paintComponent(Graphics graphic){
        //call method of parent class "JPanel" with same name
        super.paintComponent(graphic);
        draw(graphic);

    }
    public void draw(Graphics graphic){
        if(flag) {
            graphic.setColor(Color.yellow);
            graphic.fillOval(fx, fy, unit, unit);//to draw food

            //tospawn the snakes body
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    graphic.setColor(Color.red);
                    graphic.fillRect(xsnake[0], ysnake[0], unit, unit);
                } else {
                    graphic.setColor(Color.green);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);

                }
            }
            //score to display
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics fme=getFontMetrics(graphic.getFont());
            graphic.drawString("Score: "+score,(width- fme.stringWidth("Score: "+score))/2,graphic.getFont().getSize());
        }
        else{
            gameover(graphic);
        }
    }

    public void gameover(Graphics graphic){
        //for score display
        graphic.setColor(Color.white);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fme=getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+score,(width- fme.stringWidth("Score: "+score))/2,graphic.getFont().getSize());

        //game-over display
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics fme1=getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over",(width- fme1.stringWidth("Game Over"))/2,height/2);

        //for replay display
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics fme2=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay ",(width- fme2.stringWidth("Press R to replay"))/2,height/2+150);

    }

    public void move(){
        //for all the other body parts
        for(int i=length;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }

        //for updating head
        switch(dir){
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
        }
    }

    public void check(){
        //if body of snake is touched by its head
        for(int i=length;i>0;i--)
        {
            if(xsnake[0]==xsnake[i] && ysnake[0]==ysnake[i]){
                flag=false;
            }
        }

        //if snake head hit with walls
        if(xsnake[0]<0 || xsnake[0]>width  || ysnake[0]<0 || ysnake[0]>height){
            flag=false;
        }

        if(flag==false)
            timer.stop();
    }

    public void foodEaten(){
        if(xsnake[0]==fx && ysnake[0]==fy){
            length++;
            score++;
            spawnfood();
        }

    }



    public class MyKey extends KeyAdapter{
        public void keyPressed(KeyEvent k){
            switch(k.getKeyCode()){
                case VK_UP:
                    if(dir!='D'){
                        dir='U';
                    }
                    break;
                case VK_DOWN:
                    if(dir!='U'){
                        dir='D';
                    }
                    break;
                case VK_RIGHT:
                    if(dir!='L'){
                        dir='R';
                    }
                    break;
                case VK_LEFT:
                    if(dir!='R'){
                        dir='L';
                    }
                    break;
                case VK_R:
                    if(!flag){
                        score=0;
                        length=3;
                        dir='R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        gamestart();
                    }

                    break;
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        if(flag){
            move();
            foodEaten();
            check();
        }
        //to repaint the frame
        repaint();

    }
}