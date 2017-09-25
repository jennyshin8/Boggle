/**
 * Created by jh on 9/23/17.
 */
public class Controller
{
  private static Model model;
  private static View view;
  public static String[][] playBoard;
  public static String draggedStr;
  public static int score = 0;

  Controller(int i, String[] args)
  {
    model = new Model(i);
    playBoard = model.getBoggleArr();

    view = new View(i);
    view.setView(args);
  }

  public static void startDraggedStr(String s) { draggedStr = s; }
  public static void putDraggedStr(String s)
  {
    if(!draggedStr.endsWith(s))
    {
      draggedStr += s;
    }
  }

  public static void checkWord(String s)
  {
    model.findWord(s);
  }

  public static void setScore(int i)
  {
    score += i;
  }

}
