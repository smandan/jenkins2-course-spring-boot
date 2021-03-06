node {
    notify('Started')
    
	try{
	    stage('checkout') {

	       git 'https://github.com/smandan/jenkins2-course-spring-boot.git'
	    }   
	    def project_path = "spring-boot-samples/spring-boot-sample-atmosphere"
	   
	    dir(project_path) {
		stage ('compiling, tesing, and packaging'){
		    sh 'mvn clean package'
		}
		
		stage ('archival'){
		    archiveArtifacts artifacts: "target/*.jar", followSymlinks: false
		}
	    }

	    notify('Success')

	} catch (err) {
	    notify("Error: ${err}")
	    echo "Caught: ${err}"
	    currentBuild.result = 'FAILURE'
	}
}

def notify(status){
    emailext (
      to: "mandas1@nationwide.com",
      subject: "${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      body: """<p>${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
    )
}
