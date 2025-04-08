with open('microservice/pom.xml', 'r') as f:
    content = f.read()
    
# Define the tags
old_tag_open = '<n>'
old_tag_close = '</n>'
new_tag_open = '<name>'
new_tag_close = '</name>'
tag_content = 'insurance-recommendation-service'

# Replace tags
old_tag = old_tag_open + tag_content + old_tag_close
new_tag = new_tag_open + tag_content + new_tag_close
fixed_content = content.replace(old_tag, new_tag)

with open('microservice/pom.xml', 'w') as f:
    f.write(fixed_content)
    
print(f"Fixed pom.xml file: replaced {old_tag} with {new_tag}") 