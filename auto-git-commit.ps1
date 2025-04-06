# Auto Git Commit and Push Script
# This script automatically adds all changes, commits with a timestamp, and pushes to the remote repository

# Get current timestamp for the commit message
$timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
$commitMessage = "Auto commit at $timestamp"

# Add all files
Write-Host "Adding all changes to Git..." -ForegroundColor Green
git add .

# Commit with timestamp
Write-Host "Committing changes with message: $commitMessage" -ForegroundColor Green
git commit -m "$commitMessage"

# Check if remote repository is configured
$remoteExists = git remote -v
if (!$remoteExists) {
    $remoteUrl = Read-Host "No remote repository found. Enter your GitHub repository URL"
    git remote add origin $remoteUrl
    Write-Host "Remote 'origin' added with URL: $remoteUrl" -ForegroundColor Green
}

# Push to remote repository
try {
    Write-Host "Pushing changes to remote repository..." -ForegroundColor Green
    git push -u origin master
    Write-Host "Successfully pushed changes!" -ForegroundColor Green
} catch {
    Write-Host "Failed to push changes. Error: $_" -ForegroundColor Red
    Write-Host "Make sure your remote repository is properly configured." -ForegroundColor Yellow
}

Write-Host "Git operations completed." -ForegroundColor Cyan 