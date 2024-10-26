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

 stages {
        stage('Code Coverage') {
            steps {
                script {
                    // Leer el archivo de cobertura en formato XML
                    def xmlReport = readFile 'gestion-empresa/target/site/jacoco/jacoco.xml'
                    
                    // Usar expresiones regulares para obtener la cobertura de líneas
                    def matcher = xmlReport =~ /<counter type="LINE" missed="(\d+)" covered="(\d+)"/
                    def totalCovered = 0
                    def totalMissed = 0

                    matcher.each { 
                        totalMissed += it[0][1].toInteger()
                        totalCovered += it[0][2].toInteger()
                    }

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
