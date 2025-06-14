pipeline {
	agent any

    environment {
		SONAR_TOKEN = credentials('sonar-token')
        DOCKER_REGISTRY = 'your-registry'
        CONSUL_HOST = 'consul'
    }

    stages {
		stage('Checkout') {
			steps {
				checkout scm
            }
        }

        stage('Build & Test') {
			parallel {
				stage('Medecin Service') {
					steps {
						dir('medecin-service') {
							sh 'mvn clean compile test'
                        }
                    }
                }
                stage('Patient Service') {
					steps {
						dir('patient-service') {
							sh 'mvn clean compile test'
                        }
                    }
                }
                stage('RDV Service') {
					steps {
						dir('rdv-service') {
							sh 'mvn clean compile test'
                        }
                    }
                }
                stage('Dossier Service') {
					steps {
						dir('dossier-service') {
							sh 'mvn clean compile test'
                        }
                    }
                }
                stage('Gateway Service') {
					steps {
						dir('gateway') {
							sh 'mvn clean compile test'
                        }
                    }
                }
            }
        }

        stage('Security Scan - SonarQube') {
			parallel {
				stage('Scan Medecin Service') {
					steps {
						dir('medecin-service') {
							sh '''
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=${SONAR_TOKEN}
                            '''
                        }
                    }
                }
                stage('Scan Patient Service') {
					steps {
						dir('patient-service') {
							sh '''
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=${SONAR_TOKEN}
                            '''
                        }
                    }
                }
                stage('Scan RDV Service') {
					steps {
						dir('rdv-service') {
							sh '''
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=${SONAR_TOKEN}
                            '''
                        }
                    }
                }
                stage('Scan Dossier Service') {
					steps {
						dir('dossier-service') {
							sh '''
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=${SONAR_TOKEN}
                            '''
                        }
                    }
                }
                stage('Scan Gateway Service') {
					steps {
						dir('gateway') {
							sh '''
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.login=${SONAR_TOKEN}
                            '''
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
			steps {
				script {
					def services = ['medecin-service', 'patient-service', 'rdv-service', 'dossier-service', 'gateway']
                    for (service in services) {
						timeout(time: 10, unit: 'MINUTES') {
							def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
								error "Quality Gate failed for ${service}: ${qg.status}"
                            }
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
			parallel {
				stage('Build Medecin Service') {
					steps {
						dir('medecin-service') {
							sh 'mvn package -DskipTests'
                            sh 'docker build -t medecin-service:${BUILD_NUMBER} .'
                        }
                    }
                }
                stage('Build Patient Service') {
					steps {
						dir('patient-service') {
							sh 'mvn package -DskipTests'
                            sh 'docker build -t patient-service:${BUILD_NUMBER} .'
                        }
                    }
                }
                stage('Build RDV Service') {
					steps {
						dir('rdv-service') {
							sh 'mvn package -DskipTests'
                            sh 'docker build -t rdv-service:${BUILD_NUMBER} .'
                        }
                    }
                }
                stage('Build Dossier Service') {
					steps {
						dir('dossier-service') {
							sh 'mvn package -DskipTests'
                            sh 'docker build -t dossier-service:${BUILD_NUMBER} .'
                        }
                    }
                }
                stage('Build Gateway Service') {
					steps {
						dir('gateway') {
							sh 'mvn package -DskipTests'
                            sh 'docker build -t gateway:${BUILD_NUMBER} .'
                        }
                    }
                }
            }
        }

        stage('Security Scan - Docker') {
			parallel {
				stage('Scan Medecin Image') {
					steps {
						sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image medecin-service:${BUILD_NUMBER}'
                    }
                }
                stage('Scan Patient Image') {
					steps {
						sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image patient-service:${BUILD_NUMBER}'
                    }
                }
                stage('Scan RDV Image') {
					steps {
						sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image rdv-service:${BUILD_NUMBER}'
                    }
                }
                stage('Scan Dossier Image') {
					steps {
						sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image dossier-service:${BUILD_NUMBER}'
                    }
                }
                stage('Scan Gateway Image') {
					steps {
						sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image gateway:${BUILD_NUMBER}'
                    }
                }
            }
        }

        stage('Deploy to Staging') {
			steps {
				script {
					sh '''
                        docker-compose -f docker-compose.yml down
                        docker-compose -f docker-compose.yml up -d --build
                    '''
                }
            }
        }

        stage('Integration Tests') {
			steps {
				script {
					// Tests d'intégration via l'API Gateway
                    sh '''
                        sleep 30  # Attendre que les services démarrent
                        curl -f http://localhost:9999/actuator/health || exit 1
                        # Ajoutez d'autres tests d'intégration ici
                    '''
                }
            }
        }
    }

    post {
		always {
			// Nettoyage
            sh 'docker system prune -f'

            // Archiver les rapports
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'Code Coverage Report'
            ])
        }

        success {
			echo 'Pipeline executed successfully!'
            // Notification de succès
        }

        failure {
			echo 'Pipeline failed!'
            // Notification d'échec
        }
    }
}