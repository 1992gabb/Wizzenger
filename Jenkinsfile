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
		def result = sh "tail -n1 logErrors.txt | cut -d' ' -f2"
		//def result = 'SUCCESS'
		echo result
		if (result == 'SUCCESS') {
			echo 'tes nullllll'
			currentBuild.result = 'FAILURE';
			return;
		}else if (result == 'FAILED'){
			echo 'wouhou success'
  			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}else{
			echo "wat"	
		}
    	}
}
