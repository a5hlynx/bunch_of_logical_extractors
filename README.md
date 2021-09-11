# Bunch of Logical Extractors

An Android Forensic app for logical acquisition. The app is inspired and influenced by [AFLogical](https://github.com/nowsecure/android-forensics).

## Installation

Install the apk using adb shell as shown below.
```
$ adb install g4n.bole.apk
Performing Streamed Install
Success
```
Obtain the apk either by generating from this project or downloading from the [release](https://github.com/a5hlynx/bunch_of_logical_extractors/releases).

## Usage

Open the Bunch of Logical Extractors app on the Android device in which it is installed. On the first execution, the app will ask you to grant 5 permissions, that are "Calendar", "Call logs", "Contacts", "File and media", and "SMS". Unless you grant all of them, the app won't start. If you failed to grant any of the necessary permissions, go "Settings" -> "Apps & Notifications" -> "Bunch of Logical Extractors" -> "Permissions", and then give the permissions.

Check the data to extract, select the output format either from XML or JSON, and then press the "EXTRACT" button. When the extraction is done, the path to the directory where the extracted data is stored will be displayed on the app's window as shown below.
```
..snip..
Providers
  851 recs Read & Saved into Providers.xml


Done...

Files asre saved under /storage/emulated/0/Documents/g4n.bole/20210912071808
```

Copy the extracted data using adb pull as shown below.
```
$ adb pull /storage/emulated/0/Documents/g4n.bole
/storage/emulated/0/Documents/g4n.bole/: 78 files pulled, 0 skipped. 8.4 MB/s (4931700 bytes in 0.562s)
```

## License

MIT License.
