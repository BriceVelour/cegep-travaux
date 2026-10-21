#include <WiFi.h>
#include "DHTesp.h"
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// ==== CONFIGURATION ====
const char* ssid = "********";
const char* password = "********";
#define DHT_PIN 18
#define SDA 13
#define SCL 14

const byte ledPins[] = {4, 2, 15};   // R, G, B
const byte ledChns[] = {0, 1, 2};    // PWM channels

DHTesp dht;
LiquidCrystal_I2C lcd(0x27, 16, 2);
WiFiServer server(80);

float temperature = 0.0;
float humidity = 0.0;

void setup() {
  Serial.begin(115200);
  delay(1000);

  // LCD
  Wire.begin(SDA, SCL);
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Initialisation...");

  // Capteur DHT
  dht.setup(DHT_PIN, DHTesp::DHT11);

  // LED RGB - PWM Freenove
  for (int i = 0; i < 3; i++) {
    ledcAttachChannel(ledPins[i], 1000, 8, ledChns[i]);
  }

  // Connexion Wi-Fi
  WiFi.begin(ssid, password);
  lcd.setCursor(0, 1);
  lcd.print("Connexion WiFi...");
  Serial.println("🔌 Connexion WiFi en cours...");

  int essais = 0;
  while (WiFi.status() != WL_CONNECTED && essais < 20) {
    delay(1000);
    Serial.print(".");
    essais++;
  }

  lcd.clear();
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("\n✅ Wi-Fi connecté !");
    Serial.print("IP : ");
    Serial.println(WiFi.localIP());
    lcd.setCursor(0, 0);
    lcd.print("IP:");
    lcd.setCursor(3, 0);
    lcd.print(WiFi.localIP());
  } else {
    Serial.println("\n❌ Connexion Wi-Fi échouée.");
    lcd.setCursor(0, 1);
    lcd.print("WiFi echoué...");
  }

  server.begin();
}

void loop() {
  TempAndHumidity data = dht.getTempAndHumidity();
  temperature = data.temperature;
  humidity = data.humidity;

  if (!isnan(temperature) && !isnan(humidity)) {
    lcd.setCursor(0, 1);
    lcd.print("T:");
    lcd.print(temperature, 1);
    lcd.print((char)223);
    lcd.print("C H:");
    lcd.print((int)humidity);
    lcd.print("% ");
    setLedFromClimate(temperature, humidity);
  }

  WiFiClient client = server.available();
  if (client) {
    String request = client.readStringUntil('\r');
    client.flush();

    if (request.indexOf("GET /data") >= 0) {
      String json = "{";
      json += "\"temperature\":" + String(temperature, 1) + ",";
      json += "\"humidity\":" + String(humidity, 1);
      json += "}";
      client.println("HTTP/1.1 200 OK");
      client.println("Content-Type: application/json");
      client.println("Connection: close");
      client.println();
      client.println(json);
      client.stop();
      return;
    }

    // Page HTML sans LED virtuelle
String html = R"rawliteral(
<!DOCTYPE html>
<html lang='fr'>
<head>
  <meta charset='UTF-8'>
  <title>ESP32 Météo</title>
  <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background: #121212;
      color: white;
      margin: 0;
      padding: 20px;
    }

    .card {
      background: rgba(255,255,255,0.05);
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 0 20px rgba(0,255,255,0.15);
      max-width: 700px;
      margin: auto;
    }

    .summary {
      text-align: left;
      margin-bottom: 20px;
      padding: 10px;
      border-left: 4px solid #0ff;
      background: rgba(255,255,255,0.03);
      border-radius: 8px;
    }

    .assistant-bubble {
      margin-top: 10px;
      padding: 20px;
      border-radius: 12px;
      background: rgba(0, 255, 255, 0.05);
      border-left: 4px solid #0ff;
      box-shadow: 0 0 15px rgba(0,255,255,0.2);
      animation: pulse 3s ease-in-out infinite;
    }

    .assistant-bubble h3 {
      margin-top: 0;
      color: #0ff;
    }

    @keyframes pulse {
      0% { box-shadow: 0 0 8px rgba(0,255,255,0.2); }
      50% { box-shadow: 0 0 20px rgba(0,255,255,0.4); }
      100% { box-shadow: 0 0 8px rgba(0,255,255,0.2); }
    }

    .emoji {
      display: inline-block;
      animation: bounce 1.5s infinite ease-in-out;
      margin-right: 6px;
      font-size: 1.4em;
      transform-origin: center bottom;
    }

    @keyframes bounce {
      0%   { transform: translateY(0); }
      50%  { transform: translateY(-6px); }
      100% { transform: translateY(0); }
    }

    canvas {
      max-width: 100%;
      height: auto;
    }

    .legend {
      margin-top: 10px;
      text-align: center;
      font-size: 0.9em;
      opacity: 0.7;
    }
  </style>
</head>
<body>
  <div class="card">
    <h1>📊 Dashboard Météo ESP32</h1>

    <div class="summary" id="summary">
      <h2>Résumé météo</h2>
      <p><strong>Température :</strong> -- °C</p>
      <p><strong>Humidité :</strong> -- %</p>
      <p><strong>Confort :</strong> Calcul...</p>
      <p><strong>Message :</strong> --</p>
    </div>

    <div class="assistant-bubble" id="assistant">
      <h3>🤖 Assistant météo AI</h3>
      <p id="aiMessage">Chargement du message intelligent...</p>
    </div>

    <canvas id="myChart"></canvas>
    <div class="legend">
      <p><span style="color:orange;">■</span> Température &nbsp;&nbsp;
         <span style="color:cyan;">■</span> Humidité</p>
    </div>
  </div>

<script>
const chart = new Chart(document.getElementById('myChart'), {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'Température (°C)',
        borderColor: 'orange',
        backgroundColor: 'rgba(255,165,0,0.2)',
        data: []
      },
      {
        label: 'Humidité (%)',
        borderColor: 'cyan',
        backgroundColor: 'rgba(0,255,255,0.2)',
        data: []
      }
    ]
  },
  options: {
    responsive: true,
    animation: false,
    scales: {
      x: { title: { display: true, text: 'Heure' } },
      y: { beginAtZero: true }
    }
  }
});

