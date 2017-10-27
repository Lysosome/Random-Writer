import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
   Writes random text with character frequencies and location based on one or more given text files.
*/
public class RandomWriter_Multiple_Ma
{
   private static String fileName;
   private static int level;
   private static int genLength;
   private static Scanner myScan = new Scanner(System.in);
   private static String fullText="";
   private static boolean done = false;
   private static String notDone = "yes";

   /**
      Writes random text with character frequencies and location based on one or more given text files.
   */
   public static void main(String[] args)
   {
      JFileChooser fileChooser = new JFileChooser();
      
      while(notDone.equals("yes"))
      {
         System.out.println("What is the text file you'd like to emulate? (e.g. '1984.txt')");
         //use joptionpane to choose file
         int result = fileChooser.showOpenDialog(null);
   
         if (result == JFileChooser.APPROVE_OPTION)
         {
            fileName = fileChooser.getSelectedFile().getPath();
            JOptionPane.showMessageDialog(null, "You selected " + fileName);
         }
         else if (result == JFileChooser.CANCEL_OPTION)
         {   
            JOptionPane.showMessageDialog(null, "You selected nothing.");
         }
         else if (result == JFileChooser.ERROR_OPTION)
         {
            JOptionPane.showMessageDialog(null, "An error occurred.");  
         }   

         System.out.print("\nReading file... (This may take up to 15 seconds) ");
         readFile();
         System.out.print("complete.\n");
         System.out.println("Continue selecting more files?");
         notDone = myScan.nextLine().toLowerCase();
      }
      
      //System.out.println(fullText);
      
      System.out.println("What is the level of analysis you'd like? (Suggested: 1-15)");
      level = myScan.nextInt();
      
      System.out.println("How many random characters would you like to generate? (Suggested: 500-1000)");
      genLength = myScan.nextInt();
      
      String stem="";
      int lineCounter = 0;
      char nextChar = generateLetter("");
      for(int i=0; i<genLength; i++)
      {
         //goes to next line after 80 characters is reached and the char is a space
         if(lineCounter>80 && nextChar==' ')
         {
            System.out.print("\n");
            lineCounter=0;
         }
         else
         {
            System.out.print(nextChar);
            lineCounter++;
         }
         
         //adjust the stem for the next char
         if(stem.length()>=level)
            stem=stem.substring(1);
         stem+=nextChar;
         
         nextChar = generateLetter(stem);
      }
   }
   
   /*
      Pseudocode:
      First, the program begins with one truly random seed letter.
      
      Then, for each letter afterward, the program first identifies what I call the “stem”—the sequence
      which immediately precedes the new letter.  Then, it scans the source file for all instances of
      the “stem,” and stores the next character after the stem into an ArrayList called “candidates.”
      
      Finally, the program randomly selects one of the “candidate” letters to print next.
      
      Then, the process loops with a new “stem.”
   */
   
   /**
      Generates the next letter in sequence given a certain stem.
      @param a sequence of letters (the stem)
      @return the next letter
   */
   public static char generateLetter(String stem)
   {
      ArrayList<Character> candidates = new ArrayList<Character>();
      if(level==1 || stem.length()<1)
      {
         for(int i=0; i<fullText.length(); i++)
            candidates.add(fullText.charAt(i));
         return randPicker(candidates);
      }
      else
      {
         for(int i=0; i<fullText.length()-stem.length(); i++)
         {
            if(fullText.substring(i,i+stem.length()).equals(stem))
               candidates.add(fullText.charAt(i+stem.length()));
         }
         return randPicker(candidates);
      }
   }
   
   /**
      Given an ArrayList of characters, randomly returns one of them.
   */
   private static char randPicker(ArrayList<Character> arr)
   {
      return arr.get((int) (Math.random()*arr.size()) );
   }
   
   /**
      Reads in a text file.
   */
   public static void readFile()
   {
     // This will reference one line at a time
     String line = null;

     try {
         // FileReader reads text files in the default encoding.
         FileReader fileReader = 
             new FileReader(fileName);

         // Always wrap FileReader in BufferedReader.
         BufferedReader bufferedReader = 
             new BufferedReader(fileReader);

         while((line = bufferedReader.readLine()) != null) {
             fullText += (line)+" ";
         }   

         // Always close files.
         bufferedReader.close();         
     }
     catch(FileNotFoundException ex) {
         throw new Error("Unable to open file '" + 
             fileName + "'");            
     }
     catch(IOException ex) {    
         throw new Error("Unable to open file '" + 
             fileName + "'");
     }
   }
}