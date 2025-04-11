import os

def fix_pom_file(file_path):
    try:
        # Read the file
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        
        # Replace <n> with <name> and </n> with </name>
        new_content = content.replace("<n>", "<name>").replace("</n>", "</name>")
        
        # Write back only if changes were made
        if content != new_content:
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(new_content)
            print(f"Successfully fixed {file_path}")
            return True
        else:
            print(f"No changes needed in {file_path}")
            return False
    except Exception as e:
        print(f"Error processing {file_path}: {e}")
        return False

# Fix pom.xml files in these directories
dirs = ["microservice", "discovery-server", "api-gateway"]
fixed_count = 0

for directory in dirs:
    pom_path = os.path.join(directory, "pom.xml")
    if os.path.exists(pom_path):
        if fix_pom_file(pom_path):
            fixed_count += 1

print(f"Fixed {fixed_count} files.") 
