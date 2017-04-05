/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author dlwin
 */
public class PacMan extends JPanel{
    
      private Image backGroundImage;  
      private static final ArrayList<Ghost> GHOSTS = new ArrayList<>();
      private int xValue = -26;
      private final PacDot Pacdot = new PacDot();
      private Timer clock;
      private final int PACMAN_STATE = 0; // Just PacMan
      private final int TRAIN_STATE = 1; // Ghosts training
      private final int FOLLOW_STATE = 2; // Ghosts following
      private int STATE = PACMAN_STATE;

          
  @Override
  public void paintComponent(Graphics g)
  {
        try {
              backGroundImage = ImageIO.read(getClass().getResource("bg.png"));
              g.drawImage(backGroundImage,0,0,this);
          } catch (IOException ex) {
              Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
          }
        
  
        float adjX = (902f/52)*xValue+451;
        // draw Pacdot
        float y = Pacdot.evaluateFunction(xValue);
        g.drawImage(Pacdot.getImage(),(int)adjX,(int)y,this);

        // Time to follow PacDot
        if(STATE == FOLLOW_STATE ){
              GHOSTS.stream().forEach((ghost) -> {
            float ghosty = ghost.followPacman(xValue);
            g.drawImage(ghost.getImage(),(int)adjX,(int)ghosty,this);
           
           
          }); 
        }
     
        xValue++;
        
         if(xValue == 27){ // Past the last x value in coordinate plane
             xValue = -26;
             Pacdot.setYIndex(); // Resets index to zero for recording PacDot y
             if (STATE == PACMAN_STATE){ 
                STATE = TRAIN_STATE;  // Time to train
                clock.stop();
                GHOSTS.stream().forEach((ghost) -> {
                ghost.trainGhost();
                 });
                STATE = FOLLOW_STATE; // At end of training, ghosts follow
                clock.start();  
            }
             else{ // If past last x value we ghosts were following, generate
                 // new path for PacDot
                 Pacdot.nextFunction();
                 STATE = PACMAN_STATE;
             }
         }
        
  
  }
    
 // an inner class
   class timerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
         repaint();
        }
   }
   
    public void setUpComponents(){
        BlueGhost blue = new BlueGhost();
        GHOSTS.add(blue);
          
        JFrame window = new JFrame("Machine Learning Ghosts");
        // Set this window's location and size:
        window.setBounds(0, 0, 906, 682);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = window.getContentPane();
        c.add(this);
        window.setResizable(false);
        window.setVisible(true);
        // set-up timer, the object responding is timerListener
        clock = new Timer(250,new timerListener());
        clock.start();
             
  }
  
    public static void main(String[] args) {
        // TODO code application logic here
        PacMan theMan = new PacMan();
        theMan.setUpComponents();
      
     }
    
}
