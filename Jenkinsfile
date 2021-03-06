#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('check java') {
            steps {
                sh "java -version"
            }
        }

        stage('clean') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean --no-daemon"
            }
        }

        stage('nohttp') {
            steps {
                sh "./gradlew checkstyleNohttp --no-daemon"
            }
        }

        stage('npm install') {
            steps {
                sh "./gradlew npm_install -PnodeInstall --no-daemon"
            }
        }

        stage('backend tests') {
            steps {
                try {
                    sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/build/**/TEST-*.xml'
                }
            }
        }

        stage('frontend tests') {
            steps {
                try {
                    sh "./gradlew npm_run_test-ci -PnodeInstall --no-daemon"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/build/test-results/TESTS-*.xml'
                }
            }
        }

        stage('publish to docker-hub') {
            steps {
                sh "./gradlew jib -Djib.to.image=pmorski/powierzenia -Djib.to.auth.username=$USERNAME -Djib.to.auth.password=$PASSWORD"
                sh "docker push pmorski/powierzenia"
            }
        }

    }
}
