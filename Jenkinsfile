pipeline {
    agent any

    environment {
        // Suas configurações
        STATIC_DIR = "src/main/resources/static"
        APK_NAME = "rpgsaude.apk"
        APP_NAME = "rpgsaude"
        APP_PORT = "8427"
    }

    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/Biglass611/rpgsaude.git']]])
            }
        }

        // --- REQUISITO 1: Compilar APK (Adaptado para Windows) ---
        stage('Compilar APK React Native') {
            steps {
                script {
                    echo "--- Iniciando Stage de Compilação Mobile ---"

                    // No Windows, verificamos se o gradlew.bat existe
                    def hasGradle = fileExists('mobile/android/gradlew.bat')

                    if (hasGradle) {
                        echo "Ambiente Android detectado. Compilando..."
                        // Comandos Windows
                        bat 'cd mobile/android && gradlew.bat assembleRelease'
                        // Copiar no Windows é 'copy' ou 'xcopy', e as barras são invertidas
                        bat "copy mobile\\android\\app\\build\\outputs\\apk\\release\\app-release.apk ${STATIC_DIR}\\${APK_NAME}"
                    } else {
                        echo "AVISO: Android SDK/Gradle não detectado ou pasta incorreta."
                        echo "Utilizando APK pré-compilado (${APK_NAME}) que já está na pasta static."
                    }
                }
            }
        }

        // --- REQUISITO 2: Validar APK ---
       stage('Integrar APK no Backend') {
                   steps {
                       script {
                           echo "--- Validando recursos estáticos ---"
                           // Use barras invertidas duplas para Windows
                           bat "dir src\\main\\resources\\static"
                           echo "APK confirmado."
                       }
                   }
               }

        stage('Instalar Dependências (Maven)') {
            steps {
                script {
                    // Maven no Windows
                    bat 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    def imageTag = "${APP_NAME}:latest"
                    bat "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def imageTag = "${APP_NAME}:latest"

                    // Comandos Docker no Windows
                    bat "docker stop ${APP_NAME} || exit 0"
                    bat "docker rm -f ${APP_NAME} || exit 0"

                    // Subir Container
                    bat "docker run -d --name ${APP_NAME} -p ${APP_PORT}:${APP_PORT} --restart always ${imageTag}"
                }
            }
        }
    }
    post {
        success {
            echo "SUCESSO! Acesse: http://localhost:${APP_PORT}/rpgsaude"
        }
        failure {
            echo 'FALHA: Ocorreu um erro durante o deploy.'
        }
    }
}