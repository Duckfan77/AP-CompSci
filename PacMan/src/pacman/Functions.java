/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author cwenthin
 */
public interface Functions {
    enum Function{kLinear, kQuadratic, kCubic, kSine, kLog, kExp};
    
    public float evaluateLinear(float x, float a, int h, int k);
    
    public float evaluateQuadratic(float x, float a, int h, int k);
    
    public float evaluateCubic(float x, float a, int h, int k);
    
    public float evaluateSine(float x, float a, int h, int k);
    
    public float evaluateLogistic(float x, float a, int h, int k);
    
    public float evaluateExponential(float x, float a, int h, int k);
}
