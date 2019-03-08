node {
	stage('Update'){
		git pull https://github.com/gbombardier/Android_Wizzenger.git	
	}
	
    	stage('Build') {
		build 'Wizzenger_Test'   
	}
       
	stage('UI Test') {
        	echo 'Testing..'  
    	}
}
