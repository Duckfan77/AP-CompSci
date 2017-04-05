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
public class BlueGhost extends Ghost implements Functions{

    private class FunctionData
    {
        private float a;
        private int h;
        private int k;
        private Function function;
        private double cost = Double.MIN_VALUE;
        
        public FunctionData(float a, int h, int k, Function function){
            this.a = a;
            this.h = h;
            this.k = k;
            this.function = function;
        }
        
        public FunctionData(float a, int h, int k, Function function, double cost){
            this.a = a;
            this.h = h;
            this.k = k;
            this.function = function;
            this.cost = cost;
        }
        
        public float getA(){return a;}
        public int getH(){return h;}
        public int getK(){return k;}
        public Function getFunc(){return function;}
        public double getCost(){return cost;}
    }
    
    private boolean findingBest;
    
    private FunctionData bestFunction;
    
    public BlueGhost() {
        super("blue_ghost.png");
    }
    
    /**
     *
     */
    @Override
    public void trainGhost() {
        findingBest = true;
        
        for(Function func : Functions.Function.values()){
            if(bestFunction==null){
                bestFunction = solve(func);
            }else{
                FunctionData altFunction = solve(func);
                if(altFunction.cost < bestFunction.cost){
                    bestFunction = altFunction;
                }
            }
        }
    }
    
    private FunctionData solve(Function function)
    {
        float bestA = function==Function.kLog?-15:-5;
        int bestH = -5;
        int bestK = -15;
        
        //find best A
        for(float a = bestA; a<=(function==Function.kLog?15:5); a+=0.1f){
            bestA = (cost(a, bestH, bestK, function) < cost(bestA, bestH, bestK, function)?a:bestA);
        }
        
        //find best H
        for(int h = bestH; h<=5; h++){
            bestH = (cost(h, bestH, bestK, function) < cost(bestA, bestH, bestK, function)?h:bestH);
        }
        
        //find best K
        for(int k = bestK; k<=15; k++){
            double newCost = cost(k, bestH, bestK, function);
            if(newCost <= cost(bestA, bestH, bestK, function)){
                bestK = k;
            }
        }
        
        return new FunctionData(bestA, bestH, bestK, function, cost(bestA, bestH, bestK, function));
    }

    private double cost(float a, int h, int k, Function function){
        double sum=0;
        int n = 0;
        FunctionData func = new FunctionData(a, h, k, function);
        
        for(;n<=26;n++){
            double pred = executeFunction(n-13, func);
            pred-=PacDot.getYData()[n];
            sum+=Math.pow(pred, 2);
        }
        return Math.abs(sum/n);
    }
    
    @Override
    public float followPacman(float in) {
        findingBest = false;
        return executeFunction(in, bestFunction);
    }

    private float executeFunction(float in, FunctionData function)
    {
        switch(function.function){
            case kCubic:
                return evaluateCubic(in, function.a, function.k, function.h);
            case kExp:
                return evaluateExponential(in, function.a, function.k, function.h);
            case kLinear:
                return evaluateLinear(in, function.a, function.k, function.h);
            case kLog:
                return evaluateLogistic(in, function.a, function.k, function.h);
            case kQuadratic:
                return evaluateQuadratic(in, function.a, function.k, function.h);
            case kSine:
                return evaluateSine(in, function.a, function.k, function.h);
            default:
                return -100;
        }
    }
    
    public float mapY(float y)
    {
        return (-163f/9)*(y-18);
    }
    
    private float mapX(float x)
    {
        return (401.f/26.f)*x + 401;
    }

    @Override
    public float evaluateLinear(float x, float a, int h, int k) {
        float y = (a*((x-h)) +k);
        if(findingBest) return y;
        return mapY(y);
    }

    @Override
    public float evaluateQuadratic(float x, float a, int h, int k) {
        float y = (a*((x-h) * (x-h)) +k);
        if(findingBest) return y;
        return mapY(y);
    }

    @Override
    public float evaluateCubic(float x, float a, int h, int k) {
        float y = (a*((x-h) * (x-h) * (x-h)) +k);
        if(findingBest) return y;
        return mapY(y);    
    }
    
    @Override
    public float evaluateSine(float x, float a, int h, int k) {
        float y = (float)(a*Math.sin(x-h) + k);
        if(findingBest) return y;
        return mapY(y);
    }

    @Override
    public float evaluateLogistic(float x, float a, int h, int k) {
        float y = (float)(a/(1+Math.exp(-x-h))+k);
        if(findingBest) return y;
        return mapY(y);
    }

    @Override
    public float evaluateExponential(float x, float a, int h, int k) {
        float y = (float)(a*Math.exp(x/2.0f-h)+k);
        if(findingBest) return y;
        return mapY(y);
    }
}
