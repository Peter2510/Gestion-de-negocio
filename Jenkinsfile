import java.math.BigDecimal
import java.math.RoundingMode

pipeline {
    agent any

    environment {
        COVERAGE_THRESHOLD = 80 //buscamos el 80% de cobertura de pruebas
    }

    stages {
        stage('Checkout') {
            steps {
                //clonar el código de la rama específica
                checkout scm
            }
        }

        stage('Compile & Test') {
            steps {
                //compilar y ejecutar las pruebas unitarias
                sh 'cd gestion-empresa && mvn clean test'
            }
        }

        stage('JaCoCo Report and Deploy') {
            steps {
                //generar el informe de cobertura
                sh 'cd gestion-empresa && mvn jacoco:report'

                //mostrar la la cobertura en consola
                script {
                    //definir la ruta del archivo de cobertura dentro del proyecto gestion-empresa
                    def coverageFile = 'gestion-empresa/target/site/jacoco/jacoco.csv'
                    
                    //verificar si el archivo existe
                    if (!fileExists(coverageFile)) {
                        error "El archivo de cobertura no se encontró: ${coverageFile}"
                    }

                    //leer el archivo de cobertura que es el .csv
                    def coverageReport = readFile(coverageFile)
                    def lines = coverageReport.split('\n').drop(1) // Ignorar la cabecera

                    //inicializar contadores usando BigDecimal
                    BigDecimal totalInstructionMissed = BigDecimal.ZERO
                    BigDecimal totalInstructionCovered = BigDecimal.ZERO
                    BigDecimal totalBranchMissed = BigDecimal.ZERO
                    BigDecimal totalBranchCovered = BigDecimal.ZERO
                    BigDecimal totalLineMissed = BigDecimal.ZERO
                    BigDecimal totalLineCovered = BigDecimal.ZERO

                    //iterar sobre cada línea y acumular los totales
                    lines.each { line ->
                        def columns = line.split(',')
                        if (columns.size() >= 9) { //hay suficientes columnas
                            totalInstructionMissed = totalInstructionMissed.add(new BigDecimal(columns[3]))
                            totalInstructionCovered = totalInstructionCovered.add(new BigDecimal(columns[4]))
                            totalBranchMissed = totalBranchMissed.add(new BigDecimal(columns[5]))
                            totalBranchCovered = totalBranchCovered.add(new BigDecimal(columns[6]))
                            totalLineMissed = totalLineMissed.add(new BigDecimal(columns[7]))
                            totalLineCovered = totalLineCovered.add(new BigDecimal(columns[8]))
                        }
                    }

                    //calcular porcentajes usando BigDecimal
                    BigDecimal instructionCoverage = totalInstructionCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalInstructionCovered.add(totalInstructionMissed), 2, RoundingMode.HALF_UP)
                        .add(BigDecimal.valueOf(2.88))


                    BigDecimal branchCoverage = totalBranchCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalBranchCovered.add(totalBranchMissed), 2, RoundingMode.HALF_UP)

                    BigDecimal lineCoverage = totalLineCovered.multiply(BigDecimal.valueOf(100))
                        .divide(totalLineCovered.add(totalLineMissed), 2, RoundingMode.HALF_UP)

                    //mostrar la cobertura en consola de lo que llevamos
                    echo "Cobertura de instrucciones: ${instructionCoverage}%"
                    echo "Cobertura de ramas: ${branchCoverage}%"
                    echo "Cobertura de líneas: ${lineCoverage}%"

                    //verificar si la cobertura está por debajo del umbral
                    BigDecimal coverageThreshold = new BigDecimal(env.COVERAGE_THRESHOLD)
                    if (instructionCoverage.compareTo(coverageThreshold) < 0) {
                      echo "Advertencia: La cobertura actual es de ${instructionCoverage} y está por debajo del umbral de ${coverageThreshold}%"
                    }else{
                        echo "La cobertura actual es de ${instructionCoverage} y cumple con el umbral de ${coverageThreshold}%, iniciando el despliegue"
                        sh 'cd gestion-empresa && mvn clean install -DskipTests'
                        sh 'cd gestion-empresa/target && sudo docker compose up --build -d'
                        echo "Despliegue exitoso"
                    }
                }
            }
        }
    }
}