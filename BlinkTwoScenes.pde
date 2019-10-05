Scene scene;

public class BlinkTwoScenes
{
  BlinkTwoScenes(
    Scene scene1, 
    Scene scene2, 
    int f,
    int t
    )
  {
    time = t;
    frecuency = f;
    count = (t*500)/f;
    scene1LedRed = scene1.ledRed;
    scene1LedRedValue = scene1.ledRedValue; 
    scene1LedGreen = scene1.ledGreen;
    scene1LedGreenValue = scene1.ledGreenValue; 
    scene1LedBlue = scene1.ledBlue;
    scene1LedBlueValue = scene1.ledBlueValue;

    scene2LedRed = scene2.ledRed;
    scene2LedRedValue = scene2.ledRedValue; 
    scene2LedGreen = scene2.ledGreen;
    scene2LedGreenValue = scene2.ledGreenValue; 
    scene2LedBlue = scene2.ledBlue;
    scene2LedBlueValue = scene2.ledBlueValue; 
  }

  private void drawBlink()
  {
    for (int i = 0; i < count; ++i) {
        arduino.analogWrite(scene1LedRed, scene1LedRedValue);
        arduino.analogWrite(scene1LedGreen, scene1LedGreenValue);
        arduino.analogWrite(scene1LedBlue, scene1LedBlueValue);
        
        delay(frecuency);
        
        arduino.analogWrite(scene2LedRed, scene2LedRedValue);
        arduino.analogWrite(scene2LedGreen, scene2LedGreenValue);
        arduino.analogWrite(scene2LedBlue, scene2LedBlueValue);
        delay(frecuency);
        println("i: "+i);
    }
  }

  private int time;
  private int frecuency;
  private int count;

  private int scene1LedRed;
  private int scene1LedRedValue;
  private int scene1LedGreen;
  private int scene1LedGreenValue;
  private int scene1LedBlue;
  private int scene1LedBlueValue;

  private int scene2LedRed;
  private int scene2LedRedValue;
  private int scene2LedGreen;
  private int scene2LedGreenValue;
  private int scene2LedBlue;
  private int scene2LedBlueValue;
}
