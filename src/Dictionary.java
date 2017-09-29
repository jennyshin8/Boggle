import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jh on 9/23/17.
 */
public class Dictionary
{
  // Read .txt file save it to ArrayList<String> wordList
  protected ArrayList<String> dictionary = new ArrayList<String>();


  protected Dictionary()
  {
    BufferedReader reader;

    try
    {
      reader = new BufferedReader(new FileReader("src/resource/OpenEnglishWordList.txt"));
      String line = reader.readLine();

      while (line != null)
      {
        dictionary.add(line);
        line = reader.readLine();
      }
      reader.close();
    }
    catch (IOException e)
    {
      System.out.println("IO Exception: " + e.getMessage());
      System.exit(0);
    }
  }

  protected boolean findWord(String findingWord)
  {
    // Read input to find a word
//    System.out.println("Enter your word: ");
//    Scanner scanner = new Scanner(System.in);
//    String findingWord = scanner.nextLine();


    boolean found = false, noValue = false;
    int start = 0, pivot = (dictionary.size() / 2), end = dictionary.size() - 1, idx = 0;

    if (dictionary.get(pivot).length() < findingWord.length())
    {
      idx = dictionary.get(pivot).length();
    }
    else
    {
      idx = findingWord.length();
    }


    while (!found)
    {
      System.out.println(" -> " + dictionary.get(pivot) + " , pivot : " + pivot);

      for (int i = 0; i < idx; i++)
      {
        if(start == pivot || end == pivot)
        {
          noValue = true;
          break;
        }

        if (dictionary.get(pivot).charAt(i) == findingWord.charAt(i))
        {
          if (i == idx - 1 & idx == dictionary.get(pivot).length() & idx == findingWord.length())
          {
            System.out.println("****************************");
            System.out.println("wordList (" + (pivot+1)+ ") " + dictionary.get(pivot));
            found = true;
            break;
          }
          else continue;
        }
        else if (dictionary.get(pivot).charAt(i) < findingWord.charAt(i)) start = pivot;
        else if (dictionary.get(pivot).charAt(i) > findingWord.charAt(i)) end = pivot;

        pivot = (start + end) / 2;
        i = -1;

        if (dictionary.get(pivot).length() < findingWord.length()) idx = dictionary.get(pivot).length();
        else idx = findingWord.length();
      }

      // bad vs badass
      if (dictionary.get(pivot).length() != findingWord.length())
      {
        if (idx == dictionary.get(pivot).length()) start = pivot;
        else if (idx == findingWord.length()) end = pivot;

        pivot = (start + end) / 2;

        if (dictionary.get(pivot).length() < findingWord.length()) idx = dictionary.get(pivot).length();
        else idx = findingWord.length();
      }

      // if there is no word that user is looking for,
      if (noValue)
      {
        System.out.println("Sorry but no " + findingWord + " in dictionary...\n");
        break;
      }
    }

    return found;
  }

}
