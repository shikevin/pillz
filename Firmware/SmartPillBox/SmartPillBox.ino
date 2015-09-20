int LED1Pin = 7;
int LED2Pin = 6;
int LED3Pin = 5;
int LED4Pin = 4;
int SWITCH_PIN = 3;

int led1_indicatorState = LOW;
int led2_indicatorState = LOW;
int led3_indicatorState = LOW;
int led4_indicatorState = LOW;
bool pictureNeeded = false;
bool pictureTaken = false;
int boxOpen;

int incomingByte = 0;

void setup() {
  Serial.begin(9600);
  Serial.println("Connecting...");
  pinMode(LED1Pin, OUTPUT);
  pinMode(LED2Pin, OUTPUT);
  pinMode(LED3Pin, OUTPUT);
  pinMode(LED4Pin, OUTPUT);
  pinMode(SWITCH_PIN, INPUT);
}

void loop() {
  boxOpen = digitalRead(SWITCH_PIN);
  if (Serial.available() > 0) {
        incomingByte = Serial.read();
        if (incomingByte == 1) {
           led1_indicatorState = HIGH; 
        } else if (incomingByte == 2) {
           led2_indicatorState = HIGH;
        } else if (incomingByte == 3) {
           led3_indicatorState = HIGH; 
        } else if (incomingByte == 4) {
           led4_indicatorState = HIGH;  
        } else if (incomingByte == 5) {
           pictureNeeded = true;
        } else if (incomingByte == 6) {
           pictureTaken = true; 
        } else if (incomingByte == 7) {
           led1_indicatorState = LOW;
           led2_indicatorState = LOW;
           led3_indicatorState = LOW;
           led4_indicatorState = LOW;
           pictureNeeded = false;
           pictureTaken = false;
        }
  }
  digitalWrite(LED1Pin, led1_indicatorState);
  digitalWrite(LED2Pin, led2_indicatorState);
  digitalWrite(LED3Pin, led3_indicatorState);
  if (pictureNeeded && !pictureTaken && !boxOpen) {
     led4_indicatorState = HIGH; 
  }
  digitalWrite(LED4Pin, led4_indicatorState);
}
