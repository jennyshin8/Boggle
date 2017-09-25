import java.util.Scanner;

public class Main
{

  public static void main(String[] args)
  {
//    int num;
//
//    while (true)
//    {
//      System.out.println("Which board are you gonna pick?");
//      System.out.println("[1] 4x4");
//      System.out.println("[2] 5x5");
//
//      Scanner scanner = new Scanner(System.in);
//      String input = scanner.nextLine();
//      num = Integer.parseInt(input);
//
//      if (num == 1) num = 4;
//      else if (num == 2) num = 5;
//      else
//      {
//        System.out.println("********** (INVALID INPUT) Please input 1 or 2  **********");
//        continue;
//      }
//      break;
//    }
//
//    // (num x num) Boggle array is created
//    //new Controller(num, args);

    new Controller(5, args);
  }
}


