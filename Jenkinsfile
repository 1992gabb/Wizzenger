node {
	stage('Update'){
		checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		userRemoteConfigs: [[url: 'https://github.com/gbombardier/Android_Wizzenger.git']]])	
	}
	
    	stage('Build') {
		//sh 'make' 
		bat 'gradlew.bat assembleDebug'
	}
       
	stage('UI Test') {
        	echo 'Testing..'  
    	}
	
	//stage('Archivage') {
        	//archiveArtifacts artifacts: 'app/build/outputs/apk/*', fingerprint: true    
    	//}
}
