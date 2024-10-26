pipeline {
    agent any

    environment {
        COVERAGE_THRESHOLD = 80 // Define tu umbral de cobertura deseado aquí
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

        stage('JaCoCo Report') {
            steps {
                // Generar el informe de cobertura
                sh 'cd gestion-empresa && mvn jacoco:report'

                // Mostrar la cobertura en consola
                script {
                    def coverageReport = readFile('gestion-empresa/target/site/jacoco/jacoco-resources.csv')
                    def lines = coverageReport.split('\n')
                    def coverage = lines.find { it.contains('TOTAL') }?.split(',')[1]?.trim()

                    echo "Cobertura de código: ${coverage}%"

                    // Verificar si la cobertura está por debajo del umbral
                    if (coverage.toFloat() < env.COVERAGE_THRESHOLD) {
                        error "La cobertura de código está por debajo del umbral de ${env.COVERAGE_THRESHOLD}%"
                    }
                }
            }
        }
    }
}
