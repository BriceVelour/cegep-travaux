  #include <WiFi.h>
#include <HTTPClient.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Définition des broches SDA et SCL pour la communication I2C
#define SDA 13
#define SCL 14

// Initialisation de l'écran LCD avec l'adresse I2C
LiquidCrystal_I2C ecranLCD(0x27, 16, 2);

// Définition des informations WiFi
const char* nomReseauWiFi = "********"; // Remplacez par le nom de votre réseau WiFi
const char* motDePasseWiFi = "********"; // Remplacez par votre mot de passe WiFi

void setup() {
  // Initialisation de la communication série pour le débogage
  Serial.begin(115200);

  // Initialisation de la communication I2C
  Wire.begin(SDA, SCL);

  // Initialisation de l'écran LCD
  ecranLCD.init();
  ecranLCD.backlight();
  ecranLCD.setCursor(0, 0);
  ecranLCD.print("Connexion WiFi");

  // Connexion au réseau WiFi
  WiFi.begin(nomReseauWiFi, motDePasseWiFi);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  // Confirmation de la connexion
  ecranLCD.clear();
  ecranLCD.print("WiFi Connecte");
  delay(1000);
  ecranLCD.clear();

  // Récupération et affichage de l'adresse IP locale
  String adresseIPLocale = WiFi.localIP().toString();
  ecranLCD.print("IP locale :");
  ecranLCD.setCursor(0, 1);
  ecranLCD.print(adresseIPLocale);
  delay(3000);
  ecranLCD.clear();

  // Récupération de l'adresse IP externe
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient requeteHTTP;
    requeteHTTP.begin("http://api.ipify.org"); // URL pour récupérer l'IP externe
    int codeHTTP = requeteHTTP.GET();

    if (codeHTTP > 0) { // Si la requête HTTP est un succès
      String reponse = requeteHTTP.getString();
      // Extraction de l'adresse IP externe
      String adresseIPExterne = reponse.substring(reponse.indexOf(":") + 2, reponse.indexOf("\"", reponse.indexOf(":") + 2));
      ecranLCD.print("IP externe :");
      ecranLCD.setCursor(0, 1);
      ecranLCD.print(adresseIPExterne);
      delay(3000);
    } else {
      ecranLCD.print("Erreur HTTP");
    }
    requeteHTTP.end();
  }
}

void loop() {
  // Pas de boucle nécessaire pour ce projet
}
