pipeline {
    agent any
    environment {
        COVERAGE_THRESHOLD = 80
    }
    stages {
        stage('Checkout') {
            steps {
                // Clonar el código de la rama específica
                checkout scm
            }
        }

        stage('Compile & Test') {
            steps {
                // Compilar y ejecutar las pruebas unitarias
                sh 'cd gestion-empresa && mvn clean test'
            }
        }

        stage('Code Coverage') {
            steps {
                // Ejecutar JaCoCo y verificar el coverage
                script {
                    def coverageResult = sh(script: "cd gestion-empresa && mvn jacoco:report", returnStatus: true)
                    
                    if (coverageResult != 0) {
                        error "El análisis de cobertura falló."
                    }

                    // Extrae el porcentaje de cobertura de JaCoCo
                    def report = readFile 'gestion-empresa/target/site/jacoco/jacoco.csv'
                    def lines = report.split("\n")
                    def coverageLine = lines.find { it.contains("INSTRUCTION") }
                    def coverage = coverageLine.split(",")[3].toFloat()

                    echo "Cobertura actual: ${coverage}%"
                    
                    if (coverage < COVERAGE_THRESHOLD) {
                        error "El porcentaje de cobertura es menor al ${COVERAGE_THRESHOLD}%. Coverage actual: ${coverage}%"
                    }
                }
            }
        }

        stage('Docker Compose') {
            when {
                expression { 
                    // Solo si el coverage es >= 80%
                    return coverage >= COVERAGE_THRESHOLD
                }
            }
            steps {
                // Construir y ejecutar Docker Compose
                sh 'cd gestion-empresa && sudo docker-compose -f target/docker-compose.yml up --build -d'
            }
        }
    }
    post {
        always {
            // Limpieza, apagar contenedores
            sh 'cd gestion-empresa && sudo docker-compose down'
        }
        success {
            echo 'Pipeline completado exitosamente.'
        }
        failure {
            echo 'El pipeline ha fallado.'
        }
    }
}

