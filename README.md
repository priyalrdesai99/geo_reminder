# Geo-Reminder - Task Management Mobile App

## Introduction

In the dynamic realm of mobile applications, the Geo-Reminder project stands at the forefront—a transformative initiative redefining task management. Our primary focus is on crafting foundational elements that drive application efficiency, specifically creating a robust database schema and integrating Firebase services. This involves establishing the groundwork for efficient CRUD operations and incorporating Firebase Cloud Messaging to ensure timely push notification delivery.

## Overview of Work Done

The main task completed here was to integrate Firebase for user authentication and data management. In the SignUpFragment, Firebase Realtime Database is employed to securely store user information, with real-time validations ensuring accurate data entry. The SignUpFragment also incorporates duplicate email checks to prevent account duplication. In the LogInFragment, Firebase queries validate user credentials against the stored data, and upon successful login, user details are locally stored using Android's SharedPreferences for a seamless user experience. Dashboard items are showcased, and the user has the option to add items and descriptions which is pushed to the database in Firebase where userId is the key used to map.

## Features

- User registration into Firebase with first name, last name, email, phone, and password fields.
- Real-time email, phone, and password validation.
- Duplicate email checks during registration.
- User authentication using email and password for login.
- Local storage of user data upon successful login using SharedPreferences.
- Dashboard items stored in the Firebase table mapped by userId.

## Prerequisites and Installation

1. Pull Frontend XML files from branch: **Adit_Patel**, to integrate UI elements.
2. Ensure all necessary plugins are present in the Gradle files.
3. Utilize Firebase for user data storage:
   - Integrate Firebase SDK into your project.
   - Update the Firebase configuration file (google-services.json) with your project details.
   - Create a Firebase Project and add your Android App to the project.
   - Register your app by providing the package name (`com.example.geo_reminder`) and other details.
   - Download the `google-services.json` configuration file.
   - Place the `google-services.json` file in the app module of your Android Studio project.

## Screenshots

- **Fig 1: Database Structure**
![Screenshot (118)](https://github.com/priyalrdesai99/geo_reminder/assets/44197829/f99d383c-457d-4ea1-b2fa-e445e65c2987)


- **Fig 2: Item Lists Database Structure**
![Screenshot (122)](https://github.com/priyalrdesai99/geo_reminder/assets/44197829/5eeecccd-c4ad-4f41-822b-ef86bc091942)


- **Fig 3: Code Snippet for SignUp Data Push**
![Screenshot (121)](https://github.com/priyalrdesai99/geo_reminder/assets/44197829/3e656d39-52d5-43c1-b923-f7d8d60c691e)

