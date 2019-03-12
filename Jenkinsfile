node {
	stage('Update'){
		echo 'Update..'
		sh 'cd Android_Wizzenger'
		sh 'sudo git fetch'
		//checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		//userRemoteConfigs: [[url: 'https://github.com/gbombardier/Android_Wizzenger.git']]])	
	}
	
    	stage('Build') {
		echo 'Build..'
		//sh 'make' 
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/gradlew assembleDebug'
		//bat 'gradlew.bat assembleDebug'
	}
       
	stage('UI Test') {
        	echo 'Testing..' 
		//bat 'C:/Users/gbombardier/AppData/Local/Android/Sdk/tools/emulator.exe -avd "Pixel_2_API_28"'
		//bat 'gradlew.bat test'
    	}
	
	//stage('Archivage') {
        	//archiveArtifacts artifacts: 'app/build/outputs/apk/*', fingerprint: true    
    	//}
}
