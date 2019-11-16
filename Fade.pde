Scene sceneStart;
Scene sceneEnd;

public class Fade
{
  Fade(
    Scene sceneStart, 
    Scene sceneEnd
    )
  {
    fadeAmount = 1;

    fade = 0; 
    fadeStart = 0; 

    ledRedValueStart = sceneStart.ledRedValue; 
    ledRedValueEnd = sceneEnd.ledRedValue; 

    ledBlueValueStart = sceneStart.ledBlueValue; 
    ledBlueValueEnd = sceneEnd.ledBlueValue; 
    
    ledGreenValueStart = sceneStart.ledGreenValue; 
    ledGreenValueEnd = sceneEnd.ledGreenValue; 
    
    servoPositionStart = sceneStart.servoPosition;
    servoPositionEnd = sceneEnd.servoPosition;
    
    // drawFade();
  }

  void drawFade(String elevator, int elevatorSpeed, int t)
  {
    

    if(elevatorSpeed < 100){
      elevatorSpeed = motorSpeedLimitLow;
    }

    if(elevatorSpeed > 200){
      elevatorSpeed = motorSpeedLimitHigh;
    }
    fadeEnd = t/10;

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
      
    while (fade < fadeEnd) {

      fade = fade + fadeAmount;
      constLedRed = round(map(fade, fadeStart, fadeEnd, ledRedValueStart, ledRedValueEnd));
      constLedBlue = round(map(fade, fadeStart, fadeEnd, ledBlueValueStart, ledBlueValueEnd));
      constLedGreen = round(map(fade, fadeStart, fadeEnd, ledGreenValueStart, ledGreenValueEnd));
      constServo = round(map(fade, fadeStart, fadeEnd, servoPositionStart, servoPositionEnd))+90;
      arduino.servoWrite(servo, constServo);
      arduino.analogWrite(ledRed, constLedRed);
      arduino.analogWrite(ledBlue, constLedBlue);
      arduino.analogWrite(ledGreen, constLedGreen);
      
      delay(10);
    }

    arduino.digitalWrite(motorPinHigh, Arduino.LOW);
    arduino.digitalWrite(motorPinLow, Arduino.LOW);
    arduino.analogWrite(motorPinSpeed, elevatorSpeed);
    println("elevator: STOP");
  }

  private int constServo;
  private int constLedRed;
  private int constLedBlue;
  private int constLedGreen;
  

  private int fade;
  private int fadeStart;
  private float fadeEnd;
  private int fadeAmount;

  private int ledRedValueStart; 
  private int ledRedValueEnd;
  
  private int ledBlueValueStart; 
  private int ledBlueValueEnd;
  
  private int ledGreenValueStart; 
  private int ledGreenValueEnd;
  
  private int servoPositionStart;
  private int servoPositionEnd;
}
