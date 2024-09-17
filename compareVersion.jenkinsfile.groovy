pipeline {
    agent any
    stages {
        stage('Check Version') {
            steps {
                script {
                    def comparison = compareVersions("1.0.0", "1.1.0")
                    echo "Comparison Result: ${comparison.result}, Change Type: ${comparison.changeType}"

                    if (comparison.result == -1) {
                        if (comparison.changeType == "major") {
                            echo "Performing major version upgrade..."
                            // Add steps for major upgrade
                        } else if (comparison.changeType == "minor") {
                            echo "Performing minor version upgrade..."
                            // Add steps for minor upgrade
                        } else if (comparison.changeType == "patch") {
                            echo "Performing patch update..."
                            // Add steps for patch update
                        }
                    } else {
                        echo "No upgrade needed."
                    }
                }
            }
        }
    }
}
