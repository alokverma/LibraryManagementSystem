def err = null
def fastlane_command
try {

    node {

        stage('Git Checkout') {
            git url: 'https://github.com/alokverma/LibraryManagementSystem.git'
        }

        stage('Dependencies') {
                sh 'export JAVA_HOME=/opt/jdk1.8.0_201'
                sh 'export JRE_HOME=/opt/jdk1.8.0_201/jre'
                sh 'export JRE_HOME=/opt/jdk1.8.0_201/jre'
                sh 'export PATH=$PATH:/opt/jdk1.8.0_201/bin:/opt/jdk1.8.0_201/jre/bin'
                sh 'echo $JAVA_HOME'
        }

        stage('Clean Build') {
               sh './gradlew clean'
        }

        stage('Build release ') {
               sh './gradlew assembleRelease'
        }



    }

} catch (caughtError) {

    err = caughtError
    currentBuild.result = "FAILURE"

} finally {

    if(currentBuild.result == "FAILURE"){
              sh "echo 'Build FAILURE'"
    }else{
         sh "echo 'Build SUCCESSFUL'"
    }

}