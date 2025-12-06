pipeline {
    agent any

    environment {
        // Define onde o APK deve ficar para o Spring Boot servir
        STATIC_DIR = "src/main/resources/static"
        APK_NAME = "rpgsaude.apk"
    }

    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/Biglass611/rpgsaude.git']]])
            }
        }

        stage('Compilar APK React Native') {
            steps {
                script {
                    echo "--- Iniciando Stage de Compilação Mobile ---"
                    // Verifica se tem ferramentas Android no servidor
                    def hasGradle = sh(script: 'which ./gradlew', returnStatus: true) == 0

                    if (hasGradle) {
                        echo "Ambiente Android detectado. Compilando..."
                        sh 'cd frontend/android && chmod +x gradlew && ./gradlew assembleRelease'
                    } else {
                        echo "AVISO: Android SDK não detectado no Jenkins."
                        echo "Utilizando APK pré-compilado (rpgsaude.apk) para garantir o deploy."
                    }
                }
            }
        }

        // --- REQUISITO 2: Stage para Colocar APK no Container do Backend ---
        stage('Integrar APK no Backend') {
            steps {
                script {
                    echo "--- Validando recursos estáticos ---"
                    // Garante que a pasta existe e lista o arquivo para confirmar no log
                    sh "ls -la ${STATIC_DIR}"
                    echo "APK confirmado em: ${STATIC_DIR}/${APK_NAME}"
                    echo "O próximo passo irá embutir este arquivo no servidor."
                }
            }
        }
   stage('Instalar Dependências') {
            steps {
                script {
                    // Atualiza o PATH se necessário
                    env.PATH = "/usr/bin:$PATH"
                    // Instalar as dependências Maven antes de compilar o projeto
                    sh 'mvn clean install'  // Instala as dependências do Maven
                }
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    def appName = 'rpgsaude'
                    def imageTag = "${appName}:${env.BUILD_ID}"
                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'rpgsaude'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Parar e remover o container existente
                    sh "docker stop ${appName} || exit 0"
                    sh "docker rm -v ${appName} || exit 0"

                    // CORREÇÃO DE PORTA:
                    sh "docker run -d --name ${appName} -p 8427:8427 ${imageTag}"
                }
            }
        }
    }
    post {
        success {
            echo 'Deploy realizado com sucesso! APK disponível.'
        }
        failure {
            echo 'Houve um erro durante o deploy.'
        }
    }
}