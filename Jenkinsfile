node {
	stage('Update'){
		echo 'Update..'
		if (fileExists('Jenkinsfile')) {
    		sh 'sudo git pull'
		} else {
			//Modifier pour le repo actuel
    		sh 'sudo git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		}
	}
	
	stage('App Related Actions'){
		echo 'Building and testing..'
		sh '/var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	}
    	
	stage('Archivage') {
		//Archiver seulement si le build de tests a fonctionne
		def file = readFile "logErrors.txt"
		
		if (file.contains("Task :app:connectedDebugAndroidTest FAILED")) {
  			currentBuild.result = 'FAILURE';
			return;
		}else{
			archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		}
    }
}
