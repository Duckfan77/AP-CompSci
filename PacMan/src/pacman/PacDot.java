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
public class PacDot implements Functions {
    private static float[] yData; // Holds y values evaluated with function
    private static int yIndex = 0; // current index for yData
    private static int function = 0; // The function to evaluate
    private Image img;

    public PacDot(){
        PacDot.yData = new float[53];
    
        try {
              this.img = ImageIO.read(getClass().getResource("pacman.png"));
              } catch (IOException ex) {
              Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    private float adjustYValue(float y){
     
        yData[yIndex] = y; // Store Pacdot's y value in yData
        yIndex++;
        return (652f/-36)*y+326; // Pixel adjusted y value
    }
    
    public static float[]getYData(){
        return yData;
    }
    
    public float evaluateFunction(float x){
       float y = 0.0f;
        switch (function){
            case 0: y =  evaluateQuadratic(x,-0.2f,-2,15);
             break;
            case 1: y =  evaluateLinear(x,3.0f,5,-7);
             break;  
            case 2: y = evaluateCubic(x,-0.2f,-5,8);
             break;
            case 3: y = evaluateSine(x,5.0f,2,0);
             break; 
            case 4: y = evaluateLogistic(x,12.0f,2,5);
             break; 
            case 5: y = evaluateExponential(x,-.1f,0,0);
             break;
            
        }
       return y;
        
      
    }
 
    
    public void setYIndex(){
        yIndex = 0;
    }
    
    public void nextFunction(){
        function++;
        if(function == 6)function = 0;
    }
    
    @Override
    public float evaluateLinear(float x, float a, int h, int k) {
        float y = a*(x-h) + k;
        return adjustYValue(y);
    }

    @Override
    public float evaluateQuadratic(float x, float a, int h, int k) {
        float y = a*(x-h)*(x-h)+k;
        return adjustYValue(y);
    }

 
    @Override
    public float evaluateCubic(float x, float a, int h, int k) {
        float y = a*(x-h)*(x-h)*(x-h)+k;
        return adjustYValue(y);
    }

    @Override
    public float evaluateSine(float x, float a, int h, int k) {
         float y = (float)(a*Math.sin(x-h)+k);
         return adjustYValue(y);
    }

    @Override
    public float evaluateLogistic(float x, float a, int h, int k) {
        float y = (float)(a/(1+Math.exp(-x-h))+k);
        return adjustYValue(y);
    }

    @Override
    public float evaluateExponential(float x, float a, int h, int k) {
        float y = (float)(a*Math.exp(x/2.0f-h)+k);
        return adjustYValue(y);
    }

    public Image getImage(){
        return img;
    }
 }
