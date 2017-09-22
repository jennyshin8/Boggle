import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application
{

  private static final int WINDOW_WIDTH = 1300;
  private static final int WINDOW_HEIGHT = 500;
  private static VBox vbox = new VBox();
  private static HBox hbox = new HBox();



  @Override
  public void start(Stage stage)
  {
    Scene scene = new Scene(new Group());
    stage.setTitle("Dominoes_JShin");
    stage.setWidth(WINDOW_WIDTH);
    stage.setHeight(WINDOW_HEIGHT);

    Button btn1 = new Button();
    btn1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resource/a.png"))));
    btn1.setPadding(new Insets(2,2,2,2));
    btn1.setOnMouseDragged(this::handleClick);
    btn1.setOnMouseClicked(this::handleDragged);


    hbox.setPrefWidth(WINDOW_WIDTH);
    hbox.setPrefHeight(50);
    hbox.setAlignment(Pos.CENTER);
    hbox.setTranslateY(150);
    hbox.getChildren().add(btn1);

    vbox.getChildren().addAll(hbox);

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();
  }

  public void handleClick(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
    {

    }
  }


  public void handleDragged(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
    {

    }
  }


  public static void main(String[] args)
  {
    //launch(args);

    // Read .txt file save it to ArrayList<String> wordList
    BufferedReader reader;
    ArrayList<String> wordList = new ArrayList<String>();


    try
    {
      reader = new BufferedReader(new FileReader("src/OpenEnglishWordList.txt"));
      String line = reader.readLine();

      while (line != null)
      {
        wordList.add(line);
        line = reader.readLine();
      }
      reader.close();
    }
    catch (IOException e)
    {
      System.out.println("IO Exception: " + e.getMessage());
    }

    // Read input to find a word
    System.out.println("Enter your word: ");
    Scanner scanner = new Scanner(System.in);
    String findingWord = scanner.nextLine();


    boolean found = false, noValue = false;
    int start = 0, pivot = (wordList.size() / 2), end = wordList.size() - 1, idx = 0;

    if (wordList.get(pivot).length() < findingWord.length())
    {
      idx = wordList.get(pivot).length();
    }
    else
    {
      idx = findingWord.length();
    }


    while (!found)
    {
      System.out.println("w : " + wordList.get(pivot) + " , pivot : " + pivot);


      for (int i = 0; i < idx; i++)
      {

        if(start == pivot || end == pivot)
        {
          noValue = true;
          break;
        }

        if (wordList.get(pivot).charAt(i) == findingWord.charAt(i))
        {
          if (i == idx - 1 & idx == wordList.get(pivot).length() & idx == findingWord.length())
          {
            System.out.println("****************************");
            System.out.println("wordList (" + (pivot+1)+ ") " + wordList.get(pivot));
            found = true;
            break;
          }
          else continue;
        }
        else if (wordList.get(pivot).charAt(i) < findingWord.charAt(i)) start = pivot;
        else if (wordList.get(pivot).charAt(i) > findingWord.charAt(i)) end = pivot;

        pivot = (start + end) / 2;
        i = -1;

        if (wordList.get(pivot).length() < findingWord.length()) idx = wordList.get(pivot).length();
        else idx = findingWord.length();
      }

      // bad vs badass
      if (wordList.get(pivot).length() != findingWord.length())
      {
        if (idx == wordList.get(pivot).length()) start = pivot;
        else if (idx == findingWord.length()) end = pivot;

        pivot = (start + end) / 2;

        if (wordList.get(pivot).length() < findingWord.length()) idx = wordList.get(pivot).length();
        else idx = findingWord.length();
      }

      // if there is no word that user is looking for,
      if (noValue)
      {
        System.out.println("There is no " + findingWord + " in dictionary..");
        break;
      }
    }

  }


}

