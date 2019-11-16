import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import cc.arduino.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SergioLuces extends PApplet {

 //Importamos las librerías necesarias


Scene scene1;
Scene scene2;
Scene scene3;

Fade fade1;
Fade fade2;
BlinkTwoScenes blinkScenes;


Arduino arduino; // Crea el objeto Arduino
int ledPin = 13; // Designa el numero de PIN para el LED



// initializing the RGB values to zero
int redVal = 0;
int greenVal = 0;
int blueVal = 0;

int ledRed = 5;
int ledGreen = 6;
int ledBlue = 3;

int motorPinHigh = 13;  //9 10 11
int motorPinLow = 9;
int motorPinSpeed = 10;

int motorSpeedLimitLow = 100;
int motorSpeedLimitHigh = 200;

int servo = 11;


public void setup()
{
  // Setting up the screen
  // size(850, 400);
  // background(20);

  //print( Arduino.list());
  // opening up the Serial port, change the "COM5" and baud rate
  arduino = new Arduino(this, "/dev/ttyUSB0", 57600);
  arduino.pinMode(motorPinHigh, Arduino.OUTPUT);
  arduino.pinMode(motorPinLow, Arduino.OUTPUT);

  arduino.pinMode(servo, Arduino.SERVO);
  
  scene1 = new Scene(0, 200, 0, 10);
  scene2 = new Scene(0, 0, 200, 0);
  scene3 = new Scene(200, 0, 0, 90);

  blinkScenes = new BlinkTwoScenes(scene1,scene2);
  fade1 = new Fade(scene2,scene3);

  // scene3.drawScene("STOP", 0, 3000);
  // blinkScenes.drawBlink(50,5000);
  fade1.drawFade("UP", 150, 5000);
  // scene3.blink(50,5000);
  
}

public void draw(){
}

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
    count = (t*0.5f)/f;
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

  public void drawFade(String elevator, int elevatorSpeed, int t)
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

  public void blink(int f, int t)
  {

    count = (t*0.5f)/f;
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SergioLuces" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
