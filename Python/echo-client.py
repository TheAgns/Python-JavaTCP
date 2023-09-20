import socket

HOST = "10.50.137.99"  # which server to connect to
# PORT = 65432  # The same port as used by the server
PORT = 12345

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    s.sendall(b"Hello, world")
    data = s.recv(1024)

print(f"Received {data!r}")
