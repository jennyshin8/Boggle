import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by jh on 9/23/17.
 */
public class View extends Application
{
  private static GameTimer gameTimer;
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 500;
  private static Scene scene = new Scene(new Group());

  private static VBox vbox = new VBox();
  ///////////// Header - Score & Time /////////////
  private static HBox header = new HBox();
  private static HBox header2 = new HBox();

  private static Text timeL = new Text();
  public static Text timeV = new Text();
  private static Text scoreL = new Text();
  private static Text scoreV = new Text();

  //////////// Board  - (n x n) Board ////////////
  private static HBox r1 = new HBox();
  private static HBox r2 = new HBox();
  private static HBox r3 = new HBox();
  private static HBox r4 = new HBox();
  private static HBox r5 = new HBox();

  //////// Footer - Not acceptable words /////////
  private static HBox footer = new HBox();
  private static HBox footer2 = new HBox();

  private static Text footerDesc = new Text();
  private static Text notAllowed = new Text();

  private static int num;
  private static String[][] playboard;

  private static Stack<Node> stacked = new Stack<>();
  private static String draggedWord = "";
  private static Node startNode;



  public View()
  {
  }

  public View(int n, String[][] boggleArr)
  {
    this.num = n;
    this.playboard = boggleArr;
  }


  @Override
  public void init() throws Exception
  {
    super.init();
  }


  @Override
  public void stop() {
    System.out.println("Stop");
  }

  @Override
  public void start(Stage stage)
  {
    stage.setTitle("Boggle_JShin");
    stage.setWidth(WINDOW_WIDTH);
    stage.setHeight(WINDOW_HEIGHT);

    setHeader();
    setBoard(this.num);
    setFooter();

    alignVBox();

    timeV.setWrappingWidth(100);
    scoreV.setTextAlignment(TextAlignment.RIGHT);
    scoreV.setWrappingWidth(100);

    gameTimer = new GameTimer();
    gameTimer.start();

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();

  }


  private void setHeader()
  {
    timeL.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    timeL.setTextAlignment(TextAlignment.LEFT);
    timeL.setText("Time");

    scoreL.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    scoreL.setTextAlignment(TextAlignment.LEFT);
    scoreL.setText("Score");

    timeV.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    timeV.setTextAlignment(TextAlignment.LEFT);
    timeV.prefWidth(20);
    timeV.setText("");

    scoreV.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    scoreV.setTextAlignment(TextAlignment.RIGHT);
    scoreV.setText("0");

    header.setAlignment(Pos.BASELINE_LEFT);
    header.setPrefWidth(WINDOW_WIDTH);
    header.getChildren().add(timeL);
    header.setSpacing(370);
    header.getChildren().add(scoreL);

    header2.setAlignment(Pos.BASELINE_LEFT);
    header2.setPrefWidth(WINDOW_WIDTH);
    header2.getChildren().addAll(timeV);
    header2.setSpacing(271);
    header2.getChildren().add(scoreV);
  }

  private void setBoard(int n)
  {
    HBox[] tmp = {r1, r2, r3, r4, r5};

    for (int r = 0; r < n; r++)
    {
      for (int c = 0; c < n; c++)
      {
        Button btn = new Button();
        btn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resource/" + playboard[r][c] + ".png"))));
        btn.setId(playboard[r][c]);
        btn.setPadding(new Insets(2, 2, 2, 2));
        //btn.setOnMouseClicked(this::handleClick);

        btn.setOnDragDetected(this::handleDetected);
        btn.setOnMouseDragOver(this::handleDragOver);
        btn.setOnMouseDragReleased(this::handleReleased);

        tmp[r].getChildren().add(btn);
      }
    }

    for (int i = 0; i < n; i++)
    {
      tmp[i].setPrefWidth(WINDOW_WIDTH);
      tmp[i].setPrefHeight(45);
      tmp[i].setAlignment(Pos.CENTER);
      tmp[i].setSpacing(2);
    }
  }

  private void setFooter()
  {
    footerDesc.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    footerDesc.setTextAlignment(TextAlignment.LEFT);
    footerDesc.setText("Not Allowed");

    notAllowed.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    notAllowed.setTextAlignment(TextAlignment.RIGHT);
    notAllowed.setFill(Color.valueOf("#e60000"));

    footer.getChildren().add(footerDesc);
    footer2.getChildren().add(notAllowed);
  }

  private void alignVBox()
  {
    vbox.getChildren().addAll(header, header2);
    vbox.setMargin(header, new Insets(10, 10, 0, 10));
    vbox.setMargin(header2, new Insets(0, 10, 40, 10));

    vbox.getChildren().addAll(r1, r2, r3, r4, r5);
    if (num == 4) vbox.setMargin(r1, new Insets(21, 0, 0, 0));

    vbox.getChildren().addAll(footer, footer2);
    vbox.setMargin(footer, new Insets(30, 10, 0, 10));
    vbox.setMargin(footer2, new Insets(0, 10, 40, 10));
  }

  /*
  private void handleClick(MouseEvent event)
  {
    Node node = (Node) event.getSource();

    //paintToggle(node);
    System.out.println("?");
  }
  */


  private void handleDetected(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    node.startFullDrag();
    startStack(node);
    paint(node);

    startNode = node;
  }

  private void handleDragOver(MouseEvent event)
  {
    if (event.getX() < 7 || event.getY() < 7 || event.getX() > 33 || event.getY() > 33) return;

    Node node = (Node) event.getSource();
    addNode(node);
    paint(node);
  }

  private void handleReleased(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    if (!(event.getX() < 7 || event.getY() < 7 || event.getX() > 33 || event.getY() > 33))
    {
      addNode(node);
      paint(node);
    }

    removeAllPaint();
    flipWord(draggedWord);

    if (!(startNode.equals(node) || draggedWord.length() < 3)) evaluateWord(draggedWord);

    draggedWord = "";
  }

  private static void paint(Node node)
  {
    node.setEffect(new SepiaTone());
  }

  private static void removeAllPaint()
  {
    for (int i = stacked.size() - 1; i >= 0; i--)
    {
      Node tmp = stacked.pop();
      tmp.setEffect(null);

      draggedWord += tmp.getId();
    }
  }

  private static void paintToggle(Node node)
  {
    if (node.getEffect() != null) node.setEffect(null);
    else node.setEffect(new SepiaTone());
  }

  private static void startStack(Node node)
  {
    stacked.add(node);
  }

  private static void addNode(Node node)
  {
    if (!stacked.lastElement().equals(node))
    {
      stacked.add(node);
    }
  }

  private static void flipWord(String s)
  {
    String flipped = "";

    for (int i = s.length() - 1; i > 0; i--)
    {
      flipped += s.substring(i, i + 1);
      if (i == 1) flipped += s.substring(0, 1);
    }

    draggedWord = flipped;
  }

  private static void evaluateWord(String s)
  {
    if (Controller.findWord(s.toLowerCase()))
    {
      ArrayList<String> wl = Controller.getWordList();
      if (wl.contains(s)) return;
      else Controller.addWordList(s);

      Controller.setScore(s);
      scoreV.setText(Integer.toString(Controller.getScore()));

      System.out.println("Found " + s + "! Score is " + Controller.getScore() + "\n");
    }
    else
    {
      Text tmpNotAllowed = new Text(s + " ");
      tmpNotAllowed.setFont(notAllowed.getFont());
      tmpNotAllowed.setFill(notAllowed.getFill());
      footer2.getChildren().add(tmpNotAllowed);


      Double tmp = footer2.getChildren().get(footer2.getChildren().size()-1).getLayoutX();
      System.out.println("!!!!! " + tmp);

      if (tmp > WINDOW_WIDTH)
      {
        Text deliminator = new Text("\n");
        footer2.getChildren().add(deliminator);
      }
    }

  }

  public void setView(String[] args)
  {
    launch(args);
  }

}
