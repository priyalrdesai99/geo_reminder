# Geo-Reminder Android Application

## Introduction

Geo-Reminder is an Android application designed to revolutionize task management by integrating geolocation services with Firebase. It provides users with timely reminders and notifications for location-specific tasks, enhancing productivity and efficiency.

## Features

1. **User Profile**: Includes login and SignUp functions for user authentication.
2. **Database**: Utilizes Firebase's real-time database to store information.
3. **Dashboard Activity**: Main interface post-login, offering functionalities to add reminders.
4. **Geofencing Algorithm**: Triggers notifications when the user is within 2 miles of a designated location.
5. **Ranking**: Uses machine learning to intelligently rank tasks based on user preferences and semantic similarity.
6. **Maps Directions**: Integrates with Google Maps API to provide real-time directions, considering current traffic data.

## Prerequisites for Android Application

1. Android Studio.
2. Google Maps API key (obtained from Google Maps Platform).
3. Dependencies like Flask, scikit-learn (for the server component).

## Setup and Running the Android Application

1. Clone the repository: 
    ```
    git clone https://github.com/priyalrdesai99/geo_reminder.git
    ```
2. Open the project in Android Studio.
3. Ensure the active branch is `main`.
4. Replace the Google Maps API Key in `AndroidManifest.xml`, `GeoFencing.java`, and `MapsActivity.java`.
5. Build and run the application on an Android emulator or device.

# Server Setup for Task Ranking with Machine Learning

## Overview

A Flask-based server application that ranks tasks based on user search queries and preferences, utilizing NLP techniques for processing and ranking.

## Prerequisites for Server

- Python 3.8 or higher.
- Flask.
- NLTK.
- scikit-learn.
- pandas.

## Server Installation

1. Clone the Repository:
    ```bash
    git clone [repository_url]
    cd [repository_name]
    ```
2. Set Up a Virtual Environment (recommended):
    ```bash
    python -m venv venv
    source venv/bin/activate  # On Unix or MacOS
    venv\Scripts\activate  # On Windows
    ```
3. Install Required Python Packages:
    ```bash
    pip install flask nltk scikit-learn pandas
    ```
4. Download NLTK Data:
    ```bash
    python
    import nltk
    nltk.download('wordnet')
    ```

## Server Configuration

- Update `config.py` with necessary settings like `DATABASE_URI`, `API_KEY`, etc.

## Running the Server

1. Start the Flask Server:
    ```bash
    python main.py
    ```
2. To send requests (use Postman or a similar tool):
    - Send POST requests to `http://127.0.0.1:5000/rank` with a JSON body, for example:
    ```json
    {
      "search_query": "your_search_query",
      "tasks": ["task1", "task2", ...],
      "user_preferences": {"preference_key": "preference_value", ...}
    }
    ```
 ## Screenshots
  &nbsp; &nbsp; **Figure 1 Splash Screen**    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;   **Figure 2 Login Page**
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  **Figure 3 Add Item**
<br>
<img width="195" alt="image" src="https://github.com/priyalrdesai99/geo_reminder/assets/44197829/7ed714e5-d6cb-4505-9358-d4d4c5b4ce43"> &nbsp; &nbsp; &nbsp; &nbsp; 
<img width="200" alt="image" src="https://github.com/priyalrdesai99/geo_reminder/assets/44197829/8d38cd1c-f834-4a24-8437-bb58160e8ab8"> &nbsp; &nbsp; &nbsp; &nbsp;
<img width="195" alt="image" src="https://github.com/priyalrdesai99/geo_reminder/assets/44197829/d1e8a634-8261-42f9-8cdf-de9eabf04bbf">
<br>
<br>
<br>
&nbsp; &nbsp; **Figure 4 Item List**    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;   **Figure 5 Reminder Notification**
&nbsp; &nbsp; &nbsp; &nbsp;    **Figure 6 Directions Page**
<br>
<img width="200" alt="Screenshot 2023-12-07 190335" src="https://github.com/priyalrdesai99/geo_reminder/assets/44197829/78bfcbf1-9eae-49b2-86c5-2645651348b5"> &nbsp; &nbsp; &nbsp; &nbsp;
<img width="200" src = "https://github.com/priyalrdesai99/geo_reminder/assets/44197829/a7cd75fc-e0ab-42cb-aed0-8607c960fadd"> &nbsp; &nbsp; &nbsp; &nbsp;
<img width="195" alt="image" src="https://github.com/priyalrdesai99/geo_reminder/assets/44197829/0b6ca684-ebfc-4ab4-babb-8ec6b793594f">

