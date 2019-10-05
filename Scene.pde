public class Scene
{
  Scene(
    int LR, int LRV, 
    int LG, int LGV, 
    int LB, int LBV, 
    int S, int SP
    )
  {
    ledRed = LR;
    ledRedValue = LRV; 
    ledGreen = LG;
    ledGreenValue = LGV; 
    ledBlue = LB;
    ledBlueValue = LBV;
    servo = S;
    servoPosition = SP;
    //drawScene();
  }

  private void drawScene()
  {
    arduino.analogWrite(ledRed, ledRedValue);
    arduino.analogWrite(ledGreen, ledGreenValue);
    arduino.analogWrite(ledBlue, ledBlueValue);
    arduino.servoWrite(servo, servoPosition);
  }

  void blink(int f, int t)
  {
    count = (t*500)/f;
    for (int i = 0; i < count; ++i) {
        arduino.analogWrite(ledRed, ledRedValue);
        arduino.analogWrite(ledGreen, ledGreenValue);
        arduino.analogWrite(ledBlue, ledBlueValue);
        delay(f);
        arduino.analogWrite(ledRed, 0);
        arduino.analogWrite(ledGreen, 0);
        arduino.analogWrite(ledBlue, 0);
        delay(f);
        println("i: "+i);
    }
  }
  
  private int ledRed;
  private int ledRedValue; 
  private int ledGreen;
  private int ledGreenValue; 
  private int ledBlue;
  private int ledBlueValue;
  private int servo;
  private int servoPosition;

  private int time;
  private int frecuency;
  private int count;

}
