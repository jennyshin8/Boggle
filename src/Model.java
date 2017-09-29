import java.util.Random;

/**
 * Created by jh on 9/23/17.
 */
public class Model extends Dictionary
{
  //private ArrayList<String> dictionary = super.dictionary;
  private String[][] boggleArr;
  private String[] manual = {"A", "B", "C", "D", "E", "F", "G", "H",
                             "I", "J", "K", "L", "M", "N", "O", "P",
                             "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
  private int mi;

  Model(int i)
  {
    boggleArr = new String[i][i];
    this.mi = i;
    this.shuffleBoggleArr(i);
  }

  public String[][] getBoggleArr()
  {
    return boggleArr;
  }

  public void shuffleBoggleArr(int input)
  {
    String tmp = "";

    int limit = input * input;

    for (int i = 0; i < limit; i++)
    {
      while (true)
      {
        Random r = new Random();
        int rand = r.nextInt(26);

        if (tmp.contains(this.manual[rand])) continue;
        else tmp += this.manual[rand];

        break;
      }
    }

    for (int r = 0; r < input; r++)
    {
      for (int c = 0; c < input; c++)
      {
        this.boggleArr[r][c] = tmp.substring((r * 4) + c, (r * 4) + c + 1);
      }
    }

  }


}
