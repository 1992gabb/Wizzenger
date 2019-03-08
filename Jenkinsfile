pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
	    }	
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
		stage('Archive') {
			archiveArtifacts 'app/build/outputs/apk/*'	
		}
    }
}
