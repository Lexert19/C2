import socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("127.0.0.1",7676))
s.send(b"/connections\n")
s.send(b"/count\n")
s.close()