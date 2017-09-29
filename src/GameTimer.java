import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Created by jh on 9/26/17.
 */
public class GameTimer extends AnimationTimer
{
  private static long startTime;
  private static String timeS = "";

  private static IntegerProperty time = new SimpleIntegerProperty();
  private static BooleanProperty running = new SimpleBooleanProperty();


  @Override
  public void start()
  {
    startTime = System.currentTimeMillis();
    running.set(true);
    super.start();
  }

  @Override
  public void stop()
  {
    running.set(false);
    super.stop();
    System.exit(0);
  }

  @Override
  public void handle(long timestamp)
  {
    long now = System.currentTimeMillis();
    Double timeD = ((now - startTime) / 1000.0);
    //int min = 0, sec = 0;

    time.set(timeD.intValue());

    if (timeD.intValue() / 60 >= 3)
    {
      super.stop();
    }
    else if (timeD.intValue() / 60 >= 2)
    {
      timeD = (timeD % 60);
      timeD = 60 - timeD;

      if (timeD < 10) timeS = " 0:0" + Integer.toString(timeD.intValue());
      else timeS = " 0:" + Integer.toString(timeD.intValue());
    }
    else if (timeD.intValue() / 60 >= 1)
    {
      timeD = (timeD % 60);
      timeD = 60 - timeD;

      if (timeD < 10) timeS = " 1:0" + Integer.toString(timeD.intValue());
      else timeS = " 1:" + Integer.toString(timeD.intValue());
    }
    else
    {
      timeD = 60 - timeD;

      if (timeD < 10) timeS = " 2:0" + Integer.toString(timeD.intValue());
      else timeS = " 2:" + Integer.toString(timeD.intValue());
    }

    View.timeV.setText(timeS);
  }
}
