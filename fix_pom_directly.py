import os

def fix_pom_file(file_path):
    # Try different encodings
    encodings = ['utf-8', 'utf-8-sig', 'latin1', 'cp1252']
    
    for encoding in encodings:
        try:
            print(f"Trying encoding: {encoding}")
            # Read the file line by line
            with open(file_path, 'r', encoding=encoding) as file:
                lines = file.readlines()
            
            # Replace <n> with <name> and </n> with </name> on each line
            changed = False
            for i in range(len(lines)):
                if "<n>" in lines[i] and "</n>" in lines[i]:
                    old_line = lines[i]
                    lines[i] = lines[i].replace("<n>", "<name>").replace("</n>", "</name>")
                    if old_line != lines[i]:
                        changed = True
                        print(f"Line {i+1}: '{old_line.strip()}' -> '{lines[i].strip()}'")
            
            # Write back only if changes were made
            if changed:
                with open(file_path, 'w', encoding=encoding) as file:
                    file.writelines(lines)
                print(f"Successfully fixed {file_path}")
                return True
            else:
                print(f"No changes needed in {file_path}")
                return False
        except Exception as e:
            print(f"Error with encoding {encoding}: {e}")
    
    print(f"Failed to process {file_path} with all encodings")
    return False

# Fix the microservice pom.xml file
pom_path = "microservice/pom.xml"
if os.path.exists(pom_path):
    fix_pom_file(pom_path)
else:
    print(f"File not found: {pom_path}")
    # Try Windows path style
    pom_path = "microservice\\pom.xml"
    if os.path.exists(pom_path):
        fix_pom_file(pom_path)
    else:
        print(f"File not found: {pom_path}") 
