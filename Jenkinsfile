pipeline {
    agent any

    environment {
        DOCKER_TOKEN = credentials('git-ssh-token')
        DOCKER_USER = 'KyriakosKarda'
        DOCKER_SERVER = 'ghcr.io'
        DOCKER_PREFIX = 'ghcr.io/kyriakoskarda/project-ds-street_food_go'
    }

    stages {
        stage('Test') {
            steps {
                sh '''
                    echo "Start Testing"
                    ./mvnw test
                '''
            }
        }

        stage('Docker build and push') {
            steps {
                sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    docker build --rm -t $DOCKER_PREFIX:$TAG .
                    echo $TAG > .tag
                '''

                sh '''
                    echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_PREFIX --all-tags
                '''
            }
        }
        stage('Deploy App To VM') {
            steps {
                dir('deploy_to_vm') {
                    git credentialsId: 'git-pKey', 
                        url: 'git@github.com:KyriakosKarda/Ansible-Deployment-DevOps.git'

                    withCredentials([
                        string(credentialsId: 'git-ssh-token', variable: 'GHCR_TOKEN')
                    ]) {
                        sh '''
                        ansible-playbook -i hosts.yml 
                        playbooks/spring.yml
                        '''
                    }
                }
            }
        }
    }
}