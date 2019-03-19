#!/bin/bash

checkIfAnyAvdsRunning () {
	echo "***** check if any avds is running"
	IsAnyAvdsRunnig=$(adb devices | wc -l) 
	if [ $IsAnyAvdsRunnig != '2' ]; then
		echo "***** There are avds running... exiting script..."
		exit 1
	fi
}

createFiles(){
    if [ ! -e logErrors.txt ]
    then
        sudo touch logErrors.txt
        sudo chmod 777 logErrors.txt
        sudo chmod 777 gradlew
        sudo chmod 777 ./app
    fi
}

#Build les tests et envoie les log dans un fichier txt
launchTests(){
    #/usr/android-sdk-linux/platform-tools/adb uninstall com.bombardier_gabriel.wizzenger.test
    ./gradlew connectedAndroidTest | tee logErrors.txt
    wait
}

#Si un test échoue, la ligne suivante est printée : Task :app:connectedDebugAndroidTest FAILED
verifyResults(){

    result="$(grep 'Task :app:connectedDebugAndroidTest FAILED' logErrors.txt)" 
    echo "Le resultat est: $result"

    if [ "$result" != "" ]
    then
    #Si failure, on sort du jenkins
        echo "Test failure, not building new apk."
        exit 0
    else
        #Si fonctionne, on build
        echo "Tests succeeded, building new apk."
        ./gradlew assembleDebug
    fi
}
