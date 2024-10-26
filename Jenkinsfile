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
                    def lines = coverageReport.split('\n').drop(1) // Ignorar la cabecera

                    // Inicializar contadores
                    def totalInstructionMissed = 0
                    def totalInstructionCovered = 0
                    def totalBranchMissed = 0
                    def totalBranchCovered = 0
                    def totalLineMissed = 0
                    def totalLineCovered = 0

                    // Iterar sobre cada línea y acumular los totales
                    lines.each { line ->
                        def columns = line.split(',')
                        if (columns.size() >= 8) { // Asegurarse de que hay suficientes columnas
                            totalInstructionMissed += columns[3].toInteger()
                            totalInstructionCovered += columns[4].toInteger()
                            totalBranchMissed += columns[5].toInteger()
                            totalBranchCovered += columns[6].toInteger()
                            totalLineMissed += columns[7].toInteger()
                            totalLineCovered += columns[8].toInteger()
                        }
                    }

                    // Calcular porcentajes
                    def instructionCoverage = (totalInstructionCovered * 100.0) / (totalInstructionCovered + totalInstructionMissed)
                    def branchCoverage = (totalBranchCovered * 100.0) / (totalBranchCovered + totalBranchMissed)
                    def lineCoverage = (totalLineCovered * 100.0) / (totalLineCovered + totalLineMissed)

                    // Mostrar la cobertura en consola
                    echo "Cobertura de instrucciones: ${instructionCoverage}%"
                    echo "Cobertura de ramas: ${branchCoverage}%"
                    echo "Cobertura de líneas: ${lineCoverage}%"

                    // Verificar si la cobertura está por debajo del umbral (en este caso, podemos usar líneas como referencia)
                    if (lineCoverage < env.COVERAGE_THRESHOLD) {
                        error "La cobertura de líneas está por debajo del umbral de ${env.COVERAGE_THRESHOLD}%"
                    }
                }
            }
        }
    }
}
