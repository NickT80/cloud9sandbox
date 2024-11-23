import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

// Sample JSON input (replace this with the expanded list)
def artifactsJson = '''
[   
    {"id": "1", "name": "artifact1", "uploaded": "2023-10-01T12:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "2", "name": "artifact2", "uploaded": "2023-10-15T14:30:00Z", "tags": {"name": "1.1.1.stringIdontwant"}},
    {"id": "3", "name": "artifact3", "uploaded": "2023-10-28T09:15:00Z", "tags": {"name": "2.2.2"}},
    {"id": "4", "name": "artifact4", "uploaded": "2023-10-30T11:00:00Z", "tags": {"name": "3.0.1"}},
    {"id": "5", "name": "artifact5", "uploaded": "2023-10-31T12:00:00Z", "tags": {"name": "invalid.tag"}},
    {"id": "6", "name": "artifact6", "uploaded": "2023-11-01T13:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "7", "name": "artifact7", "uploaded": "2023-11-02T14:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "8", "name": "artifact8", "uploaded": "2023-10-29T10:00:00Z", "tags": {"name": "4.4.4"}},
    {"id": "9", "name": "artifact9", "uploaded": "2023-10-25T16:45:00Z", "tags": {"name": "4.4.4.extra"}},
    {"id": "10", "name": "artifact10", "uploaded": "2023-10-20T15:15:00Z", "tags": {"name": "5.5.5"}},
    {"id": "11", "name": "artifact11", "uploaded": "2023-10-18T13:30:00Z", "tags": {"name": "1.1.1"}},
    {"id": "12", "name": "artifact12", "uploaded": "2023-10-22T14:30:00Z", "tags": {"name": "2.2.2.extra"}},
    {"id": "13", "name": "artifact13", "uploaded": "2023-10-26T17:30:00Z", "tags": {"name": "invalid"}},
    {"id": "14", "name": "artifact14", "uploaded": "2023-10-24T11:00:00Z", "tags": {"name": "7.7.7"}},
    {"id": "15", "name": "artifact15", "uploaded": "2023-10-28T14:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "16", "name": "artifact16", "uploaded": "2023-10-27T12:00:00Z", "tags": {"name": "8.8.8"}},
    {"id": "17", "name": "artifact17", "uploaded": "2023-11-01T08:15:00Z", "tags": {"name": "3.0.3"}},
    {"id": "18", "name": "artifact18", "uploaded": "2023-11-02T10:45:00Z", "tags": {"name": "invalid"}},
    {"id": "19", "name": "artifact19", "uploaded": "2023-10-19T14:15:00Z", "tags": {"name": "6.6.6"}},
    {"id": "20", "name": "artifact20", "uploaded": "2023-11-03T09:00:00Z", "tags": {"name": "1.1.1"}},
    {"id": "21", "name": "artifact21", "uploaded": "2023-10-31T18:00:00Z", "tags": {"name": "9.9.9"}},
    {"id": "22", "name": "artifact22", "uploaded": "2023-11-02T14:30:00Z", "tags": {"name": "invalid"}},
    {"id": "23", "name": "artifact23", "uploaded": "2023-10-15T09:45:00Z", "tags": {"name": "1.1.1.stringIdontwant"}},
    {"id": "24", "name": "artifact24", "uploaded": "2023-10-20T11:00:00Z", "tags": {"name": "2.2.2"}},
    {"id": "25", "name": "artifact25", "uploaded": "2023-10-30T16:00:00Z", "tags": {"name": "5.5.5.extra"}},
    {"id": "26", "name": "artifact26", "uploaded": "2023-11-01T17:00:00Z", "tags": {"name": "7.7.7"}},
    {"id": "27", "name": "artifact27", "uploaded": "2023-11-02T19:30:00Z", "tags": {"name": "8.8.8"}},
    {"id": "28", "name": "artifact28", "uploaded": "2023-11-03T08:00:00Z", "tags": {"name": "9.9.9"}},
    {"id": "29", "name": "artifact29", "uploaded": "2023-10-29T09:30:00Z", "tags": {"name": "1.1.1"}},
    {"id": "30", "name": "artifact30", "uploaded": "2023-11-01T10:15:00Z", "tags": {"name": "6.6.6"}}
]
'''

// Parse JSON data into a JSONArray
def jsonSlurper = new JsonSlurper()
def jsonArray = jsonSlurper.parseText(artifactsJson)

// SimpleDateFormat for parsing the uploaded date string
def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"))

// Regex to match "digit.digit.digit"
def regex = /^\d+\.\d+\.\d+$/

// Filter artifacts to match regex
def filteredArtifacts = jsonArray.findAll { artifact ->
    def tagName = artifact.tags?.name?.toString()
    tagName ==~ regex
}

// Sort filtered artifacts by date (descending, latest first)
filteredArtifacts.sort { artifact ->
    -dateFormat.parse(artifact.uploaded.toString()).time
}

// Separate keep and delete lists
def keepList = filteredArtifacts.take(5) // Keep the 5 latest
def deleteList = filteredArtifacts - keepList

// Print the keep list
println "Keep List:"
keepList.each { artifact ->
    println "ID: ${artifact.id}, Name: ${artifact.name}, Uploaded: ${artifact.uploaded}, Tag: ${artifact.tags?.name}"
}

// Print the delete list
println "\nDelete List:"
deleteList.each { artifact ->
    println "ID: ${artifact.id}, Name: ${artifact.name}, Uploaded: ${artifact.uploaded}, Tag: ${artifact.tags?.name}"
}
