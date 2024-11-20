import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

// Sample JSON input
def artifactsJson = '''
[
    {"id": "1", "name": "artifact1", "uploaded": "2023-10-01T12:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "2", "name": "artifact2", "uploaded": "2023-10-15T14:30:00Z", "tags": {"name": "1.1.1.stringIdontwant"}},
    {"id": "3", "name": "artifact3", "uploaded": "2023-10-28T09:15:00Z", "tags": {"name": "2.2.2"}},
    {"id": "4", "name": "artifact4", "uploaded": "2023-10-30T11:00:00Z", "tags": {"name": "3.0.1"}},
    {"id": "5", "name": "artifact5", "uploaded": "2023-10-31T12:00:00Z", "tags": {"name": "invalid.tag"}},
    {"id": "6", "name": "artifact6", "uploaded": "2023-11-01T13:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "7", "name": "artifact7", "uploaded": "2023-11-02T14:00:00Z", "tags": {"name": "1.1.1"}}
]
'''

// Parse JSON data into a JSONArray
def jsonSlurper = new JsonSlurper()
def jsonArray = jsonSlurper.parseText(artifactsJson)

// Get current date and time in UTC
def currentDate = new Date()

// Create a calendar instance to calculate two weeks ago
def calendar = Calendar.getInstance()
calendar.setTime(currentDate)
calendar.add(Calendar.WEEK_OF_YEAR, -2)
def twoWeeksAgo = calendar.getTime()

// SimpleDateFormat for parsing the uploaded date string
def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"))

// Regex to match "digit.digit.digit"
def regex = /^\d+\.\d+\.\d+$/

// Lists for artifacts
def oldArtifacts = []
def recentArtifacts = []
def invalidTagArtifacts = []

// Loop through each artifact in the JSONArray
jsonArray.each { artifact ->
    def tagName = artifact.tags?.name?.toString()

    // Check if the tagName is valid
    if (tagName == null || !(tagName ==~ regex)) {
        invalidTagArtifacts.add(artifact)
        return // Skip further processing for this artifact
    }

    // Ensure the uploaded field is a string
    def uploadedString = artifact.uploaded.toString()
    try {
        // Parse the uploaded date string into a Date object
        def uploadedDate = dateFormat.parse(uploadedString)
        
        // Check if the artifact is older than two weeks
        if (uploadedDate.before(twoWeeksAgo)) {
            oldArtifacts.add(artifact)
        } else {
            recentArtifacts.add(artifact)
        }
    } catch (ParseException e) {
        println "Error parsing date for artifact ${artifact.id}: ${e.message}"
        // Handle the error, e.g., log it
    }
}

// Ensure at least 5 artifacts remain
if (recentArtifacts.size() < 5) {
    // Sort old artifacts by uploaded date in descending order
    oldArtifacts.sort { dateFormat.parse(it.uploaded.toString()).time }.reverse()
    
    // Add the most recent old artifacts to recentArtifacts until we have at least 5
    while (recentArtifacts.size() < 5 && !oldArtifacts.isEmpty()) {
        def artifactToAdd = oldArtifacts.remove(0)
        recentArtifacts.add(artifactToAdd)
    }
}

// Combine invalid tag artifacts and remaining old artifacts into the delete list
def artifactsToDelete = oldArtifacts + invalidTagArtifacts

// Remove artifacts to delete from the original jsonArray
artifactsToDelete.each { jsonArray.remove(it) }

// Print the remaining artifacts
println "Remaining Artifacts:"
jsonArray.each { artifact ->
    println "ID: ${artifact.id}, Name: ${artifact.name}, Uploaded: ${artifact.uploaded}, Tag: ${artifact.tags?.name}"
}

// Print the artifacts to delete
println "\nArtifacts to Delete:"
artifactsToDelete.each { artifact ->
    println "ID: ${artifact.id}, Name: ${artifact.name}, Uploaded: ${artifact.uploaded}, Tag: ${artifact.tags?.name}"
}
