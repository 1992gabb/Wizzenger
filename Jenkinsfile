node {
	stage('Update'){
		checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		userRemoteConfigs: [[url: 'https://github.com/gbombardier/Android_Wizzenger.git']]])	
	}
	
    	stage('Build') {
		//sh 'make' 
		sh 'chmod +x gradlew && ./gradlew --no-daemon --stacktrace clean :app:assembleDebug'
                
	}
       
	stage('UI Test') {
        	echo 'Testing..'  
    	}
	
	stage('Archivage') {
        	archiveArtifacts artifacts: 'app/build/outputs/apk/*', fingerprint: true    
    	}
}
