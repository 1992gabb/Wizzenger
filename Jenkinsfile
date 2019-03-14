node {
	stage('Update'){
		echo 'Update..'
		if (fileExists('Jenkinsfile')) {
    			sh 'git pull'
		} else {
    			sh 'git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		}
	}
	
	stage('App Related Actions'){
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	}
    	
	stage('Archivage') {
		//Archiver seulement si le build de tests a fonctionné
		def result = sh 'echo "$?"'
		if (result == 1) {
  			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}else{
			currentBuild.result = 'FAILURE';
			return;
		}
    	}
}
