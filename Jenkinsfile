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
		echo 'Building and testing..'
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	}
    	
	stage('Archivage') {
		//Archiver seulement si le build de tests a fonctionné
		def file = readFile "logErrors.txt"
		def result = file.split("Task :app:connectedDebugAndroidTest FAILED")
		if (result.trim() == '') {
  			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}else{
			currentBuild.result = 'FAILURE';
			return;
		}
    	}
}
