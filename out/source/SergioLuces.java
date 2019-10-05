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
BlinkTwoScenes blink1;


Arduino arduino; // Crea el objeto Arduino
int ledPin = 13; // Designa el numero de PIN para el LED



// initializing the RGB values to zero
int redVal = 0;
int greenVal = 0;
int blueVal = 0;

int ledRed = 5;
int ledGreen = 3;
int ledBlue = 6;

int servo = 13;


public void setup()
{
  // Setting up the screen
  // size(850, 400);
  // background(20);

  //print( Arduino.list());
  // opening up the Serial port, change the "COM5" and baud rate
  arduino = new Arduino(this, "/dev/ttyUSB0", 57600); // Configura el puerto como

  arduino.pinMode(servo, Arduino.SERVO);
  
  scene1 = new Scene(ledRed, 200,ledBlue, 200,ledGreen, 0,servo,0);
  scene2 = new Scene(ledRed, 200,ledBlue, 0,ledGreen, 200,servo,170);
  scene3 = new Scene(ledRed, 200,ledBlue, 0,ledGreen, 0,servo,100);

  blink1 = new BlinkTwoScenes(scene1,scene2,500,4);
  fade1 = new Fade(scene2,scene3,1);

  blink1.drawBlink();
  // fade1.drawFade();
  // scene1.blink(500,4);
  // fade2 = new Fade(scene2,scene3,2);
  // scene1.blink(50,3);


  // println(scene1.ledRed);
  
}


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
        println("constServo: " + constServo);
        println("ledRedValue: " + ledRedValue);
        println("constLedBlue: " + constLedBlue);
        println("constLedGreen: " + constLedGreen);
    
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
        println("constServo: " + constServo);
        println("ledRedValue: " + ledRedValue);
        println("constLedBlue: " + constLedBlue);
        println("constLedGreen: " + constLedGreen);
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

  public void blink(int f, int t)
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SergioLuces" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
