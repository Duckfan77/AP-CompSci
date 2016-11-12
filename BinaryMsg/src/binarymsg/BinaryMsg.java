/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarymsg;

/**
 *
 * @author dlwin
 * 
 */
import java.io.*;

public class BinaryMsg {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // The name of the file to open
       
        String fileName = "binary_msg.txt";
          

        // This will reference one line at a time
       
        String theFile = null;
        @SuppressWarnings("UnusedAssignment")
        String line = "";
        
        try {
            
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            try ( // Always wrap FileReader in BufferedReader.
           
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line.length());
                    theFile = line;
                    
                }
                // Always close files.
                bufferedReader.close();
            }         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
          }
        
        if(theFile != null){// We have data
            // TODO Code
            Integer fileInt = Integer.parseInt(theFile,2);
            System.out.println(convertToText(fileInt.toString()));

        }
          System.out.println(theFile);        
    }
    
    public static String convertToText(String sentence)
    {
        String outStr = "";
        
        for(int k=0; k<sentence.length(); k+=3){
           String charStr = sentence.substring(k,k+3);
           
           int charInt = Integer.parseInt(charStr);
           outStr+= (char)charInt;
        }
        
        return outStr;
    }
 }
    
