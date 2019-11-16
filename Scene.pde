public class Scene
{
  Scene(
    int LRV, 
    int LGV, 
    int LBV, 
    int SP
    )
  {
    ledRedValue = LRV; 
    ledGreenValue = LGV; 
    ledBlueValue = LBV;
    servoPosition = SP;
  }

  private void drawScene(String elevator, int elevatorSpeed, int t) {
    
    if(elevatorSpeed < 100){
      elevatorSpeed = motorSpeedLimitLow;
    }

    if(elevatorSpeed > 200){
      elevatorSpeed = motorSpeedLimitHigh;
    }

    if(elevator == "UP"){
      arduino.digitalWrite(motorPinHigh, Arduino.HIGH);
      arduino.digitalWrite(motorPinLow, Arduino.LOW);
      arduino.analogWrite(motorPinSpeed, elevatorSpeed);
      println("elevator: "+elevator);
      println("elevatorSpeed: "+elevatorSpeed);
    }

    if(elevator == "DOWN"){
      arduino.digitalWrite(motorPinHigh, Arduino.LOW);
      arduino.digitalWrite(motorPinLow, Arduino.HIGH);
      arduino.analogWrite(motorPinSpeed, elevatorSpeed);
      println("elevator: "+elevator);
      println("elevatorSpeed: "+elevatorSpeed);
    }

    if(elevator == "STOP"){
      arduino.digitalWrite(motorPinHigh, Arduino.LOW);
      arduino.digitalWrite(motorPinLow, Arduino.LOW);
      arduino.analogWrite(motorPinSpeed, 0);
      println("elevator: "+elevator);
      println("elevatorSpeed: " +elevatorSpeed);
    }

    arduino.analogWrite(ledRed, ledRedValue);
    arduino.analogWrite(ledGreen, ledGreenValue);
    arduino.analogWrite(ledBlue, ledBlueValue);
    arduino.servoWrite(servo, servoPosition);
    delay(t);

    arduino.digitalWrite(motorPinHigh, Arduino.LOW);
    arduino.digitalWrite(motorPinLow, Arduino.LOW);
    arduino.analogWrite(motorPinSpeed, elevatorSpeed);
    println("elevator: STOP");
  }

  void blink(int f, int t)
  {

    count = (t*0.5)/f;
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
  
  private int ledRedValue; 
  private int ledGreenValue; 
  private int ledBlueValue;
  private int servoPosition;

  private int time;
  private int frecuency;
  private float count;

}
