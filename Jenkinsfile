node {
	stage('Update'){
		echo 'Update..'
		//sh 'git clone https://github.com/gbombardier/Android_Wizzenger.git .'
		sh 'git pull'
		//checkout([$class: 'GitSCM', branches: [[name: '*/master']],
     		//userRemoteConfigs: [[url: 'https://github.com/gbombardier/Android_Wizzenger.git']]])	
	}
	
    	sh 'sudo /var/lib/jenkins/workspace/Wizzenger_Pipeline/appActionsScript.sh'
	
	stage('Archivage') {
        	archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
		
    	}
}
