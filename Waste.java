
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SON OF HEAVEN
 */
public class Waste {
    
  public static void main(String[] args) throws Exception {
      
      BufferedReader br=new BufferedReader(new FileReader("data.json"));
      String line="";int i=0;
      PrintWriter test=new PrintWriter(new FileWriter("test.json"));
      PrintWriter train=new PrintWriter(new FileWriter("train.json"));
      while((line=br.readLine())!=null){
          if(i<100000)test.println(line);
          else train.println(line);i++;
          
      }
      test.close();
      train.close();
      
  }
}
