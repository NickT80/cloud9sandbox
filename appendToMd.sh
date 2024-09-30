#!/bin/bash 

# Function to append text after a matching line
append_text_after_line() {
    local file_path=$1
    local search_text=$2
    local append_text=$3

    if [[ ! -f "$file_path" ]]; then
        echo "File does not exist: $file_path"
        return 1
    fi

    # Check if search_text is found and append if found
    if grep -q "$search_text" "$file_path"; then
        awk -v search="$search_text" -v append="$append_text" '
        { print }
        $0 ~ search { print append }
        ' "$file_path" > "${file_path}.tmp" && mv "${file_path}.tmp" "$file_path"
        echo "Text appended successfully!"
    else
        echo "Text not found in the file."
    fi
}

# Usage example:
# Pass file path, search text, and text to append
append_text_after_line "example.md" "Search Text" "Appended Text"
