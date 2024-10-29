def Build(){
    echo "Building the application..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-id-pass', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'  
        sh "docker build -t ${env.APP_NAME}:${env.TAG} ." 
        sh "docker tag ${env.APP_NAME}:${env.TAG} ${env.REPO_NAME}:${env.TAG}" 
        
        sh "docker push ${env.REPO_NAME}:${env.TAG}"  
    }
}

def Test(){
    echo "Testing the application..."

def Deploy(){
    echo "Deploying the application..."
    def makedir = "mkdir /root/new"  
    def cd = "cd /root/new"  
    def dockerCmd = "TAG=${env.TAG} REPO_NAME=${env.REPO_NAME} docker-compose up -d"
    
    sshagent(['jenkins-private-key']) { 
        
        sh "ssh -o StrictHostKeyChecking=no ${env.SERVER_USER}@${env.SERVER_IP} '${makedir};${cd}'"  

        sh "scp docker-compose.yml ${env.SERVER_USER}@${env.SERVER_IP}:/root/new" 
        sh "ssh -o StrictHostKeyChecking=no ${env.SERVER_USER}@${env.SERVER_IP} '${cd};${dockerCmd}'"
        
    }
}

return this