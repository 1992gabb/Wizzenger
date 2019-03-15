#!/bin/bash

#Actions à faire lors du build (builder l'app et le test)
function buildTest(){
  echo 'Building and testing..'
  
}
buildTest



#Si on voulait installer lapp pour lutiliser et non tester

#phone1=1215fcf92c900304
#phone2=05157df5bd0d0412

#./gradlew assembleDebug

#/usr/android-sdk-linux/platform-tools/adb -s $phone1 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk
#/usr/android-sdk-linux/platform-tools/adb -s $phone2 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk

