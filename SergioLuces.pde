import processing.serial.*; //Importamos las librerías necesarias
import cc.arduino.*;

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


void setup()
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

void draw(){
}

