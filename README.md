# Geo-Reminder Android Application

## Introduction

In the present scenario, we find ourselves at the most ideal situation to capitalize on the functionalities of Geo-Reminder: an Android application that seeks to redefine task management through the seamless integration of geolocation services and Firebase services. This application helps users receive timely reminders and prompts for location-specific tasks. The application triggers notifications when approaching landmark areas.

## Overview of Work Done

The main task completed here was the development of the User Interface of the User Profile module. This includes Login Fragment, SignUp Fragment, and Forget Password. These are required to ensure privacy and security and give users a personalized experience by storing further reminders in their account and enhancing their user experience. The application begins with a splash screen and then is redirected to the LogIn Page. Here, the user needs to input credentials for authentication purposes. If credentials are verified with data present in Firebase’s realtime database, they are redirected to Dashboard Activity.

### Features

1. **Splash Activity**: It is a constant screen that appears for a set time when the application is launched.
2. **LogIn Fragment**: It is for user authentication when a user tries to log in back to the application.
3. **SignUp Fragment**: If the user doesn’t have an account, then they need to sign up for the application.
4. **Forget Password**: If the account exists and the user has forgotten the password, then we have provided OTP-based login functionality leveraging Firebase services.
5. **Dashboard Activity**: Once you are successfully logged in, you will be redirected to this page through which a dialog box will open to add reminders of items.

### Prerequisites

1. Pull all backend (.Java) Files from branch: **Avi_Mehta**, to effectively use Google Firebase’s services.
2. Ensure all plugins are present in the Gradle files.

### Steps

1. Launch the app on your Android device.
2. Navigate to the SignUp or LogIn screen.
3. For SignUp, fill in the required information and click "Create Account."
4. For LogIn, enter your credentials and click "Login."
5. Receive real-time validation feedback.
6. If successful, the user is redirected to the Dashboard.
7. Click on the "Add Reminder" button which opens a dialog box to add items along with a description.

## Screenshots
- **Fig 1. Splash Activity**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/6aa54dc3-2e52-4bbb-8180-576e187ff5f4" width="150" height="300">

- **Fig 2. Login Fragment**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/1ec0cd36-d678-4f68-a23c-7f65a715c353" width="150" height="300">

- **Fig 3. SignUp Fragment**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/e881e537-c5a0-469c-a37c-16561594d805" width="150" height="300">

- **Fig 4. Forget Password**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/70f998e7-776d-4623-9b04-2a857b7cbfc3" width="150" height="300">

- **Fig 5. Dashboard**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/ec1dd805-06af-4e6d-b5e8-f8605e4120dc" width="150" height="300">

- **Fig 6. Add Items**
<img src="https://github.com/priyalrdesai99/geo_reminder/assets/60565128/5674c44a-144a-41a7-9b28-0466b00bb4b1" width="150" height="300">
