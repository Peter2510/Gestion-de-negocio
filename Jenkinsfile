pipeline {
    agent any
    environment {
        COVERAGE_THRESHOLD = 80.0
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
                script {
                    // Leer el archivo de cobertura
                    def report = readFile 'gestion-empresa/target/site/jacoco/jacoco.csv'
                    def lines = report.split("\n")
                    def totalCovered = 0
                    def totalMissed = 0

                    // Saltar la primera línea (encabezados)
                    for (int i = 1; i < lines.length; i++) {
                        def coverageParts = lines[i].split(",")
                        if (coverageParts.length > 4) {
                            // Convertir a Integer para evitar errores de tipo
                            totalMissed += coverageParts[3].toInteger() // INSTRUCTION_MISSED
                            totalCovered += coverageParts[4].toInteger() // INSTRUCTION_COVERED
                        }
                    }

                    // Calcular la cobertura total
                    def coverage = (totalCovered + totalMissed > 0) ? (totalCovered / (totalCovered + totalMissed)) * 100 : 0
                    echo "Cobertura actual: ${coverage}%"

                    // Verificar si cumple con el umbral
                    if (coverage < COVERAGE_THRESHOLD) {
                        error "El porcentaje de cobertura es menor al ${COVERAGE_THRESHOLD}%. Cobertura actual: ${coverage}%"
                    }
                }
            }
        }


}

