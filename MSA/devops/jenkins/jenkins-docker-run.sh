docker run -d \
 -v $(pwd)/data:/var/jenkins_home \
 -p 8989:8080 \
 -p 50000:50000 \
 --name jenkins-server \
 --restart=on-failure \
 jenkins/jenkins:lts-jdk17  
