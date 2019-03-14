#!/bin/bash

#Actions à faire lors du build (builder l'app et le test)
echo 'Build..'
sh './gradlew assembleDebug'

#Phase de test
echo 'Testing..' 

#Vérifier si l'app est déja présente sur le device
sh '/usr/android-sdk-linux/platform-tools/adb install /var/lib/jenkins/workspace/Wizzenger_Pipeline/app/build/outputs/apk/debug/app-debug.apk'

