
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Data_Gathering{
    public static void random(){
        return ;
    }
   public static void man(String args[]) throws FileNotFoundException, IOException{
       BufferedReader br=new BufferedReader(new FileReader("data.json"));
       
       PrintWriter train=new PrintWriter(new FileWriter("train.json"));
       PrintWriter test=new PrintWriter(new FileWriter("test.json"));
       String line="";int i=1,j,k;
       while((line=br.readLine())!=null){
           
        
       }
   }
   public static void main(String args[]) throws Exception{
       
       
       JSONObject obj;
   //    ArrayList<JSONObject> json=new ArrayList<JSONObject>(); // not used
   
    String line = null;
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader("data.json");
        PrintWriter out=new PrintWriter(new FileWriter("train.json"));
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int j=0,pos=0,neg=0;
       while((line = bufferedReader.readLine()) != null &&(pos<10000 || neg<10000)) {
           
            obj = (JSONObject) new JSONParser().parse(line);
           if((j=(int)(double)obj.get("overall"))!=3){
             if((j==4||j==5)&&pos<10000){pos++;out.println(line);}
             else if((j==1||j==2)&&neg<10000){ neg++;out.println(line);}
            }
        }
       System.out.println("pos "+pos+" neg "+neg+"  total"+(pos+neg));
       out.close();
                    
   }
    }