# Task Tracker (for Samsung Galaxy)

A simple, clean task-capture app for your Samsung Galaxy phone. Add tasks,
give them a category, optionally set a due date/time (you'll get a
notification), check them off, and delete them when you're done. Everything
is saved privately on your phone — nothing is uploaded anywhere.

This is a real Android app, written in Kotlin. Since you're not that
technical, here's a step-by-step guide to get it running on your phone.

## What you'll need

- A Windows, Mac, or Linux computer
- Your Samsung Galaxy phone and a USB cable
- About 20-30 minutes for the one-time setup (mostly waiting for downloads)

## Step 1: Install Android Studio

Android Studio is the free, official tool for building Android apps.

1. Go to https://developer.android.com/studio and download Android Studio.
2. Install it using the default options (just keep clicking "Next").
3. The first time you open it, it will download some additional Android
   components — let it finish.

## Step 2: Open this project

1. Open Android Studio.
2. Choose **Open** (not "New Project").
3. Select the `task-tracker-app` folder (the one this README is in).
4. Android Studio will "sync" the project — a progress bar appears at the
   bottom. This can take a few minutes the first time. If a yellow banner
   appears saying the Gradle wrapper is missing or out of date, click the
   button it offers (usually "OK" or "Download") to let it fix itself.

## Step 3: Turn on Developer Mode on your Galaxy phone

1. On your phone, open **Settings > About phone**.
2. Tap **Software information**.
3. Tap **Build number** 7 times in a row. You'll see a message that
   Developer mode is turned on.
4. Go back to **Settings**, find **Developer options** (usually near the
   bottom, or under "Battery and device care > More" on some models), and
   turn on **USB debugging**.

## Step 4: Run the app on your phone

1. Plug your phone into your computer with the USB cable.
2. On the phone, a popup may ask you to "Allow USB debugging" — tap **Allow**.
3. Back in Android Studio, your phone's name should appear in the device
   dropdown near the top toolbar (it may take a few seconds to show up).
4. Click the green **Run ▶** button.
5. The app will install and open automatically on your phone.

That's it — the app is now installed just like any other app. You can find
it on your phone under the name **Task Tracker**, and you don't need to keep
it plugged into the computer to use it afterward.

### Optional: get an installable file (APK) instead

If you'd rather install the app without keeping it plugged into a computer
each time, in Android Studio choose **Build > Build App Bundle(s) / APK(s) >
Build APK(s)**. When it finishes, click the "locate" link in the
notification to find the `.apk` file, then copy it to your phone (e.g. via
USB, Google Drive, or email to yourself) and tap it there to install. Your
phone may ask you to allow installing from this source the first time.

## Using the app

- Tap the **+** button to add a task.
- Type what you need to do, pick a category (Personal, Work, Errands,
  Health, Other), and optionally tap "Set a due date" to be reminded.
- Tap the checkbox to mark a task done, or the trash icon to delete it.
- Tap a task to edit it.

## Notes for anyone technical picking this up later

- UI: Jetpack Compose + Material 3 (adopts the phone's dynamic color on
  Android 12+/One UI 4.1+).
- Storage: Room (SQLite) database, on-device only, no network permissions.
- Reminders: `AlarmManager` schedules a notification at the task's due
  time; reminders are re-scheduled on device boot.
- Minimum Android version: 8.0 (API 26).
- This project was not built/compiled in the sandbox that generated it (no
  Android SDK available there) — please run a Gradle sync in Android
  Studio as the first real build/verification step.
