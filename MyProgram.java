import java.util.*;  
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;

class HelloWorld
{
	// Creates an array from a string
	public static String[] transformToArray(String arr) 
	{
		// Count the number of spaces in the string
		int count = arr.length() - arr.replace(" ", "").length();
		
		int y = 0;
		String[] newArray = new String[count+1];
		
		// Make the string empty of "null" values
		int x = 0;
		for (String num: newArray)
		{
		    newArray[x] = "";
			x++;
		}

        // Create the array
		for (int i = 0; i < arr.length(); i++)
		{
			if (arr.charAt(i) == ' ')
			{
				y++;
				continue;
			}
			
			newArray[y] = newArray[y] + arr.charAt(i);
		}
		return newArray;
    }
	
	// This function counts the number of specific words in a given string
	private static int counter(String str, String wordsToDetect)
    {
		int tempCharCount = 0;
		int numberOfMatches = 0;
		for (int i = 0; i < str.length(); i++)
		{
			if (tempCharCount == (wordsToDetect.length()-1))
			{
				tempCharCount = 0;
				numberOfMatches++;
				continue;
			}
			if (str.charAt(i) == wordsToDetect.charAt(tempCharCount))
			{
				tempCharCount++;	
			}
		}
		return numberOfMatches;
    }
	
	 private static String getFileContent(String file_path)
    {
 
        // Declaring an empty string
        String str = "";
        // Try block to check for exceptions
        try {
            // Reading all bytes form file and
            // storing that in the string
            str = new String(
                Files.readAllBytes(Paths.get(file_path)));
        }
 
        // Catch block to handle the exceptions
        catch (IOException e) {
            // Print the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
 
        return str;
    }
 
	
	// Main funcion
	public static void main(String args[])
	{
		// Take input from the user + transform the string to an array
		Scanner sc = new Scanner(System.in); 
		System.out.println();
		System.out.println("Please enter an array of strings: (separate each word with a space)");
		String arrayOfStrings = sc.nextLine();
		System.out.println("Please enter a folder path:");
		String folderPath = sc.nextLine();
		
		// Transform the string into an array
		String[] newArrayOfStrings = transformToArray(arrayOfStrings);
		
		// Get the log file names and paths
	    int count = arrayOfStrings.length() - arrayOfStrings.replace(" ", "").length();
		List filesPaths = new ArrayList(); // a list of all the paths
		List filesNames = new ArrayList(); // a list of all the file names

		File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
            
			if (!((listOfFiles[i].getName()).contains(".txt")) && !((listOfFiles[i].getName()).contains(".log")))
			{
		    continue;
			}
			else {
			filesPaths.add(listOfFiles[i]);
			filesNames.add(listOfFiles[i].getName());
          } 
          }
}

        // Count the number of times the string appears in the logs
		List finalList_tempPath = new ArrayList(); // final list of pathes
		List finalList_tempFile = new ArrayList(); // final list of files
		List finalList_temptempCount = new ArrayList(); // final list of counts

		for (int x = 0; x < filesPaths.size(); x++)
		{
			int tempCount = 0;
			for (int y = 0; y < newArrayOfStrings.length; y++)
			{
				String temp = (filesPaths.get(x)).toString(); // transform into string
				String tempString = getFileContent(temp);
				tempString = tempString.replace("\n", "").replace("\r", ""); // remove new lines
				tempCount = counter(tempString, newArrayOfStrings[y]);
				// Convert elements and int into Strings
				String tempPath = filesPaths.get(x).toString();
				String tempFile = newArrayOfStrings[y].toString();
				String temptempCount = String.valueOf(tempCount);
				finalList_tempPath.add(temptempCount + tempPath);
				finalList_tempFile.add(temptempCount + tempFile);
				finalList_temptempCount.add(temptempCount);
			}
		}
		
        // Sort the 3 lists
		Collections.sort(finalList_tempPath);
		Collections.reverse(finalList_tempPath);
		Collections.sort(finalList_tempFile);
		Collections.reverse(finalList_tempFile);
		Collections.sort(finalList_temptempCount);
		Collections.reverse(finalList_temptempCount);

		// Remove the first digit from the lists finalList_tempPath and finalList_tempFile
		for (int x = 0; x < finalList_tempPath.size(); x++)
		{
		String tempStr1 = finalList_tempPath.get(x).toString();
		tempStr1 = tempStr1.substring(1);
		finalList_tempPath.set(x, tempStr1);
		tempStr1 = finalList_tempFile.get(x).toString();
		tempStr1 = tempStr1.substring(1);
		finalList_tempFile.set(x, tempStr1);
		}
		
		// Make a new list from the final list, that includes "a href" html tags
		List finalListWithLinks = new ArrayList(); // final list with html
		for(int x = 0; x < finalList_tempPath.size(); x++)
		{
			String tempString = (finalList_tempPath.get(x).toString());
			tempString = tempString.replace(folderPath+"\\", "");
			String html = "<a href=\"" + (finalList_tempPath.get(x).toString()) + "\">" + tempString + "</a>";
			finalListWithLinks.add(html);
		}
		
		// Create html report
		try {
          FileWriter myWriter = new FileWriter("report.html");
          myWriter.write("<table border = \"1\">\n");
	      myWriter.write("<thead>\n");
	      myWriter.write("<tr><th>File Name\n");
	      myWriter.write("</th><th>String  </th><th style=\"text-align: right;\">  Count</th></tr>\n");
	      myWriter.write("</thead>\n");
	      myWriter.write("<tbody>\n");
		  for (int x = 0; x < finalList_tempPath.size(); x++)
		  {
			String tempURL = (finalListWithLinks.get(x).toString());
			String tempBUG = (finalList_tempFile.get(x).toString());
			String tempNUM = (finalList_temptempCount.get(x).toString());
			myWriter.write("<tr><td>" + tempURL + "</td><td>" + tempBUG + "</td><td style=\"text-align: right;\">" + tempNUM + "</td></tr>\n");
		  }
		  myWriter.write("</tbody>\n");
		  myWriter.write("</table>\n");
          myWriter.close();
          System.out.println("Done!");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
    }
		}
}