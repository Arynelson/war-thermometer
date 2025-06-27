### World War Thermometer API ###

The World War Thermometer is an API that calculates a threat level index for the possibility of a third world war. It aggregates data from multiple sources to compute an index ranging from 0 to 100, where 100 represents the highest threat level. This project is developed by Ary Hauffe Neto (GitHub: Arynelson).

## Features
Real-time Threat Index: Calculates a threat index based on current global events





-Getting Started
Prerequisites
Java 21 JDK
Maven

-API keys for:
NewsAPI
ACLED (requires registration)

-Installation
Clone the repository:

```
git clone https://github.com/Arynelson/war-thermometer-api.git
cd war-thermometer-api
Configure API keys:
Create an application.properties file in src/main/resources with:
```
properties
news.api.key=your_newsapi_key
acled.api.key=your_acled_key
Build and run:

```
mvn spring-boot:run
API Endpoints
Current Threat Index:

http
GET /api/current-index
Example response:

json
{
  "currentIndex": 34.5,
  "description": "World War III Threat Level"
}
Calculation Methodology:

http
GET /api/methodology
Example response:

json
{
  "title": "World War Thermometer Index Methodology",
  "description": "The index is calculated with capped contributions..."
}
```
Deployment
The application can be deployed for free on Render.com:

Create a new Web Service on Render

Connect to your GitHub repository

Set the following:

Runtime: Java

Build Command: mvn clean package

Start Command: java -jar target/*.jar

Environment Variables:
```
NEWS_API_KEY: your NewsAPI key

ACLED_API_KEY: your ACLED key

JAVA_OPTS: -Xmx256m -Xss512k -XX:+UseCompressedOops
```
How It Works
The index is calculated using four weighted factors:

Active Armed Conflicts (40% weight, max 40 points):
```
formula
(actual_conflicts / 50) * 40
Source: ACLED API

Nuclear War Mentions (30% weight, max 30 points):

formula
(actual_mentions / 200) * 30
Source: NewsAPI

International Sanctions (20% weight, max 20 points):

formula
(actual_sanctions / 100) * 20
Source: GDELT Project (simulated)

Global Tension Events (10% weight, max 10 points):

formula
(tension_events / 500) * 10
Source: GDELT Project
```
The index updates every 6 hours using a scheduled task.

Project Structure
```
src/
├── main/
│   ├── java/com/war_thermometer/
│   │   ├── controllers/
│   │   │   └── ThermometerController.java
│   │   ├── services/
│   │   │   ├── IndexCalculationService.java
│   │   │   ├── NewsApiService.java
│   │   │   ├── AcledApiService.java
│   │   │   └── GdeltApiService.java
│   │   └── Application.java
│   ├── resources/
│   │   └── application.properties

```




Developed by Ary Hauffe Neto

"Measuring global tensions one API call at a time"
