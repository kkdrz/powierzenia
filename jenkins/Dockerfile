FROM jenkins/jenkins:lts

RUN /usr/local/bin/install-plugins.sh blueocean

RUN /usr/local/bin/install-plugins.sh git

RUN /usr/local/bin/install-plugins.sh docker

RUN /usr/local/bin/install-plugins.sh kubernetes

USER root

RUN curl -sL https://deb.nodesource.com/setup_10.x | bash -

RUN apt-get install -y nodejs
RUN apt-get install apt-transport-https ca-certificates software-properties-common -y

RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"

RUN apt-get update

RUN apt-get install -y docker-ce

RUN apt-get update

RUN systemctl enable docker
RUN usermod -aG docker jenkins

USER jenkins
