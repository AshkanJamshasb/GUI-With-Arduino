#include "Arduino.h"
//The setup function is called once at startup of the sketch
const int redPin = 9;
const int greenPin = 10;
const int bluePin = 11;

String in, red, green, blue, strobe;
int numRed, numGreen, numBlue, numStrobe, first, second, third;

void setup() {

  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);

  
  Serial.begin(9600);
  Serial.setTimeout(50);

}


void loop() {
  
  in = Serial.readString();
  
  first = in.indexOf(".");
  second = in.indexOf(".", (first+1));
  third = in.indexOf(".", (second+1));
  
  red = in.substring(0, first);
  green = in.substring((first+1), second);
  blue = in.substring((second+1), third);
  strobe = in.substring(third+1);
  
  numRed = red.toInt();
  numGreen = green.toInt();
  numBlue = blue.toInt();
  numStrobe = strobe.toInt();
  
  numRed = map(numRed, 0, 100, 0, 255);
  numGreen = map(numGreen, 0, 100, 0, 255);
  numBlue = map(numBlue, 0, 100, 0, 255);
  numStrobe = map(numStrobe, 0, 100, 100, 500);

  analogWrite(9, numRed);     // Turn the RED LED on
  analogWrite(10, numGreen);     // Turn the GREEN LED on
  analogWrite(11, numBlue);     // Turn the BLUE LED on

  
  if(numStrobe != 100) {
    delay(numStrobe*.1);   
    analogWrite(9, LOW);     // Turn the RED LED on
    analogWrite(10, LOW);     // Turn the GREEN LED on
    analogWrite(11, LOW);     // Turn the BLUE LED on
    delay(numStrobe*.1);  
  } else {
    delay(30);
  } 
}

