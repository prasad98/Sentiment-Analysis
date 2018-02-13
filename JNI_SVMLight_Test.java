/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SON OF HEAVEN
 */
import java.io.BufferedReader;
import java.io.FileReader;  
import java.io.FileWriter;
import java.io.PrintWriter;
import static java.lang.Math.ceil;
import jnisvmlight.FeatureVector;
import jnisvmlight.KernelParam;
import jnisvmlight.LabeledFeatureVector;
import jnisvmlight.LearnParam;
import jnisvmlight.SVMLightModel;
import jnisvmlight.SVMLightInterface;
import jnisvmlight.TrainingParameters;

public class JNI_SVMLight_Test {
  public static void main(String[] args) throws Exception {
      System.out.println("Main Procedure is Called ");
  Extract_Data extraction=new Extract_Data();
 int size=extraction.main("train.json");  //training data 
    SVMLightInterface trainer = new SVMLightInterface();
    System.out.println("Size of Train data "+size);
  Sample feature=new Sample(); 
 feature.main("review.txt","train_feature2.txt",(int)ceil(size/10000.0));//Sampling the review data
 feature.main("summary.txt","train_feature1.txt",0);
    String rate="";double label=0;   
    BufferedReader br=new BufferedReader(new FileReader("rating.txt"));
    BufferedReader feature1=new BufferedReader(new FileReader("train_feature1.txt"));//summary
    BufferedReader feature2=new BufferedReader(new FileReader("train_feature2.txt"));//reviews
     PrintWriter out=new PrintWriter(new FileWriter("final.txt"));
    LabeledFeatureVector[] traindata = new LabeledFeatureVector[size];
    int dims[]={1,2,3,4,5,6,7,8};
    String f1="",f2="";int i=0,j=0;
    while((f1=feature1.readLine())!=null) {
         double[] values = new double[8];
        f2=feature2.readLine();
        String[] words=f1.split("\\s");

        values[0]=Double.parseDouble(words[0]);
        values[1]=Double.parseDouble(words[1]);
        values[2]=Double.parseDouble(words[2]);
        values[3]=Double.parseDouble(words[3]);
      
         String[] word=f2.split("\\s"); 
      values[4]=Double.parseDouble(word[0]);
      values[5]=Double.parseDouble(word[1]);
      values[6]=Double.parseDouble(word[2]);
      values[7]=Double.parseDouble(word[3]);
        rate=br.readLine();
        if(rate.equals("1")||rate.equals("2")) label=-1;
        else if(rate.equals("4")||rate.equals("5")) label=1;
      traindata[i] = new LabeledFeatureVector(label, dims,values);  
     out.println(traindata[i].toString());
      i++;
    }out.close();
    br.close();
    feature1.close();
    feature2.close();
    System.out.println("\nTRAINING SVM-light MODEL ..");
    LearnParam lp=new LearnParam();                         //parameters starts here
    lp.eps=1.0;lp.verbosity=1;lp.biased_hyperplane=0;
    KernelParam kp=new KernelParam();
    kp.kernel_type=KernelParam.RBF;
    TrainingParameters tp = new TrainingParameters(lp,kp);
 SVMLightModel model = trainer.trainModel(traindata,tp);            //feeding parameters
    System.out.println(" DONE.");
    
  //  model.writeModelToFile("jni_model.dat");
    model = SVMLightModel.readSVMLightModelFromURL(new java.io.File("jni_model.dat").toURL());
  
   size=extraction.main("test.json");  //testing data  
   System.out.println("Size of test data "+size);
   
   System.out.println(size+"size is ");
  feature.main("review.txt","test_feature2.txt",(int)ceil(size/10000.0)); //Sampling the review data
 feature.main("summary.txt","test_feature1.txt",0);
    br=new BufferedReader(new FileReader("rating.txt"));
   feature1=new BufferedReader(new FileReader("test_feature1.txt"));//summary
   feature2=new BufferedReader(new FileReader("test_feature2.txt"));//reviews
   out.close();
   out=new PrintWriter(new FileWriter("Actual.txt"));
   PrintWriter result=new PrintWriter(new FileWriter("result.txt"));
   FeatureVector testdata ;i=0;
   int tr=0,ne=0,pos=0,neg=0;double k;  //analytics
   while((f1=feature1.readLine())!=null) {
        double[] values = new double[8];
        f2=feature2.readLine();
        String[] words=f1.split("\\s");

        values[0]=Double.parseDouble(words[0]);
        values[1]=Double.parseDouble(words[1]);
        values[2]=Double.parseDouble(words[2]);
        values[3]=Double.parseDouble(words[3]);
      
         String[] word=f2.split("\\s"); 
      values[4]=Double.parseDouble(word[0]);
      values[5]=Double.parseDouble(word[1]);
      values[6]=Double.parseDouble(word[2]);
      values[7]=Double.parseDouble(word[3]);
        rate=br.readLine();
        if(rate.equals("1")||rate.equals("2"))label=-1;
        else if(rate.equals("4")||rate.equals("5"))label=1;
     
       testdata = new FeatureVector(dims,values);
      k=model.classify(testdata);
      
      out.println(i+".  Actual = " +label+" - predeicted = "+k);
     k=k>1?1:-1;
     out.println(" rounded = "+k+" Accuracy = "+(label==k)); 
     if(label==1)
        tr++;
     else
         ne++;
     if(label==k)pos++;
     else
      neg++;
      result.println();i++;
    }
    System.out.println(" DONE.");
    out.println("Analytics Are here");
    out.println();out.println("original pos are "+tr);
    out.println();out.println("original neg are "+ne);
    out.println("Accurate Results are --- "+pos);
    out.println("UnAccurate Results are --- "+neg);
    out.close();
    result.close();
    br.close();
    feature1.close();
    feature2.close();
  }
}