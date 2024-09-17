/**
 * Compares two version strings.
 *
 * @param version1 First version string (e.g., "1.0.0")
 * @param version2 Second version string (e.g., "1.0.1")
 * @return 0 if versions are equal, -1 if version1 < version2, 1 if version1 > version2
 */
def compareVersions(String version1, String version2) {
    // Split the version strings into parts (e.g., ["1", "0", "0"])
    def parts1 = version1.tokenize('.')
    def parts2 = version2.tokenize('.')

    // Determine the maximum length to compare each part
    int length = Math.max(parts1.size(), parts2.size())

    // Loop through each part and compare
    for (int i = 0; i < length; i++) {
        int v1 = (i < parts1.size()) ? parts1[i].toInteger() : 0
        int v2 = (i < parts2.size()) ? parts2[i].toInteger() : 0

        if (v1 < v2) return -1
        if (v1 > v2) return 1
    }

    // If all parts are equal
    return 0
}

// Example usage:
println compareVersions("1.2.0", "1.2")   // Output: 0
println compareVersions("1.0.0", "1.0.1") // Output: -1
println compareVersions("2.1.0", "2.0.9") // Output: 1


pipeline {
    agent any
    stages {
        stage('Version Check') {
            steps {
                script {
                    def result = compareVersions("1.0.0", "1.1.0")
                    if (result == -1) {
                        echo "Upgrade required."
                    } else {
                        echo "No upgrade needed."
                    }
                }
            }
        }
    }
}
