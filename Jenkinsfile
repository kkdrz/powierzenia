#!/usr/bin/env groovy

pipeline {
    stages {
        stage('checkout') {
           steps {
               step('checkout') {
                    checkout scm
               }
           } 
        }

        // stage('check java') {
        //     sh "java -version"
        // }

        // stage('clean') {
        //     sh "chmod +x gradlew"
        //     sh "./gradlew clean --no-daemon"
        // }
        // stage('nohttp') {
        //     sh "./gradlew checkstyleNohttp --no-daemon"
        // }

        // stage('npm install') {
        //     sh "./gradlew npm_install -PnodeInstall --no-daemon"
        // }

        // stage('backend tests') {
        //     try {
        //         sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
        //     } catch(err) {
        //         throw err
        //     } finally {
        //         junit '**/build/**/TEST-*.xml'
        //     }
        // }

        // stage('frontend tests') {
        //     try {
        //         sh "./gradlew npm_run_test-ci -PnodeInstall --no-daemon"
        //     } catch(err) {
        //         throw err
        //     } finally {
        //         junit '**/build/test-results/TESTS-*.xml'
        //     }
        // }

        stage('publish to docker-hub') {
            stepc {
                step('build image') {
                    sh "./gradlew -Pprod jibDockerBuild -Djib.to.image='pmorski/powierzenia'"
                    sh "docker push pmorski/powierzenia"
                }
            }
        }

    }
}
