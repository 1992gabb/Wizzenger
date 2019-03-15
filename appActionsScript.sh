#!/bin/bash

#Actions à faire lors du build (builder l'app et le test)

echo 'Building and testing..'

#./gradlew connectedAndroidTest 2> logErrors.txt
#wait

result= tail -n1 logErrors.txt | cut -d' ' -f2
echo $result

if [ $result=="FAILURE" ]
then
  #Si failure, on sort du jenkins
  echo "Test failure, not building new apk."
  exit 0
else
  #Si fonctionne, on build
  echo "Tests succeeded, building new apk."
  ./gradlew assembleDebug
fi


#Si on voulait installer lapp pour lutiliser et non tester

#phone1=1215fcf92c900304
#phone2=05157df5bd0d0412

#./gradlew assembleDebug

#/usr/android-sdk-linux/platform-tools/adb -s $phone1 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk
#/usr/android-sdk-linux/platform-tools/adb -s $phone2 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk

