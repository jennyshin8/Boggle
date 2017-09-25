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
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * Created by jh on 9/23/17.
 */
public class View extends Application
{
  private final int WINDOW_WIDTH = 500;
  private final int WINDOW_HEIGHT = 500;
  private Scene scene = new Scene(new Group());

  private VBox vbox = new VBox();
  ///////////// Header - Score & Time /////////////
  private HBox header = new HBox();
  private HBox header2 = new HBox();

  private Text time = new Text();
  private Text time2 = new Text();
  private Text score = new Text();
  private Text score2 = new Text();

  //////////// Board  - (n x n) Board ////////////
  private HBox r1 = new HBox();
  private HBox r2 = new HBox();
  private HBox r3 = new HBox();
  private HBox r4 = new HBox();
  private HBox r5 = new HBox();

  //////// Footer - Not acceptable words /////////
  private HBox footer = new HBox();
  private HBox footer2 = new HBox();

  private Text footerDesc = new Text();
  private Text notAllowed = new Text();

  private static int num;

  public View() { }
  public View(int n)
  {
    this.num = n;
  }

  @Override
  public void init() throws Exception { super.init();}

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

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();
  }


  private void setHeader()
  {
    time.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    time.setTextAlignment(TextAlignment.LEFT);
    time.setText("Time");

    score.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    score.setTextAlignment(TextAlignment.LEFT);
    score.setText("Score");

    time2.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    time2.setTextAlignment(TextAlignment.LEFT);
    time2.setText("2:55");

    score2.setFont(Font.font("Arial Red", FontWeight.BLACK, 20));
    score2.setTextAlignment(TextAlignment.RIGHT);
    score2.setText("2");

    header.getChildren().add(time);
    header.setSpacing(370);
    header.getChildren().add(score);

    header2.getChildren().add(time2);
    header2.setSpacing(413);
    header2.getChildren().add(score2);
  }

  private void setBoard(int n)
  {
    HBox[] tmp = {r1, r2, r3, r4, r5};

    for (int r = 0; r < n; r++)
    {
      for (int c = 0; c < n; c++)
      {
        Button btn = new Button();
        btn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resource/" + Controller.playBoard[r][c] + ".png"))));
        btn.setId(Controller.playBoard[r][c]);
        btn.setPadding(new Insets(2, 2, 2, 2));
        btn.setOnMouseClicked(this::handleClick);

        btn.setOnDragDetected(this::handleDetected);
        btn.setOnMouseDragOver(this::handleDragOver);
        btn.setOnMouseDragReleased(this::handleReleased);

        tmp[r].getChildren().add(btn);
      }
    }

    for(int i = 0; i < n; i++)
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
    notAllowed.setText("Acs");

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

  private void handleClick(MouseEvent event)
  {
    //if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
    Node node = (Node) event.getSource();

    paintToggle(node);
  }


  private void handleDetected(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    node.startFullDrag();
    paint(node);
    Controller.startDraggedStr(node.getId());
  }

  private void handleDragOver(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    if(event.getX() < 7 || event.getY() < 7 || event.getX() > 33 || event.getY() > 33) return;
    paint(node);
    Controller.putDraggedStr(node.getId());
  }

  private void handleReleased(MouseEvent event)
  {
    Node node = (Node) event.getSource();
    paint(node);
    Controller.putDraggedStr(node.getId());
    Controller.checkWord(Controller.draggedStr);
  }

  private static void paint(Node node)
  {
    node.setEffect(new SepiaTone());
  }

  private static void paintToggle(Node node)
  {
    if (node.getEffect() != null) node.setEffect(null);
    else node.setEffect(new SepiaTone());
  }



  public void setView(String[] args)
  {
    launch(args);
  }

}
