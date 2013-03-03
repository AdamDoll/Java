   import java.util.*;
   import java.io.FileNotFoundException;


/***************************************************************************
* this is a class that allows you to search a document for duplicate lines
*
* @author Adam Doll
* @version V1 - 4-26-12
*  
***************************************************************************/
   public class DuplicateSearch
   {
   // the start position for the plagiarized text in document 1
      private static int startDOne;
   	// the start position for the plagiarized text in document 1
      private static int startDTwo;
   
      public static void main(String args[]) //throws FileNotFoundException
      {
         String fileOne;
         String fileTwo;
         String longestString;
         int minLength;
         String[] splitLongestString;
         DocumentReader read = new DocumentReader();
         ArrayList<String> documentOne = new ArrayList<String>();
         ArrayList<String> documentTwo = new ArrayList<String>();
         longestString = "";
      
         fileOne = args[0];
         fileTwo = args[1];
         minLength = Integer.parseInt(args[2]);
         try
         {
            documentOne = read.readDocument(fileOne);
            documentTwo = read.readDocument(fileTwo);
         }
            catch(FileNotFoundException fnfe)
            {
               System.out.println("bad files");
            }
      
      
         longestString = Search(documentOne, toHashMap(documentTwo), documentTwo);
       
         splitLongestString = longestString.split(" ");
         if(splitLongestString.length >= minLength)
         {
            System.out.println("\nMatch length: " + splitLongestString.length);
            System.out.println("Position in " + fileOne + ": " + startDOne);
            System.out.println("Position in " + fileTwo + ": " + startDTwo + "\n");
            System.out.println("Matching words:");
            System.out.println(longestString);
         }
         else
            System.out.println("\nNo matches found.");
      }
      
   	/***************************************************************************
   * It searches for the longest string
   *
   * @param dOne arraylist of strings
   * @param dTwo hashmap of string keys and an arraylist of intigers
   * @param arayDTwo arraylist of strings
   * @return string of longest plagiarized
   ***************************************************************************/
   	
      private static String Search(ArrayList<String> dOne, 
      										HashMap<String, ArrayList<Integer>> dTwo,
      											 ArrayList<String> arrayDTwo)
      {
         String duplicate;
         String longest;
         duplicate ="";
         longest = "";
      
         ArrayList<Integer> working = new ArrayList<Integer>();
      
         for(int ii = 0; ii < dOne.size(); ii++)
         {
         
            if(dTwo.containsKey(dOne.get(ii)))
            {
               working = dTwo.get(dOne.get(ii));
            }
            
            for(int intList = 0; intList < working.size(); intList++)
            {
               duplicate = positions(dOne, arrayDTwo, ii, working.get(intList));
            
               if(duplicate.length() > longest.length())
               {
                  longest = duplicate;
                  startDOne = ii;
                  startDTwo = working.get(intList);
               }
            }
         }
         return longest;
      }
   		
   		
   		/***************************************************************************
   * searches for duplicate words between documents
   *
   * @param dOne arraylist of strings
   * @param arrayDTwo arraylist of strings
   * @param positionOne int of where to start the search in document 1
   * @param positionTwo int of where to start the search in document 2
   * @return string of longest plagiarized words
***************************************************************************/
   		
   		
      private static String positions(ArrayList<String> dOne, 
      											ArrayList<String> arrayDTwo, int positionOne,
      										int positionTwo)
      {
         String coppied;
         int starter;
         boolean keepLooping;
         coppied = "";
         keepLooping = true;
         starter = positionOne;
      	
         while(keepLooping && dOne.get(positionOne).equals(arrayDTwo.get(positionTwo)))
         {
            if(starter == positionOne)
               coppied = dOne.get(positionOne);
            else
               coppied = coppied + " " + dOne.get(positionOne);
         
            if((dOne.size() == positionOne + 1) || (arrayDTwo.size() == positionTwo + 1))
               keepLooping = false;
               
            positionOne++;
            positionTwo++;
         }
         return coppied;
      }
   		
   		
   	/***************************************************************************
   * creates an hashmap
   *
   * @param document an arraylist of strings
   * @return a hashmap with the strings as keys and the pisitions in an 
   *			arraylist associated with those strings.
   ***************************************************************************/	
   			
   	
      private static HashMap<String, ArrayList<Integer>> 
      							toHashMap(ArrayList<String> document)
      {
         HashMap<String,ArrayList<Integer>> newDocument = 
            new HashMap<String,ArrayList<Integer>>();
         ArrayList<Integer> list = new ArrayList<Integer>();
      	
         for(int ii = 0; ii < document.size(); ii++)
         {
            if (!(newDocument.containsKey(document.get(ii))))
            {
               list.add(ii);
               newDocument.put(document.get(ii), list);
            }
            else 
               newDocument.get(document.get(ii)).add(ii);
                     
            list = new ArrayList<Integer>();
         }
         return newDocument;
      }
   }