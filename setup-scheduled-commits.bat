@echo off
echo Setting up scheduled automatic Git commits...
powershell -ExecutionPolicy Bypass -File setup-scheduled-commits.ps1
pause 