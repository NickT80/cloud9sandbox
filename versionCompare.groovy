**/
 * Compares two version strings and determines the type of version change.
 *
 * @param version1 First version string (e.g., "1.0.0")
 * @param version2 Second version string (e.g., "1.0.1")
 * @return Map containing comparison result and change type:
 *         - result: 0 if versions are equal, -1 if version1 < version2, 1 if version1 > version2
 *         - changeType: "major", "minor", "patch", or "none"
 */
def compareVersions(String version1, String version2) {
    // Split the version strings into parts (e.g., ["1", "0", "0"])
    def parts1 = version1.tokenize('.')
    def parts2 = version2.tokenize('.')

    // Determine the maximum length to compare each part
    int length = Math.max(parts1.size(), parts2.size())

    // Initialize version parts
    int majorChange = 0
    int minorChange = 0
    int patchChange = 0

    // Loop through each part and compare
    for (int i = 0; i < length; i++) {
        int v1 = (i < parts1.size()) ? parts1[i].toInteger() : 0
        int v2 = (i < parts2.size()) ? parts2[i].toInteger() : 0

        if (v1 < v2) {
            if (i == 0) majorChange = 1
            else if (i == 1) minorChange = 1
            else if (i == 2) patchChange = 1
            return [result: -1, changeType: (majorChange ? "major" : (minorChange ? "minor" : "patch"))]
        }

        if (v1 > v2) {
            if (i == 0) majorChange = 1
            else if (i == 1) minorChange = 1
            else if (i == 2) patchChange = 1
            return [result: 1, changeType: (majorChange ? "major" : (minorChange ? "minor" : "patch"))]
        }
    }

    // If all parts are equal
    return [result: 0, changeType: "none"]
}

// Example usage:
println compareVersions("1.2.0", "1.2.1")   // Output: [result:-1, changeType:patch]
println compareVersions("1.0.0", "2.0.0")   // Output: [result:-1, changeType:major]
println compareVersions("2.1.0", "2.2.0")   // Output: [result:-1, changeType:minor]
println compareVersions("1.2.3", "1.2.3")   // Output: [result:0, changeType:none]
