#!/bin/bash

#Pour certaines fonction, on doit passer l'id du téléphone en paramètre lorsque l'on run le script. 

checkIfAnyAvdsRunning () {
	echo "***** check if any avds is running"
	IsAnyAvdsRunnig=$(adb devices | wc -l) 
	if [ $IsAnyAvdsRunnig != '2' ]; then
		echo "*********** No devices are connected, exiting script ***********"
		exit 1
	fi
}

#Pas utilise pour l'instant
grantPermissions () {
	echo "*********** grant permissions to device $1 ***********"
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.WRITE_EXTERNAL_STORAGE
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.READ_EXTERNAL_STORAGE
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.READ_PHONE_NUMBERS   
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.READ_CONTACTS
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.WRITE_CONTACTS
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.READ_PHONE_STATE
	adb -s $1 shell pm grant com.summit.nexos.sdk.app.service android.permission.ACCESS_COARSE_LOCATION

	adb -s $1 shell pm grant com.summit.nexos.sdk.test android.permission.READ_SMS
	adb -s $1 shell pm grant com.summit.nexos.sdk.test android.permission.WRITE_EXTERNAL_STORAGE
	adb -s $1 shell pm grant com.summit.nexos.sdk.test android.permission.READ_EXTERNAL_STORAGE
}

createFiles(){

    if [ ! -e logErrors.txt ]
    then
	echo "*********** Creating necessary files in repo ***********"
        sudo touch log.txt
        sudo chmod 777 logErrors.txt
        sudo chmod 777 gradlew
        sudo chmod 777 ./app
    fi
}

#Build les tests et envoie les log dans un fichier txt
launchTests(){
    #/usr/android-sdk-linux/platform-tools/adb uninstall com.bombardier_gabriel.wizzenger.test
    
	echo "*********** Tests launching on connected devices. Logs will be in log.txt ***********"
	./gradlew connectedAndroidTest | tee logErrors.txt
    wait
}


verifyResults(){
	#Si un test échoue, la ligne suivante est printée : Task :app:connectedDebugAndroidTest FAILED
    result="$(grep 'Task :app:connectedDebugAndroidTest FAILED' logErrors.txt)" 
   

    if [ "$result" != "" ]
    then
		#Si failure, on sort du jenkins
        echo "*********** Test failure, not building new apk. ***********"
        exit 0
    else
        #Si fonctionne, on build
        echo "*********** Tests succeeded, building new apk. ***********"
        ./gradlew assembleDebug
    fi
}


checkIfAnyAvdsRunning
createFiles
launchTests
verifyResults
