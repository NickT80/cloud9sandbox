import java.nio.file.* 

def appendTextAfterLine(String filePath, String searchText, String appendText) {
    File file = new File(filePath)
    if (!file.exists()) {
        println "File does not exist: $filePath"
        return
    }
    
    // Read the lines of the file
    List<String> lines = file.readLines()

    // Create a list to hold modified lines
    List<String> updatedLines = []

    // Flag to check if the line was found
    boolean found = false

    // Iterate over the lines and look for the search text
    lines.each { line ->
        updatedLines << line
        if (line.contains(searchText) && !found) {
            updatedLines << appendText
            found = true
        }
    }

    if (found) {
        // Write the updated lines back to the file
        file.text = updatedLines.join(System.lineSeparator())
        println "Text appended successfully!"
    } else {
        println "Text not found in the file."
    }
}

// Usage example:
appendTextAfterLine('example.md', 'Search Text', 'Appended Text')
