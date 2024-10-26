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
                    // Leer el archivo de cobertura en formato XML
                    def xmlReport = readFile 'gestion-empresa/target/site/jacoco/jacoco.xml'
                    def parser = new XmlSlurper().parseText(xmlReport)

                    // Extraer datos de cobertura
                    def totalCovered = parser.counter.find { it.@type == 'LINE' }.@covered.toInteger()
                    def totalMissed = parser.counter.find { it.@type == 'LINE' }.@missed.toInteger()

                    // Calcular la cobertura total
                    def totalLines = totalCovered + totalMissed
                    def coverage = (totalLines > 0) ? (totalCovered / totalLines) * 100 : 0
                    echo "Cobertura actual: ${coverage}%"

                    // Verificar si cumple con el umbral
                    if (coverage < COVERAGE_THRESHOLD) {
                        error "El porcentaje de cobertura es menor al ${COVERAGE_THRESHOLD}%. Cobertura actual: ${coverage}%"
                    }
                }
            }
        }

        // stage('Docker Compose') {
        //     when {
        //         expression { 
        //             // Solo si el coverage es >= 80%
        //             return coverage >= COVERAGE_THRESHOLD
        //         }
        //     }
        //     steps {
        //         // Construir y ejecutar Docker Compose
        //         sh 'cd gestion-empresa && sudo docker-compose -f target/docker-compose.yml up --build -d'
        //     }
        // }
    }
    // post {
    //     always {
    //         // Limpieza, apagar contenedores
    //         sh 'cd gestion-empresa && sudo docker-compose down'
    //     }
    //     success {
    //         echo 'Pipeline completado exitosamente.'
    //     }
    //     failure {
    //         echo 'El pipeline ha fallado.'
    //     }
    // }
}
