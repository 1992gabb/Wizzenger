node {
	stage('Update'){
		
	}
	
    stage('Build') {
		build 'Wizzenger_Test'   
	}
       
	stage('Test') {
        echo 'Testing..'  
    }
	
	stage('Deploy') {
        echo 'Deploying....'   
    }
		
	stage('Archive') {
		archiveArtifacts 'app/build/outputs/apk/debug/app-debug.apk'			
	}
    
}
