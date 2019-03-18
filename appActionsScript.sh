#!/bin/bash

#Creer logErrors.txt si ce n'est pas fait.
if [ ! -e logErrors.txt ]
then
    sudo touch logErrors.txt
    sudo chmod 777 logErrors.txt
    sudo chmod 777 gradlew
    sudo chmod 777 /var/lib/jenkins/workspace/Wizzenger_Pipeline/app
fi

#-------------- Builder et Tester App ----------------
#/usr/android-sdk-linux/platform-tools/adb uninstall com.bombardier_gabriel.wizzenger.test
#./gradlew connectedAndroidTest | tee logErrors.txt
wait

result="$(grep 'Task :app:connectedDebugAndroidTest FAILED' logErrors.txt)"
echo $result
#Si un test échoue, la ligne suivante est printée : Task :app:connectedDebugAndroidTest FAILED

if [ "$result" != "" ]
then
  #Si failure, on sort du jenkins
  echo "Test failure, not building new apk."
  exit 0
else
  #Si fonctionne, on build
  echo "Tests succeeded, building new apk."
  #./gradlew assembleDebug
fi


#Si on voulait installer lapp pour lutiliser et non tester

#phone1=1215fcf92c900304
#phone2=05157df5bd0d0412

#./gradlew assembleDebug

#/usr/android-sdk-linux/platform-tools/adb -s $phone1 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk
#/usr/android-sdk-linux/platform-tools/adb -s $phone2 install -r  /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk

