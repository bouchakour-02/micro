#!/usr/bin/env python
import os

def fix_pom_files():
    dirs = ["microservice", "discovery-server", "api-gateway"]
    fixed_files = 0
    
    for directory in dirs:
        pom_file = os.path.join(directory, "pom.xml")
        if os.path.exists(pom_file):
            try:
                with open(pom_file, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                # Replace <n> with <name> and </n> with </name>
                new_content = content.replace("<n>", "<name>").replace("</n>", "</name>")
                
                if content != new_content:
                    with open(pom_file, 'w', encoding='utf-8') as f:
                        f.write(new_content)
                    print(f"Successfully fixed {pom_file}")
                    fixed_files += 1
                else:
                    print(f"No changes needed in {pom_file}")
            except Exception as e:
                print(f"Error processing {pom_file}: {str(e)}")
    
    print(f"Fixed {fixed_files} files.")

if __name__ == "__main__":
    fix_pom_files() 