function updateChart() {
  fetch('/data')
    .then(res => res.json())
    .then(data => {
      const now = new Date().toLocaleTimeString();
      const temp = data.temperature;
      const hum = data.humidity;

      chart.data.labels.push(now);
      chart.data.datasets[0].data.push(temp);
      chart.data.datasets[1].data.push(hum);

      if (chart.data.labels.length > 10) {
        chart.data.labels.shift();
        chart.data.datasets[0].data.shift();
        chart.data.datasets[1].data.shift();
      }

      chart.update();

      let comfort = '';
      let message = '';
      let aiMessage = '';

      if (temp < 10) {
        comfort = 'Très froid';
        message = 'Reste bien au chaud !';
        aiMessage = "<span class='emoji'>🥶</span> Il fait très froid. Reste bien au chaud !";
      } else if (temp < 20 && hum < 40) {
        comfort = 'Frais et sec';
        message = 'Bon pour se concentrer.';
        aiMessage = "<span class='emoji'>🌬️</span> Ambiance fraîche et sèche. Bon pour travailler !";
      } else if (temp >= 20 && temp < 28 && hum < 70) {
        comfort = 'Parfait';
        message = 'Profite de ta journée !';
        aiMessage = "<span class='emoji'>😌</span> Température parfaite. Profite bien de ta journée !";
      } else if (temp >= 28 && hum >= 70) {
        comfort = 'Chaleur humide';
        message = 'Hydrate-toi.';
        aiMessage = "<span class='emoji'>🥵</span> C’est chaud et humide. Pense à t’hydrater.";
      } else if (temp >= 30 && hum >= 80) {
        comfort = 'Canicule';
        message = 'Alerte chaleur extrême !';
        aiMessage = "<span class='emoji'>🚨</span> Canicule humide ! Alerte chaleur extrême.";
      } else {
        comfort = 'Modéré';
        message = 'Conditions normales.';
        aiMessage = "<span class='emoji'>🌡️</span> Conditions météo modérées.";
      }

      document.getElementById("summary").innerHTML = `
        <h2>Résumé météo</h2>
        <p><strong>Température :</strong> ${temp.toFixed(1)} °C</p>
        <p><strong>Humidité :</strong> ${hum.toFixed(1)} %</p>
        <p><strong>Confort :</strong> ${comfort}</p>
        <p><strong>Message :</strong> ${message}</p>
      `;

      document.getElementById("aiMessage").innerHTML = aiMessage;
    });
}

setInterval(updateChart, 2000);
</script>
</body>
</html>
)rawliteral";

    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("Connection: close");
    client.println();
    client.println(html);
    client.stop();
  }

  delay(2000);
}

// ==== LED RGB INTELLIGENTE ====
void setColor(byte r, byte g, byte b) {
  ledcWrite(ledChns[0], 255 - r);  // Rouge
  ledcWrite(ledChns[1], 255 - g);  // Vert
  ledcWrite(ledChns[2], 255 - b);  // Bleu
}

void setLedFromClimate(float t, float h) {
  if (t < 20 && h < 40) setColor(0, 0, 255);           // Froid et sec → bleu
  else if (t < 30 && h >= 50) setColor(0, 255, 0);     // Doux et humide → vert
  else if (t >= 30 && h >= 50) setColor(255, 0, 0);    // Chaud et humide → rouge vif
  else if (t >= 30 && h < 50) setColor(128, 0, 0);     // Chaud et sec → rouge foncé
}

