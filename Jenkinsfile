node {
	stage('Update'){
		echo 'Update..'
		if (fileExists('JenkinsFile')) {
    			sh 'git pull'
		} else {
    			sh 'git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		}
	}
	
	stage('App Related Actions'){
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	}
    	
	stage('Archivage') {
        	archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		
    	}
}
