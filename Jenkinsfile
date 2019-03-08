node {
	stage('Update'){
		
	}
	
    	stage('Build') {
		sh 'make' 
                archiveArtifacts artifacts: 'app/build/outputs/apk/*', fingerprint: true  
	}
       
	stage('UI Test') {
        	echo 'Testing..'  
    	}
}
