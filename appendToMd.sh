#!/bin/bash 

# Function to insert text at a particular line number in a file
append_text_at_line() {
    local file_path=$1
    local line_number=$2
    local append_text=$3

    if [[ ! -f "$file_path" ]]; then
        echo "File does not exist: $file_path"
        return 1
    fi

    # Count total number of lines in the file
    total_lines=$(wc -l < "$file_path")

    # Check if the line number is within the file's range
    if (( line_number > 0 && line_number <= total_lines + 1 )); then
        # Use awk to insert the text at the specific line number
        awk -v append="$append_text" -v line="$line_number" 'NR == line {print append} {print}' "$file_path" > temp.md && mv temp.md "$file_path"

        echo "Text appended successfully at line $line_number!"
    else
        echo "Line number $line_number is out of range."
    fi
}
