node {
	stage('Update'){
		checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		userRemoteConfigs: [[url: 'http://git-server/user/repository.git']]])	
	}
	
    	stage('Build') {
		sh 'make' 
                archiveArtifacts artifacts: 'app/build/outputs/apk/*', fingerprint: true  
	}
       
	stage('UI Test') {
        	echo 'Testing..'  
    	}
}
