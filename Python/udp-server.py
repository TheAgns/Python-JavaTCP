import socket

HOST = "10.50.130.79"  # Listen on all available network interfaces
PORT = 12345  # Choose a port for your server

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.bind((HOST, PORT))
    print(f"Server is listening on {HOST}:{PORT}")

    while True:
        data, addr = s.recvfrom(1024)
        print(f"Received from {addr}: {data.decode()}")
        s.sendto(data, addr)  # Echo the received data back to the client
