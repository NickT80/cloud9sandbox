// First list
def list1 = [
    [id: "26", name: "artifact26", uploaded: "2023-11-01T17:00:00Z", tags: [name: "7.7.7"]],
    [id: "27", name: "artifact27", uploaded: "2023-11-02T19:30:00Z", tags: [name: "8.8.8"]],
    [id: "28", name: "artifact28", uploaded: "2023-11-03T08:00:00Z", tags: [name: "9.9.9"]],
    [id: "29", name: "artifact29", uploaded: "2023-10-29T09:30:00Z", tags: [name: "1.1.1"]],
    [id: "30", name: "artifact30", uploaded: "2023-11-01T10:15:00Z", tags: [name: "6.6.6"]],
    [id: "19", name: "artifact19", uploaded: "2023-10-19T14:15:00Z", tags: [name: "6.6.6"]],
    [id: "20", name: "artifact20", uploaded: "2023-11-03T09:00:00Z", tags: [name: "1.1.1"]],
    [id: "21", name: "artifact21", uploaded: "2023-10-31T18:00:00Z", tags: [name: "9.9.9"]]
]

// Second list
def list2 = [
    [id: "1", name: "artifact1", uploaded: "2023-10-01T12:00:00Z", tags: [name: "1.1.1"]],
    [id: "2", name: "artifact2", uploaded: "2023-10-15T14:30:00Z", tags: [name: "1.1.1.stringIdontwant"]],
    [id: "3", name: "artifact3", uploaded: "2023-10-28T09:15:00Z", tags: [name: "2.2.2"]],
    [id: "26", name: "artifact26", uploaded: "2023-11-01T17:00:00Z", tags: [name: "7.7.7"]],
    [id: "27", name: "artifact27", uploaded: "2023-11-02T19:30:00Z", tags: [name: "8.8.8"]],
    [id: "28", name: "artifact28", uploaded: "2023-11-03T08:00:00Z", tags: [name: "9.9.9"]]
]

// Subtract second list from the first, comparing by ID
def result = list1.findAll { item1 -> !list2.any { item2 -> item1.id == item2.id } }

// Print the resulting list
println "Resulting List:"
println result

def mergedList = (list1 + list2).unique { it.id }

println "Merged List (deduplicated by id):"
println mergedList

def mergedList = (list1 + list2)
    .groupBy { it.id }
    .collect { id, items -> 
        items.max { item -> item.uploaded }  // Keep the most recent by `uploaded`
    }

println "Merged List (deduplicated by id with priority):"
println mergedList
