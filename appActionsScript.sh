#!/bin/bash

#Noter ici les id des téléphones
phone1=1215fcf92c900304

#Actions à faire lors du build (builder l'app et le test)
echo 'Build..'
#./gradlew assembleDebug
./gradlew connectedAndroidTest

#Phase de test
echo 'Testing..' 

#Vérifier si l'app est déja présente sur le device
#/usr/android-sdk-linux/platform-tools/adb -s $phone1 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk
/usr/android-sdk-linux/platform-tools/adb -s $phone1 install -r -t  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk

