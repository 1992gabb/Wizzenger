node {
	stage('Update'){
		echo 'Update..'
		//sh 'git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		sh 'git pull'
		//checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		//userRemoteConfigs: [[url: 'https://github.com/gbombardier/Android_Wizzenger.git']]])	
	}
	
    	stage('Build') {
		echo 'Build..'
		sh './gradlew assembleDebug'
	}
       
	stage('UI Test') {
        	echo 'Testing..' 
		//bat 'C:/Users/gbombardier/AppData/Local/Android/Sdk/tools/emulator.exe -avd "Pixel_2_API_28"'
		//bat 'gradlew.bat test'
    	}
	
	stage('Archivage') {
        	archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
	
    	}
}
