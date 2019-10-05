import processing.serial.*; //Importamos las librerías necesarias
import cc.arduino.*;

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


void setup()
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


