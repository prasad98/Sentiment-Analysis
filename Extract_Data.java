
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SON OF HEAVEN
 */
public class Extract_Data{
    public int i=0;
    public int main(String filepath) throws FileNotFoundException, IOException, ParseException{
        System.out.println("Data Is Exctracting ..... ");
       i=0;
       JSONObject obj;
   //    ArrayList<JSONObject> json=new ArrayList<JSONObject>(); // not used
       
     
    // This will reference one line at a time
    String line = null;
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(filepath);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int j,k=1;
        PrintWriter review=new PrintWriter(new FileWriter("review1.txt"));
        PrintWriter summary=new PrintWriter(new FileWriter("summary.txt"));
        PrintWriter rating=new PrintWriter(new FileWriter("rating.txt"));    
        while((line = bufferedReader.readLine()) != null) {
            if(i==10000*k){
                k++; review.close();
                review=new PrintWriter(new FileWriter("review"+k+".txt"));
            }
            obj = (JSONObject) new JSONParser().parse(line);
           if((j=(int)(double)obj.get("overall"))!=3){
             review.println(i+" . "+(String)obj.get("reviewText"));
             summary.println(i+" . "+(String)obj.get("summary"));
             rating.println(j);i++;
            }
            
        }
        // Always close files.
        bufferedReader.close();
        review.close();
        summary.close();
        rating.close();
        System.out.println("Given Data Extracted Suceessfully.....");
       return i;
       
   }
}