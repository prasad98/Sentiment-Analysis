/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SON OF HEAVEN
 */
import java.io.*;
import java.util.*;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
//import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;;
public class Sample {

    /**
     * @param args the command line arguments
     */
    Annotation annotation;
    StanfordCoreNLP pipeline ;
    Properties props;SentiWordNet sentiscore;
    int n=0;
    PrintWriter out;
    
    public Sample() throws IOException{
        sentiscore=new SentiWordNet();
      
    props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos");
    props.put("ssplit.eolonly", "true");
    pipeline = new StanfordCoreNLP(props);

    
    }
    public void main(String path,String pa,int k) throws IOException{
         n=0;
         System.out.println("Called Feature Extraction Procedure......");
        out=new PrintWriter(new FileWriter(pa));
        if(path.equals("review.txt")){
            System.out.println("Features From Review Data .....");
            for(int i=1;i<=k;i++){
                man("review"+i+".txt",pa);   
                System.out.println("over for review part "+i +" and count till now is "+n);
            }
        }
        else{
            System.out.println("Features From Summary Data .....");
            man(path,pa);        
        }
        out.close();
        System.out.println(n+" this is feature count");
    }
    public void man(String path,String pa) throws IOException {
        System.out.println("Pos Tagger is Called For Tagging......");
    annotation = new Annotation(IOUtils.slurpFileNoExceptions(path));
    pipeline.annotate(annotation);
    List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
    for(CoreMap sentence: sentences) {
        n++;
       int flag=0;double temp=0;    double[] values=new double[4];  
     for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
    // this is the text of the token
     String word = token.get(TextAnnotation.class);
    // this is the POS tag of the token
    String pos = token.get(PartOfSpeechAnnotation.class);
    if (pos.equals("NN") || pos.equals("NNS")|| pos.equals("NNP")|| pos.equals("NNPS")){
                   values[0]+=sentiscore.extract(word,"n");flag=0;
    }
  /*  else if (pos.equals("VB") || pos.equals("VBD")|| pos.equals("VBG") || pos.equals("VBN")|| pos.equals("VBP") || pos.equals("VBZ")) {
                    pos="v";verb+=sentiscore.extract(word,"v");
    }*/
    else if (pos.equals("JJ") || pos.equals("JJR")|| pos.equals("JJS")){
                    values[1]+=sentiscore.extract(word,"a");if(flag==1){values[3]+=temp+sentiscore.extract(word,"a");flag=0;}
    }
    else if (pos.equals("RB") || pos.equals("RBR")|| pos.equals("RBS")){
                    flag=1;values[2]+=(temp=sentiscore.extract(word,"r"));
    }
    }
     out.println(values[0]+" "+values[1]+" "+values[2]+" "+values[3]);
  
     
    }
    System.out.println("SentiWord Net For Scoring Words .... ");
    }
}