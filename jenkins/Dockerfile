FROM jenkins/jenkins:lts

RUN /usr/local/bin/install-plugins.sh blueocean

RUN /usr/local/bin/install-plugins.sh git

RUN /usr/local/bin/install-plugins.sh docker

RUN /usr/local/bin/install-plugins.sh kubernetes

USER jenkins