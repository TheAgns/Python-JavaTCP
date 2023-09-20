import socket

HOST = "10.50.130.79"  # IP address of the server
PORT = 12345  # The same port as used by the server

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    while True:
        message = input("Enter a message (or 'exit' to quit): ")

        if message.lower() == 'exit':
            break

        s.sendto(message.encode(), (HOST, PORT))
        data, addr = s.recvfrom(1024)
        print(f"Received from {addr}: {data.decode()}")

print("Connection closed.")
