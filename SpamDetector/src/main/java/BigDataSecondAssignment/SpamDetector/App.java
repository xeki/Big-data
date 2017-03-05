package BigDataSecondAssignment.SpamDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

import org.apache.commons.math3.util.Precision;
import org.jfree.ui.RefineryUtilities;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class App 
{
	private static int[] hashSeeds =  new int[64];
	public static void main( String[] args )
    {
    	try
    	{
    	hyperPlaneHashSeeds();
    	//PartI();
    	//PartII();
    	PartIII();
    	//partIV();
    	
    	
   }
   catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    }
 public App() {
		super();
		
	}
    private static void PartI() throws IOException
    {
    	String fileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron1\\ham";	
    	//String queryFileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron1\\spam";
    	String queryFileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron2\\ham";
    	int emailNos= 1000,querySize = 1;
    	List<EmailDistance> distanceList;
    	List<Email> testList = new ArrayList<Email>();
    	List<Email> queryList = new ArrayList<Email>();
    	List<Email> neighbourEmails = new ArrayList<Email>();
    	//Part I
    	testList = emailReaderFromFile(fileLocation,"ham",1, emailNos);
    	queryList = emailReaderFromFile(queryFileLocation,"ham",1, querySize);
    	distanceList = computeDistanceFromQueryEmail(queryList.get(0), testList);
    	Collections.sort(distanceList);
    	System.out.println("The smallest distance: " + distanceList.get(0).getDistance() + " index: "+ distanceList.get(0).getIndex() );
    	System.out.println("The largest distance: " + distanceList.get(distanceList.size()-1).getDistance()+ " index: "+ distanceList.get(distanceList.size()-1).getIndex());
    	System.out.println("The random distance: " + distanceList.get(1).getDistance()+ " index: "+ distanceList.get(1).getIndex());
    	System.out.println("The random distance: " + distanceList.get(999).getDistance()+ " index: "+ distanceList.get(999).getIndex());
    	
    }
    private static void PartII() throws IOException
    {
    	//part II
    	String fileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron1\\ham";	
    	//String queryFileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron1\\spam";
    	String queryFileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron2\\ham";
    	int emailNos= 1000,querySize = 1;
    	List<EmailDistance> distanceList;
    	List<Email> testList = new ArrayList<Email>();
    	List<Email> queryList = new ArrayList<Email>();
    	List<Email> neighbourEmails = new ArrayList<Email>();
    	emailNos = 4000;
    	querySize = 1;
    	int hyperPlaneSize = 4;
    	fileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron2\\ham";
    	queryFileLocation = "G:\\WISE\\BigData\\EmailSpam\\enron2\\spam";
    	testList = emailReaderFromFile(fileLocation,"ham",2, emailNos);
    	queryList = emailReaderFromFile(queryFileLocation,"ham",2, querySize);
    	queryList = computeHyperPlaneForEmails(queryList,hyperPlaneSize);
    	testList = computeHyperPlaneForEmails(testList,hyperPlaneSize);
    	for(int i=0;i<queryList.size();i++)
    	{
    	neighbourEmails = closestEmail(queryList.get(i),testList,0, hyperPlaneSize);
    	if(neighbourEmails.size()==0)
    	{
    		System.out.println("Distance = "+ Math.PI/2);
    	}
    	else
    	{
    		distanceList = computeDistanceFromQueryEmail(queryList.get(0), neighbourEmails);
    		Collections.sort(distanceList);
	    	System.out.println("The smallest distance: " + distanceList.get(0).getDistance() + " index: "+ distanceList.get(0).getIndex() );
	    	System.out.println("The largest distance: " + distanceList.get(distanceList.size()-1).getDistance()+ " index: "+ distanceList.get(distanceList.size()-1).getIndex());
	    	System.out.println("Closest Emails size: " + distanceList.size());
 	 	}
    	}	
    }
    private static void PartIII() throws IOException
    {
  	 String [] dataSetHam = new String[5];
    	String [] dataSetSpam= new String[5];
    	String querySpam,queryHam;
    	long [] exactDistTime = new long[100];
    	long [] approxDistTime = new long[100];
    	Double [] minExactDist = new Double[100];
    	Double [][] minApproxDist = new Double[6][100];
    	int hyperPlaneSize,querySize,hyperPlaneIndex=1;
    	Double [] error = new Double[6];
    	List<EmailDistance> distanceList;
    	long startTime,endTime;
    	List<Email> emailList = new ArrayList<Email>();
    	List<Email> queryList = new ArrayList<Email>();
    	List<Email> neighbourEmails = new ArrayList<Email>();
    	hyperPlaneSize = 32;
    	querySize = 50;
    	querySpam = "G:\\WISE\\BigData\\EmailSpam\\enron6\\spam";
    	queryHam = "G:\\WISE\\BigData\\EmailSpam\\enron6\\ham";
    	dataSetHam[0] = "G:\\WISE\\BigData\\EmailSpam\\enron1\\ham";
    	dataSetHam[1] = "G:\\WISE\\BigData\\EmailSpam\\enron2\\ham";
    	dataSetHam[2] = "G:\\WISE\\BigData\\EmailSpam\\enron3\\ham";
    	dataSetHam[3] = "G:\\WISE\\BigData\\EmailSpam\\enron4\\ham";
    	dataSetHam[4] = "G:\\WISE\\BigData\\EmailSpam\\enron5\\ham";
    	dataSetSpam[0] = "G:\\WISE\\BigData\\EmailSpam\\enron1\\spam";
    	dataSetSpam[1] = "G:\\WISE\\BigData\\EmailSpam\\enron2\\spam";
    	dataSetSpam[2] = "G:\\WISE\\BigData\\EmailSpam\\enron3\\spam";
    	dataSetSpam[3] = "G:\\WISE\\BigData\\EmailSpam\\enron4\\spam";
    	dataSetSpam[4] = "G:\\WISE\\BigData\\EmailSpam\\enron5\\spam";
    	for(int i=0;i<5;i++)
    	{
    		emailList.addAll(emailReaderFromFile(dataSetHam[i],"ham",i+1, -1));
    		emailList.addAll(emailReaderFromFile(dataSetSpam[i],"spam",i+1, -1));
    	}
    	System.out.println("Reading emails from 5 sets completed");
    	queryList = emailReaderFromFile(queryHam,"ham",6, querySize);
    	queryList.addAll(emailReaderFromFile(querySpam,"spam",6, querySize));
    	System.out.println("Reading query emails from set 6 completed");
    	for(int j=0;j<queryList.size();j++)
    	{
    		startTime = System.currentTimeMillis();
    		distanceList = computeDistanceFromQueryEmail(queryList.get(j), emailList);
    		endTime = System.currentTimeMillis();
    		exactDistTime[j] = endTime - startTime;
    		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    		Collections.sort(distanceList);
    		minExactDist[j] = distanceList.get(0).getDistance();
    		
    	}
    	System.out.println("Actual distance comparison completed");
    	queryList = computeHyperPlaneForEmails(queryList,hyperPlaneSize);
    	emailList = computeHyperPlaneForEmails(emailList,hyperPlaneSize);
    	System.out.println("HyperPlane computation for whole emails completed");
    	for(int k=0;k<6;k++)
    	{
    		
    		hyperPlaneSize = hyperPlaneIndex;
    		hyperPlaneIndex = hyperPlaneIndex*2;
  	    	for(int j=0;j<queryList.size();j++)
  	    	{
  	    		startTime = System.currentTimeMillis();
  	    		neighbourEmails = closestEmail(queryList.get(j),emailList,0, hyperPlaneSize);
  	    		distanceList = computeDistanceFromQueryEmail(queryList.get(j), neighbourEmails);
  	    		endTime = System.currentTimeMillis();
  	    		approxDistTime[j] = endTime - startTime;
  	    		Collections.sort(distanceList);
  	    		if(distanceList.size()==0)
  	    		{
  	    			minApproxDist[k][j] = Math.PI/2;
  	    		}
  	    		else
  	    		{
  	    			minApproxDist[k][j] = distanceList.get(0).getDistance();
  	    		}
  	    		System.out.println("Hyper plane comparison for query email " + j +" completed");
  	    	}
  	    	System.out.println("Hyper plane comparison for index " + k +" completed");
    	}
    	for(int j =0;j<6;j++)
    	{
    	error[j] = 0.0;
    	for(int k=0;k<100;k++)	
    		{
    		error[j]  = error[j] + Math.abs(minExactDist[k]-minApproxDist[j][k]);
    		}
    	error[j] = error[j]/100;
    	System.out.println("Hyper plane Error for index " + j +" completed");
    	}
    	BarChart_AWT chart = new BarChart_AWT("Hyperplane Errors");
    	chart.setChartSetting(BarChart_AWT.createDatasetError(error),"error chart","hyper planes","error values");
        chart.pack( );        
        RefineryUtilities.centerFrameOnScreen( chart );        
        chart.setVisible( true ); 
        LineChart lineChart = new LineChart("processing time chart");
        lineChart.createLineChart(lineChart.createChart(lineChart.createDataset(exactDistTime, approxDistTime)));
        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }
    
    private static void partIV() throws IOException
    {
  	int []index = new int[]{0,15,31,47};
  	Random random = new Random();
  	String [] dataSetHam = new String[5];
	String [] dataSetSpam= new String[5];
	String querySpam,queryHam;
	int [] detectionResult = new int[4];
	int hyperPlaneSize;
	List<EmailDistance> distanceList;
	String detectedLabel;
  	List<Email> emailList = new ArrayList<Email>();
  	 	
    	List<Email> queryList = new ArrayList<Email>();
    	List<Email> neighbourEmails = new ArrayList<Email>();
    	hyperPlaneSize = 64;
    	querySpam = "G:\\WISE\\BigData\\EmailSpam\\enron6\\spam";
    	queryHam = "G:\\WISE\\BigData\\EmailSpam\\enron6\\ham";
    	dataSetHam[0] = "G:\\WISE\\BigData\\EmailSpam\\enron1\\ham";
    	dataSetHam[1] = "G:\\WISE\\BigData\\EmailSpam\\enron2\\ham";
    	dataSetHam[2] = "G:\\WISE\\BigData\\EmailSpam\\enron3\\ham";
    	dataSetHam[3] = "G:\\WISE\\BigData\\EmailSpam\\enron4\\ham";
    	dataSetHam[4] = "G:\\WISE\\BigData\\EmailSpam\\enron5\\ham";
    	dataSetSpam[0] = "G:\\WISE\\BigData\\EmailSpam\\enron1\\spam";
    	dataSetSpam[1] = "G:\\WISE\\BigData\\EmailSpam\\enron2\\spam";
    	dataSetSpam[2] = "G:\\WISE\\BigData\\EmailSpam\\enron3\\spam";
    	dataSetSpam[3] = "G:\\WISE\\BigData\\EmailSpam\\enron4\\spam";
    	dataSetSpam[4] = "G:\\WISE\\BigData\\EmailSpam\\enron5\\spam";
    	
    	for(int i=0;i<5;i++)
    	{
    		emailList.addAll(emailReaderFromFile(dataSetHam[i],"ham",i+1, -1));
    		emailList.addAll(emailReaderFromFile(dataSetSpam[i],"spam",i+1, -1));
    	}
    	
    	System.out.println("Reading emails from 5 sets completed");
    	queryList = emailReaderFromFile(queryHam,"ham",6, -1);
    	queryList.addAll(emailReaderFromFile(querySpam,"spam",6, -1));
    	System.out.println("Reading query emails from set 6 completed");
    	queryList = computeHyperPlaneForEmails(queryList,hyperPlaneSize);
    	emailList = computeHyperPlaneForEmails(emailList,hyperPlaneSize);
    	System.out.println("HyperPlane computation for whole emails completed");
    	for(Email tempQueryEmail:queryList)
    	{
    		int randomIndex = random.nextInt(4);
    		int startIndex=index[randomIndex];
    		int endIndex = startIndex+15;
    		
    		neighbourEmails = closestEmail(tempQueryEmail, emailList, startIndex, endIndex);
    		if(neighbourEmails.size()==0)
    		{
    			detectedLabel = "ham";
    		}
    		else
    		{
    			distanceList = computeDistanceFromQueryEmail(tempQueryEmail, neighbourEmails);
    			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        		Collections.sort(distanceList);
        		detectedLabel = distanceList.get(0).getEmailType();
    			
    		}
    		if(tempQueryEmail.getEmailType()=="ham"&&detectedLabel=="ham")
    		{
    			detectionResult[0]+=1;
    			
    		}
    		else if(tempQueryEmail.getEmailType()=="spam"&&detectedLabel=="spam")
    		{
    			detectionResult[1]+=1;
    		}
    		else if(tempQueryEmail.getEmailType()=="ham"&&detectedLabel=="spam")
    		{
    			detectionResult[2]+=1;
    		}
    		else if(tempQueryEmail.getEmailType()=="spam"&&detectedLabel=="ham")
    		{
    			detectionResult[3]+=1;
    		}
    	}
    	BarChart_AWT chart = new BarChart_AWT("Hyperplane Errors");
    	chart.setChartSetting(BarChart_AWT.createDatasetEmailLabels(detectionResult),"Email labels Chart","Labels","values");
        chart.pack( );        
        RefineryUtilities.centerFrameOnScreen( chart );        
        chart.setVisible( true ); 
    }
    
	private static List<Email> emailReaderFromFile(String fileLocation,String emailType,int bucketIndex,int emailNos) throws IOException
    {
			
			File folder = new File(fileLocation);
    		File[] files = folder.listFiles();
    		emailNos = emailNos==-1?files.length:emailNos;
       	    Email tempEmail;
       	    List<Email> emailList = new ArrayList<Email>();
       	    //Email[] emailArray = new Email[100];
    	   for (int i=0;(i<files.length)&&(i<emailNos);i++)
    	   {
    		  String emailFromFile = fileReader(files[i].getAbsolutePath());
    		  tempEmail = convertWordsToEmail(emailFromFile);
    		  tempEmail.setEmailType(emailType);
    		  tempEmail.setBucketIndex(bucketIndex);
    		  emailList.add(tempEmail);
    	    }
    	   return emailList;
    	
    }
	private static List<EmailDistance> computeDistanceFromQueryEmail(Email queryEmail, List<Email> emailList)
	{
		List<EmailDistance> distanceList = new ArrayList<EmailDistance>();
		for(int i=0;i<emailList.size();i++)
		{
			EmailDistance emailDistance = new EmailDistance();
			emailDistance.setDistance(cosineDistanceMuliSet(queryEmail, emailList.get(i)));
			emailDistance.setIndex(i);
			emailDistance.setEmailType(emailList.get(i).getEmailType());
			distanceList.add(emailDistance);
		}
		return distanceList;
			
	}
	private static void setHyperPlanForEmail(int hyperPlaneIndex,Multiset<String> emailMessage)
	{
		Double dotProduct=0.0;
		HashFunction hf = Hashing.murmur3_128(hashSeeds[hyperPlaneIndex]);
		for(String msg: emailMessage)
		{
			dotProduct = dotProduct + emailMessage.count(msg)*hf.hashString(msg,StandardCharsets.UTF_8).asInt();
		}
	}
  
    private static void hyperPlaneHashSeeds()
    {
    	for(int i=0;i<64;i++)
    	{
    		hashSeeds[i] =i;
    	}
    }
    private static List<Email> closestEmail(Email queryEmail,List<Email> emailList,int startIndex,int hyperPlaneSize) throws IOException
    {
    	int tempCount=0;
    	List<Email> closestEmails = new ArrayList<Email>();
    	for(int j=0;j<emailList.size();j++)
    	{ 
    		
    	    tempCount =0;	
    		for(int k=startIndex;k<hyperPlaneSize;k++)
    		{
    			
    			if(queryEmail.getHyperPlaneValue(k)==emailList.get(j).getHyperPlaneValue(k))
    			{
    				tempCount++;
    			}
    		}
    		if(tempCount ==hyperPlaneSize)
    		{
    			closestEmails.add(emailList.get(j));
    		}
    		
    	}
    	
    	return closestEmails;
    }
    private static String fileReader(String fileName) throws IOException
    {
    	String fileContent="",line;
    	BufferedReader br = null;
    	try{
    	br = new BufferedReader(new FileReader(fileName));
		while (((line = br.readLine()) != null)) {
			fileContent = fileContent + line;
		}
    	}catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		br.close();
    	}
    	return fileContent;
    }
    private static Double vectorNorm(Email vector)
    {
    	Double norm = 0.0;
    	for(String wordIndex: vector.getEmailSet())
    	{
    		norm = norm + Math.pow(vector.getEmailSet().count(wordIndex),2);
    	}
    	return Math.sqrt(norm);
    }
    private static List<Email> computeHyperPlaneForEmails(List<Email> emailList, int hyperPlaneSize)
    {
    	List<Email> resultList = new ArrayList<Email>();
    	int []hyperPlaneSigns = new int[hyperPlaneSize];
    	int index, hyperValue;
    	for(Email tempEmail:emailList)
    	{
    	HashFunction hf = Hashing.murmur3_128(100);//seed 100 for messages
    	for(int i=0;i<hyperPlaneSize;i++)
    		{
    		hyperPlaneSigns[i] =0;
    		HashFunction hyperPlanehash = Hashing.murmur3_128(hashSeeds[i]);
    			for(String word:tempEmail.getEmailSet())
    	    	{
	    			index = hf.hashString(word,StandardCharsets.UTF_8).asInt(); 
	    			hyperValue = hyperPlanehash.hashString(word,StandardCharsets.UTF_8).asInt();
	    			hyperPlaneSigns[i] = hyperPlaneSigns[i] + index*hyperValue;
    	    	}
    			hyperPlaneSigns[i] =(hyperPlaneSigns[i]>=0)?1:-1;
    			tempEmail.setHyperPlaneValue(i, hyperPlaneSigns[i]);
    			//System.out.println("hyperPlane " + i +"  hyperplane value:"+hyperPlaneSigns[i]);
    	  	}
    	resultList.add(tempEmail);
    	}	
    	return resultList;
    }
    private static Email convertWordsToEmail(String email)
    {
    	Multiset<String> emailSet = HashMultiset.create();
    	Email tempEmail = new Email();
    	email = email.substring(8);
    	String[] words = email.split(" ");
    	for(String word: words)
    	{
    		emailSet.add(word);
    	}
    	tempEmail.setEmailSet(emailSet);
    	return tempEmail;
    }
  
    private static Double dotProduct(Email a, Email b)
    {
    	Double dotProd=0.0;
    	Multiset<String> smallSet = (a.getEmailSet().size() <= b.getEmailSet().size())?a.getEmailSet():b.getEmailSet();
    	Multiset<String> bigSet = (b.getEmailSet().size() >=a.getEmailSet().size())?b.getEmailSet():a.getEmailSet();
    	for(String sWord: smallSet )
    	{
    		dotProd = dotProd + smallSet.count(sWord)*bigSet.count(sWord);
    			    		
    	}
    	return dotProd;
    }
    private static Double cosineDistanceMuliSet(Email a, Email b)
    {
    	Double distance,aNorm,bNorm,dotProd;
    	dotProd = Precision.round(dotProduct(a,b),4);
    	aNorm = vectorNorm(a);
    	bNorm = vectorNorm(b);
    	if(aNorm==0 || bNorm==0)
    	{
    		distance = Math.PI/2;
    	}
    	else
    	{
    		distance =Precision.round(dotProd/(aNorm*bNorm),4);
    		distance = Math.acos(distance);
    	}
    	return distance;
    }
}
