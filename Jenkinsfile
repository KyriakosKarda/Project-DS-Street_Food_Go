pipeline{
    agent any
    
    environment{
        DOCKER_TOKEN=credentials('git')
        DOCKER_USER='KyriakosKarda'
        DOCKER_SERVER='ghcr.io'
        DOCKER_PREFIX='ghcr.io/KyriakosKarda/Project-DS-Street_Food_Go.git'
    }

    stages('Test'){
        steps{
            sh '''
                echo "Start Testing"
                ./mvnw test
            '''
        }
    }

    stage('Docker build and push'){
        steps{
            sh '''
                HEAD_COMMIT=$(git rev-parse --short HEAD)
                TAG=$HEAD_COMMIT-$BUILD_ID
                docker build --rm -t $DOCKER_PREFIX:$TAG .
            '''

            sh '''
                echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                docker push $DOCKER_PREFIX --all-tags
            '''
        }
    }
}