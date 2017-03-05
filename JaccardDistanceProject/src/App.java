import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//App.readCSV("G:\\WISE\\BigData\\words.txt", "");
		App.formatArticlesFile("G:\\WISE\\BigData\\wikipedia_utf8_filtered_20pageviews.csv","G:\\WISE\\BigData\\formattedarticle.txt");
		// App.formatFile("G:\\WISE\\BigData\\stemmertest.txt",
		// "G:\\WISE\\BigData\\formatedfile.txt");
	}

	private static void textWriter(String fileName, String lineToWrite)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName, true), "utf-8"))) {
			writer.write(lineToWrite + "\n");
			writer.close();
			// System.out.println("");
		}
	}

	private static void readCSV(String fileInputName, String fileOutName) {
		String csvFile = fileInputName;
		fileOutName = "G:\\WISE\\BigData\\resizedwords.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = " ";
		int lineNum = 0, writtenLines = 0;
		boolean startWrite = false, checkFreq = true;
		boolean endLoop = false;
		try {

			Map<String, String> maps = new HashMap<String, String>();
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileOutName, true), "utf-8"));

			br = new BufferedReader(new FileReader(csvFile));
			while (((line = br.readLine()) != null) && !endLoop) {
				lineNum++;
				// use comma as separator
				// String[] country = line.split(cvsSplitBy);
				String tempLine = line;
				String[] word = tempLine.trim().split(cvsSplitBy);
				// maps.put(country[4], country[5]);
				if (checkFreq && Integer.parseInt(word[0]) >= 5) {
					startWrite = true;
					checkFreq = false;
					System.out.println("Line Number: " + lineNum);
				}
				if (startWrite) {
					// textWriter("G:\\WISE\\BigData\\resizedwords.txt", line);
					writer.write(line + "\n");
					writtenLines++;
				}
				if (lineNum >= 2908064) {
					endLoop = true;
					System.out.println("line number: " + lineNum);
				}
			}
			System.out.println("Written Lines " + writtenLines);
			writer.close();

		} catch (FileNotFoundException fnfex) {
			fnfex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

	}

	private static HashSet<String> readFrequentWords(String fileName) {
		HashSet<String> set = new HashSet<String>();
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while (((line = br.readLine()) != null)) {
				String[] temp = line.trim().split(" ");
				set.add(temp[1].trim());
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return set;
	}
 private static double jaccardDistance(HashSet<String> firstSet, HashSet<String> secondSet )
 {
 double jaccDist = 0.00;
 int n1,n2, union, intersect;
 n1 = firstSet.size();
 n2 = secondSet.size();
 firstSet.retainAll(secondSet);
 intersect = firstSet.size();
 union = n1 + n2 - intersect;
 jaccDist = 1 - (double)intersect/union;
 
 return jaccDist;
 }
	private static void formatArticlesFile(String sourceFileName,
			String destinationFileName) {
		String csvFile = sourceFileName;
		Stemmer stemmer = new Stemmer();
		BufferedReader br = null;
		String line = "";
		int lineCount = 0;
		HashSet<String> frequentWordsSet = readFrequentWords("G:\\WISE\\BigData\\resizedwords.txt");
		try {

			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destinationFileName, true), "utf-8"));

			br = new BufferedReader(new FileReader(csvFile));
			while (((line = br.readLine()) != null)&&(lineCount < 50000)) {
				// use comma as separator
				// String[] country = line.split(cvsSplitBy);
				lineCount++;
				String[] tempLine = line.split(",");
				 String tempStr =  tempLine[0].replaceAll(
							"[^a-zA-Z0-9-]", "").toLowerCase();
				 writer.write(tempStr + "\n");
				for (int i = 1; i < tempLine.length; i++) {
					String[] tempWord = tempLine[i].split(" ");
					for (String SplitedWords : tempWord) {
						SplitedWords = SplitedWords.replaceAll(
								"[^a-zA-Z0-9-]", "").toLowerCase();
						if (frequentWordsSet.contains(SplitedWords)) {
							char [] charWords = SplitedWords.toCharArray();
							stemmer.add(charWords, charWords.length);
							stemmer.stem(); 
							String stemmedWord = stemmer.toString();
							writer.write(stemmedWord + "\n");
						}
					}
				}
				System.out.println(lineCount);
				// line = line.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
				// String[] word = tempLine.trim().split(cvsSplitBy);
				// maps.put(country[4], country[5]);
				// textWriter("G:\\WISE\\BigData\\resizedwords.txt", line);
				// writer.write(line + "\n");
			}

			writer.close();

		} catch (FileNotFoundException fnfex) {
			fnfex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

	}
}
