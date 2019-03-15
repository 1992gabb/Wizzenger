node {
	stage('Update'){
		echo 'Update..'
		if (fileExists('Jenkinsfile')) {
    			sh 'sudo git pull'
		} else {
    			sh 'sudo git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		}
	}
	
	stage('App Related Actions'){
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	}
    	
	stage('Archivage') {
		//Archiver seulement si le build de tests a fonctionné
		def result = readFile("${WORKSPACE}/scriptResult.txt")
		//def result = 'FAILED'
		echo result.trim()
		if (result.trim() == 'actionable') {
  			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}else if (result.trim() == 'FAILED'){
			currentBuild.result = 'FAILURE';
			return;
		}else{
			echo "wat"
			currentBuild.result = 'FAILURE';
			return;
		}
    	}
}
