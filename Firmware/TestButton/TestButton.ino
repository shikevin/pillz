int LED1Pin = 7;
int SWITCH_PIN = 3;
int val;

void setup() {
  // put your setup code here, to run once:
  pinMode(LED1Pin, OUTPUT);
  pinMode(SWITCH_PIN, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  val = digitalRead(SWITCH_PIN);
  digitalWrite(LED1Pin, val);
}
