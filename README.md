# English with Sir Azeem — Student Communication App

Android app for preparing WhatsApp/SMS messages for a list of student numbers.

## Build APK online with GitHub Actions

1. Create a GitHub account and a new repository.
2. Upload all files in this project to the repository (keep `.github/workflows/build-apk.yml`).
3. Open **Actions** → **Build English with Sir Azeem APK**.
4. Click **Run workflow**.
5. Wait for the build to finish.
6. Open the completed workflow run and download the artifact named **English-with-Sir-Azeem-debug-apk**.
7. Extract the downloaded artifact; it contains `app-debug.apk`.

The app name and branding are set to **English with Sir Azeem**. WhatsApp is opened with the message pre-filled; the final Send action remains manual.

## Local build

Open the project in Android Studio and use Build → Build APK(s). Android Studio's official documentation explains the build flow and signed release APK process.
