def gv

pipeline{
    agent any


environment {
    TAG = '1.0.5'
    SERVER_IP = "137.184.23.94" 
    SERVER_USER = "root"  
    REPO_NAME = "sahilrazz/mywebsite"
    APP_NAME = "mywebsite" 
}

    stages{
        stage("init"){
            steps{
                script{
                    gv = load "script.groovy"
                }
            }
        }

        stage("Build"){
            steps{
                script{
                    gv.Build()
                }
            }
        }

        stage("Test"){
            steps{
                script{
                    gv.Test()
                    echo "Test by webhook from github"
                }
            }
        }

        stage("Deploy"){
            steps{
                script{
                    gv.Deploy()
                }
            }
        }
    }
}
