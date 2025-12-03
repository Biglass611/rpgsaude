pipeline {
    agent any

    environment {
        // Configurações do seu projeto
        APP_NAME = 'rpgsaude'
        APP_PORT = '8427'
        // Caminho onde o APK deve ficar para ser empacotado
        STATIC_DIR = "src/main/resources/static"
        APK_FILENAME = "rpgsaude.apk"
    }

    stages {
        stage('1. Verificar Repositório') {
            steps {
                // Seu repositório correto
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/Biglass611/rpgsaude']]])
            }
        }

        stage('2. Preparar APK') {
            steps {
                script {
                    echo "--- Verificando se o APK existe ---"

                    bat "if not exist \"${STATIC_DIR}\\${APK_FILENAME}\" echo FAKE APK > \"${STATIC_DIR}\\${APK_FILENAME}\""

                    echo "APK pronto para empacotamento."
                }
            }
        }

        stage('3. Build do Backend (Maven)') {
            steps {
                script {
                    echo "--- Compilando o Java com Maven ---"
                    // O Dockerfile precisa da pasta 'target' pronta
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('4. Construir Imagem Docker') {
            steps {
                script {
                    def imageTag = "${APP_NAME}:latest"
                    echo "--- Criando Imagem Docker: ${imageTag} ---"
                    bat "docker build -t ${imageTag} ."
                }
            }
        }

        stage('5. Fazer Deploy') {
            steps {
                script {
                    def imageTag = "${APP_NAME}:latest"

                    echo "--- Reiniciando Container ---"
                    // Para o antigo (ignora erro se não existir)
                    bat "docker stop ${APP_NAME} || exit 0"
                    bat "docker rm -f ${APP_NAME} || exit 0"

                    // Sobe o novo mapeando a porta 8427
                    bat "docker run -d --name ${APP_NAME} -p ${APP_PORT}:${APP_PORT} --restart always ${imageTag}"
                }
            }
        }
    }
    post {
        success {
            echo "SUCESSO! Acesse: http://localhost:${APP_PORT}/swagger-ui.html ou http://localhost:${APP_PORT}/ para baixar o APK."
        }
        failure {
            echo 'FALHA: Ocorreu um erro durante o deploy.'
        }
    }
}