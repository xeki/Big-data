package Bigdata.HyperLogLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		int b = 14;
		
		System.out.println("Distinct Value " +hyperLogLog(b,100,10000));

	}
	private static double hyperLogLog(int b,int size,int hashSeed) throws IOException
	{
		InputStream is = null;
    	BufferedReader bf = null;
		int m = (int)Math.pow(2, b);
		Random rnd = new Random();
		int []zeros = new int[m];
		//System.out.println("bucket size m: "+m );
		//Scanner scanner = new Scanner(System.in);
		int zeroCount,mIndex;
		double zeroSum=0;
		long hashedVal;
		String inputBits,mString,line;
		double alpha,dValue=-1.0;
		HashFunction hf;
    	hf = Hashing.murmur3_128(hashSeed);
    	alpha = setAlphaValue(b);
    	is = System.in;
		bf = new BufferedReader(new InputStreamReader(is));
		//for(int i=0;i<size;i++)
		while((line = bf.readLine())!=null) 
		{
			//inputData = scanner.nextLine();
			//inputData = Integer.toString(rnd.nextInt(100000));
			//hashedVal = hf.hashString(inputData, StandardCharsets.UTF_8).asLong();
			if(rnd.nextDouble()>=0.5 && rnd.nextDouble()<=0.6)
			{
				hashedVal = hf.hashString(line, StandardCharsets.UTF_8).asLong();
				inputBits = Long.toBinaryString(hashedVal);
				mString = inputBits.substring(inputBits.length()-b);
				mIndex = decodeBinaryString(mString);
				//System.out.println("mindex : "+mIndex );
				inputBits = inputBits.substring(0,inputBits.length()-b-1);
				zeroCount = countZeros(inputBits);
				zeroCount = Math.max(zeroCount, zeros[mIndex]);
				//System.out.println("zeroCount : " + zeroCount );
				zeros[mIndex] = zeroCount;
			}
			
		}
		//scanner.close();
		for(int i=0;i<m;i++)
		{
			zeroSum += Math.pow(2,-1*zeros[i]);
		}
		//System.out.println("zeroSum: "+ zeroSum+ " alpha: "+alpha);
		dValue = (double) alpha*Math.pow(m, 2)*1/zeroSum;
		//System.out.println("dvalue : " + dValue);
		if(dValue <2.5*m)
		{
			int v = zeroRegisters(zeros);
			//System.out.println("Number of zeros : " + v);
			if(v!=0)
			{
				dValue = m*Math.log((m/v));
			}
		}
		if(dValue > (1/30)*Math.pow(2, 32))
		{
			//System.out.println("dvalue : " + dValue);
			dValue = -1*Math.pow(2,32)*Math.log(1-dValue/Math.pow(2,32));
		}
		dValue = Math.round(dValue);
		return dValue;
	}
	private static int zeroRegisters(int[] registers)
	{
		int count = 0;
		for(int i=0;i<registers.length;i++)
		{
			if(registers[i]==0)
			{
				count++;
			}
		}
		return count;
	}
	private static int countZeros(String str)
	{
		int count=1;
		boolean loop = true;
		char []inputArray = str.toCharArray();
		for(int i=inputArray.length-1;(i>=0)&&loop;i--)
		{
			if(inputArray[i]=='0')
			{
				count++;
			}
			else
			{
				loop = false;
			}
		}
		return count;
		
	}
	private static int decodeBinaryString(String str)
	{
		int result = 0;
		char []inputArray = str.toCharArray();
		for(int i=0;i<inputArray.length;i++)
		{
			result += Integer.parseInt(String.valueOf(inputArray[i]))*(int)Math.pow(2,inputArray.length-i-1);
		}
		return result;
	}
	private static double setAlphaValue(int b)
	{
		double alpha = 0.0;
		int m = (int)Math.pow(2, b);
		if(m==16)
		{
			alpha = 0.673;
		}
		else if(m==32)
		{
			alpha = 0.697;
		}
		else if(m==64)
		{
			alpha = 0.709;
		}
		else
		{
			alpha = 0.7213/(1+1.079/m);
		}
		return alpha;
	}
}

