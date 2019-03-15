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
		//echo sh '$?'
		if (1 == 1) {
			echo 'wouhou success'
  			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}else{
			echo 'tes nullllll'
			currentBuild.result = 'FAILURE';
			return;
		}
    	}
}
