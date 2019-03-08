pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
		try {
			sh './gradlew --refresh-dependencies clean assemble'
			lock('emulator') {
				sh './gradlew connectedCheck'
			}
			currentBuild.result = 'SUCCESS'
			} catch(error) {
				slackSend channel: '#build-failures', color: 'bad', message: "This build is broken ${env.BUILD_URL}", token: 'XXXXXXXXXXX'
				currentBuild.result = 'FAILURE'
			}
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
