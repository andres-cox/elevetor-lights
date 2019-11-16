Scene scene;

public class BlinkTwoScenes
{
  BlinkTwoScenes(
    Scene scene1, 
    Scene scene2
    )
  {
    
    scene1LedRedValue = scene1.ledRedValue; 
    scene1LedGreenValue = scene1.ledGreenValue; 
    scene1LedBlueValue = scene1.ledBlueValue;

    scene2LedRedValue = scene2.ledRedValue; 
    scene2LedGreenValue = scene2.ledGreenValue; 
    scene2LedBlueValue = scene2.ledBlueValue; 
  }

  private void drawBlink(int f, int t)
  {
    time = t;
    frecuency = f;
    count = (t*0.5)/f;
    for (int i = 0; i < count; ++i) {
        arduino.analogWrite(ledRed, scene1LedRedValue);
        arduino.analogWrite(ledGreen, scene1LedGreenValue);
        arduino.analogWrite(ledBlue, scene1LedBlueValue);
        
        delay(frecuency);
        
        arduino.analogWrite(ledRed, scene2LedRedValue);
        arduino.analogWrite(ledGreen, scene2LedGreenValue);
        arduino.analogWrite(ledBlue, scene2LedBlueValue);
        delay(frecuency);
        println("i: "+i);
    }
  }

  private int time;
  private int frecuency;
  private float count;

  private int scene1LedRedValue;
  private int scene1LedGreenValue;
  private int scene1LedBlueValue;

  private int scene2LedRedValue;
  private int scene2LedGreenValue;
  private int scene2LedBlueValue;
}
