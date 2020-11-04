cd "C:\Program Files\MacroCreator"
.\MacroCreator.exe "C:\Users\yttak\Desktop\hogehoge\hogehoge.pmc"

Add-Type -AssemblyName System.Windows.Forms
Start-Sleep -m 500

[System.Windows.Forms.SendKeys]::SendWait("%{TAB}")
Start-Sleep -m 10000
[System.Windows.Forms.SendKeys]::SendWait("^+~")

Start-Sleep -m 60