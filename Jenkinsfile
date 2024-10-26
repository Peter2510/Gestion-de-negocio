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
                    // Definir la ruta del archivo de cobertura
                    def coverageFile = 'gestion-empresa/target/site/jacoco/jacoco.csv'
                    
                    // Verificar si el archivo existe
                    if (!fileExists(coverageFile)) {
                        error "El archivo de cobertura no se encontró: ${coverageFile}"
                    }

                    // Leer el archivo de cobertura
                    def coverageReport = readFile(coverageFile)
                    def lines = coverageReport.split('\n')
                    
                    // Buscar la línea que contiene 'TOTAL'
                    def totalLine = lines.find { it.contains('TOTAL') }

                    // Comprobar si se encontró la línea TOTAL
                    if (totalLine) {
                        // Extraer la cobertura total
                        def coverage = totalLine.split(',')[1]?.trim()

                        // Validar que coverage no sea nulo o vacío
                        if (coverage) {
                            echo "Cobertura de código: ${coverage}%"

                            // Verificar si la cobertura está por debajo del umbral
                            if (coverage.toFloat() < env.COVERAGE_THRESHOLD) {
                                error "La cobertura de código está por debajo del umbral de ${env.COVERAGE_THRESHOLD}%"
                            }
                        } else {
                            error "La cobertura total no se pudo determinar."
                        }
                    } else {
                        error "No se encontró la línea TOTAL en el archivo de cobertura."
                    }
                }
            }
        }
    }
}
