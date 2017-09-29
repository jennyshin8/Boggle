import java.util.ArrayList;

/**
 * Created by jh on 9/23/17.
 */
public class Controller
{
  private static Model model;
  private static View view;

  private static int score = 0;
  private static ArrayList<String> wordList = new ArrayList<String>();

  Controller(int i, String[] args)
  {
    model = new Model(i);

    view = new View(i, model.getBoggleArr());
    view.setView(args);

    wordList.add("");
  }

  public static boolean findWord(String s)
  {
    return model.findWord(s);
  }

  public static void setScore(String s)
  {
    score += s.length() - 2;
  }

  public static int getScore()
  {
    return score;
  }


  public static void addWordList(String s)
  {
    wordList.add(s);
  }

  public static ArrayList<String> getWordList()
  {
    return wordList;
  }
}
