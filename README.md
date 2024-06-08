# MimChat

MimChat is a chat application with features similar to WhatsApp, utilizing Firebase for backend services and Firestore for image storage. The application follows the MVVM (Model-View-ViewModel) design pattern for better code organization and maintainability.


## Video Demo

Here is a demo of the project:

<video width="600" controls>
  <source src="https://github.com/mohammad-ayan-008/CHATAPP/raw/main/8E458B4718189B106599C16DCC302E84_video_dashinit.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>



## Features

- **Real-time Messaging:** Send and receive messages in real-time.
- **User Authentication:** Secure user authentication using Firebase Authentication with email and password.
- **Image Sharing:** Share images in chat, stored in Firebase Firestore.
- **User Presence:** See online/offline status of users.
- **Push Notifications:** Receive notifications for new messages.

## Architecture

MimChat follows the MVVM design pattern, which helps in separating the business logic from the UI, making the codebase more modular and easier to test.

- **Model:** Handles the data layer, including data retrieval and storage using Firebase.
- **View:** The UI components that display the data.
- **ViewModel:** Acts as a mediator between the View and the Model, holding the UI logic.

## Technologies Used

- **Firebase Authentication:** For user authentication via email and password.
- **Firebase Firestore:** For storing chat messages and images.
- **Firebase Realtime Database:** For presence and online status updates.
- **MVVM Architecture:** For structured and maintainable code.
- **Java:** Programming language used for the development of the app.

## Getting Started

### Prerequisites

- Android Studio installed.
- Firebase account and project set up.

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/mimchat.git
    cd mimchat
    ```

2. Open the project in Android Studio:
    - Open Android Studio.
    - Click on "Open an existing Android Studio project".
    - Navigate to the cloned repository directory and select it.

3. Set up Firebase:
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project or use an existing one.
    - Add an Android app to your Firebase project.
    - Follow the instructions to download the `google-services.json` file.
    - Place the `google-services.json` file in the `app` directory of your Android Studio project.

4. Add Firebase SDK to your project:
    - Add the Firebase SDK dependencies to your `app/build.gradle` file. Make sure to include dependencies for Firebase Authentication, Firestore, and Realtime Database.

5. Sync the project with Gradle files:
    - Click on "Sync Now" in the banner that appears in Android Studio.

6. Run the application:
    - Connect your Android device or start an emulator.
    - Click on "Run" in Android Studio to build and run the app.

## Configuration

- **Firebase Configuration:** Ensure your Firebase project's credentials are correctly set up in your app.
- **Permissions:** Make sure to add necessary permissions in the `AndroidManifest.xml` file for internet access and other required functionalities.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes or improvements.
