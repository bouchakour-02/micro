import os
import sys
import re

def replace_n_with_name(filename):
    try:
        with open(filename, 'r', encoding='utf-8') as file:
            content = file.read()
        
        # Replace <n> with <name> and </n> with </name>
        new_content = content.replace("<n>", "<name>").replace("</n>", "</name>")
        
        if content != new_content:
            with open(filename, 'w', encoding='utf-8') as file:
                file.write(new_content)
            print(f"Successfully updated {filename}")
            return True
        else:
            print(f"No changes needed in {filename}")
            return False
    except Exception as e:
        print(f"Error processing {filename}: {str(e)}")
        return False

def main():
    dirs = ["microservice", "discovery-server", "api-gateway"]
    fixed_files = 0
    
    for directory in dirs:
        pom_file = os.path.join(directory, "pom.xml")
        if os.path.exists(pom_file):
            if replace_n_with_name(pom_file):
                fixed_files += 1
    
    print(f"Fixed {fixed_files} files.")

if __name__ == "__main__":
    main() 
