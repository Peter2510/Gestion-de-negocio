import java.math.BigDecimal
import java.math.RoundingMode

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

                    // Inicializar contadores usando BigDecimal
                    BigDecimal totalInstructionMissed = BigDecimal.ZERO
                    BigDecimal totalInstructionCovered = BigDecimal.ZERO
                    BigDecimal totalBranchMissed = BigDecimal.ZERO
                    BigDecimal totalBranchCovered = BigDecimal.ZERO
                    BigDecimal totalLineMissed = BigDecimal.ZERO
                    BigDecimal totalLineCovered = BigDecimal.ZERO

                    // Iterar sobre cada línea y acumular los totales
                    lines.each { line ->
                        def columns = line.split(',')
                        if (columns.size() >= 9) { // Asegúrate de que hay suficientes columnas
                            totalInstructionMissed = totalInstructionMissed.add(new BigDecimal(columns[3]))
                            totalInstructionCovered = totalInstructionCovered.add(new BigDecimal(columns[4]))
                            totalBranchMissed = totalBranchMissed.add(new BigDecimal(columns[5]))
                            totalBranchCovered = totalBranchCovered.add(new BigDecimal(columns[6]))
                            totalLineMissed = totalLineMissed.add(new BigDecimal(columns[7]))
                            totalLineCovered = totalLineCovered.add(new BigDecimal(columns[8]))
                        }
                    }

                    // Calcular porcentajes usando BigDecimal
                    BigDecimal instructionCoverage = totalInstructionCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalInstructionCovered.add(totalInstructionMissed), 2, RoundingMode.HALF_UP)

                    BigDecimal branchCoverage = totalBranchCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalBranchCovered.add(totalBranchMissed), 2, RoundingMode.HALF_UP)

                    BigDecimal lineCoverage = totalLineCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalLineCovered.add(totalLineMissed), 2, RoundingMode.HALF_UP)

                    // Mostrar la cobertura en consola
                    echo "Cobertura de instrucciones: ${instructionCoverage}%"
                    echo "Cobertura de ramas: ${branchCoverage}%"
                    echo "Cobertura de líneas: ${lineCoverage}%"

                    // Verificar si la cobertura está por debajo del umbral
                    BigDecimal coverageThreshold = new BigDecimal(env.COVERAGE_THRESHOLD)
                    if (lineCoverage.compareTo(coverageThreshold) < 0) {
                        error "La cobertura de líneas está por debajo del umbral de ${coverageThreshold}%"
                    }
                }
            }
        }
    }
}
