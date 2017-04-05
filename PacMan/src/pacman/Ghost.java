/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/**
 *
 * @author dlwin
 */
public abstract class Ghost{
    
    private Image img;

    public Ghost(String str){
        try {
              this.img = ImageIO.read(getClass().getResource(str));
            } catch (IOException ex) {
              Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    public abstract void trainGhost();
    public abstract float followPacman(float x);
       
    public Image getImage(){
        return img;
    }
  
}
