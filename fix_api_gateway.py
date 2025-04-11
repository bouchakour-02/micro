#!/usr/bin/env python

with open('api-gateway/pom.xml', 'r') as f:
    lines = f.readlines()

# Replace line 14 (index 13) with the correct tag
lines[13] = '    <name>api-gateway</name>\n'

with open('api-gateway/pom.xml', 'w') as f:
    f.writelines(lines)

print("Fixed api-gateway/pom.xml") 