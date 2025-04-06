# Setup Scheduled Git Commits
# This script creates a Windows scheduled task that runs the auto-git-commit.ps1 script at specified intervals

# Get the current directory (where the scripts are located)
$scriptPath = Get-Location
$scriptFullPath = Join-Path -Path $scriptPath -ChildPath "auto-git-commit.ps1"

Write-Host "Setting up scheduled task for automatic Git commits..." -ForegroundColor Green

# Ask for the interval
Write-Host "How often do you want the auto-commits to run?" -ForegroundColor Yellow
Write-Host "1: Hourly" -ForegroundColor Cyan
Write-Host "2: Daily" -ForegroundColor Cyan
Write-Host "3: Weekly" -ForegroundColor Cyan
$choice = Read-Host "Enter your choice (1, 2, or 3)"

# Set up the trigger based on user choice
switch ($choice) {
    "1" { 
        $trigger = New-ScheduledTaskTrigger -Once -At (Get-Date) -RepetitionInterval (New-TimeSpan -Hours 1)
        $frequencyText = "hourly"
    }
    "2" { 
        $trigger = New-ScheduledTaskTrigger -Daily -At "9:00 AM"
        $frequencyText = "daily at 9:00 AM"
    }
    "3" { 
        $trigger = New-ScheduledTaskTrigger -Weekly -DaysOfWeek Sunday -At "9:00 AM"
        $frequencyText = "weekly on Sunday at 9:00 AM"
    }
    default { 
        $trigger = New-ScheduledTaskTrigger -Once -At (Get-Date) -RepetitionInterval (New-TimeSpan -Hours 1)
        $frequencyText = "hourly"
        Write-Host "Invalid choice. Defaulting to hourly schedule." -ForegroundColor Yellow
    }
}

# Create the action (run the script)
$action = New-ScheduledTaskAction -Execute "powershell.exe" -Argument "-ExecutionPolicy Bypass -File `"$scriptFullPath`""

# Register the scheduled task
$taskName = "AutoGitCommit"
$taskDescription = "Automatically commits and pushes changes to Git $frequencyText"

try {
    Register-ScheduledTask -TaskName $taskName -Trigger $trigger -Action $action -Description $taskDescription -RunLevel Highest -Force
    Write-Host "Scheduled task '$taskName' created successfully! It will run $frequencyText." -ForegroundColor Green
} catch {
    Write-Host "Failed to create scheduled task. Error: $_" -ForegroundColor Red
    Write-Host "You can manually run the auto-git-commit.ps1 script whenever you want to commit changes." -ForegroundColor Yellow
}

Write-Host "Setup complete. You can manage this task in Windows Task Scheduler." -ForegroundColor Cyan 