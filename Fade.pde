Scene sceneStart;
Scene sceneEnd;

public class Fade
{
  Fade(
    Scene sceneStart, 
    Scene sceneEnd,
    int F
    )
  {
    fadeAmount = F;
    ledRed = sceneStart.ledRed;
    ledRedValue = sceneStart.ledRedValue; 
    ledRedValueStart = sceneStart.ledRedValue; 
    ledRedValueEnd = sceneEnd.ledRedValue;

    ledBlue = sceneStart.ledBlue;
    ledBlueValueStart = sceneStart.ledBlueValue; 
    ledBlueValueEnd = sceneEnd.ledBlueValue; 
    
    ledGreen = sceneStart.ledGreen;
    ledGreenValueStart = sceneStart.ledGreenValue; 
    ledGreenValueEnd = sceneEnd.ledGreenValue; 
    
    servo = sceneStart.servo;
    servoPositionStart = sceneStart.servoPosition;
    servoPositionEnd = sceneEnd.servoPosition;
    // constLedRed = map(constLedRed, 0, 100, 0, l);
    constServo = round(map(ledRedValueStart, ledRedValueStart, ledRedValueEnd, servoPositionStart, servoPositionEnd));
    // drawFade();
  }

  private void drawFade()
  {

    if (ledRedValue >= ledRedValueEnd) {
      while (ledRedValue > ledRedValueEnd) {

        ledRedValue = ledRedValue - fadeAmount;
        constLedBlue = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, ledBlueValueStart, ledBlueValueEnd));
        constLedGreen = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, ledGreenValueStart, ledGreenValueEnd));
        constServo = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, servoPositionStart, servoPositionEnd));
        arduino.servoWrite(servo, constServo);
        arduino.analogWrite(ledRed, ledRedValue);
        arduino.analogWrite(ledBlue, constLedBlue);
        arduino.analogWrite(ledGreen, constLedGreen);
        delay(35);
      }
    } else {
      while (ledRedValue < ledRedValueEnd) {

        ledRedValue = ledRedValue + fadeAmount;
        constLedBlue = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, ledBlueValueStart, ledBlueValueEnd));
        constLedGreen = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, ledGreenValueStart, ledGreenValueEnd));
        constServo = round(map(ledRedValue, ledRedValueStart, ledRedValueEnd, servoPositionStart, servoPositionEnd));
        arduino.servoWrite(servo, constServo);
        arduino.analogWrite(ledRed, ledRedValue);
        arduino.analogWrite(ledBlue, constLedBlue);
        arduino.analogWrite(ledGreen, constLedGreen);
        delay(35);
      }
    }
  }

  private int constServo;
  private int constLedBlue;
  private int constLedGreen;
  private int fadeAmount;

  private int ledRed;
  private int ledRedValue; 
  private int ledRedValueStart; 
  private int ledRedValueEnd;
  
  private int ledBlue;
  private int ledBlueValueStart; 
  private int ledBlueValueEnd;
  
  private int ledGreen;
  private int ledGreenValueStart; 
  private int ledGreenValueEnd;
  
  private int servo; 
  private int servoPositionStart;
  private int servoPositionEnd;
}
