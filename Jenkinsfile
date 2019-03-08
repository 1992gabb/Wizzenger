node {
	stage('Update'){
		git submodule update --init
	}
	
    stage('Build') {
		//branch name from Jenkins environment variables
		echo "My branch is: ${env.BRANCH_NAME}"

		def flavor = flavor(env.BRANCH_NAME)
		echo "Building flavor ${flavor}"

		//build your gradle flavor, passes the current build number as a parameter to gradle
		sh "./gradlew clean assemble${flavor}Debug -PBUILD_NUMBER=${env.BUILD_NUMBER}"    
	}
       
	stage('Test') {
        echo 'Testing..'  
    }
	
	stage('Deploy') {
        echo 'Deploying....'   
    }
		
	stage('Archive') {
		archiveArtifacts 'app/build/outputs/apk/*'			
	}
    
}