pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
				sh './gradlew --refresh-dependencies clean assemble'
				lock('emulator') {
					sh './gradlew connectedCheck'
				}
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
			steps{
				archiveArtifacts 'app/build/outputs/apk/*'
			}
				
		}
    }
}