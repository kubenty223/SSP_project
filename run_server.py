import SimpleHTTPServer
import SocketServer
import json
import random
import os

class MyHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path.startswith('/file/'):
            # Extract filename from the path
            filename = self.path[len('/file/'):]
            
            # Check if the file exists
            if os.path.exists(filename):
                try:
                    with open(filename, 'rb') as file:
                        content = file.read()
                        self.send_response(200)
                        self.send_header('Content-type', 'application/octet-stream')
                        self.send_header('Content-Disposition', 'attachment; filename={}'.format(filename))
                        self.end_headers()
                        self.wfile.write(content)
                except IOError:
                    self.send_response(500)
                    self.end_headers()
                    self.wfile.write(b'Internal Server Error')
            else:
                self.send_response(404)
                self.end_headers()
                self.wfile.write(b'File not found')
        elif self.path == '/load':
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()

            random_number = random.randint(1, 100)
            response_data = random_number
            response_json = json.dumps(response_data)

            self.wfile.write(response_json)
        else:
            # Default behavior for other paths
            SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

# Set up the server
PORT = 6789
Handler = MyHandler
httpd = SocketServer.TCPServer(("", PORT), Handler)

print("Server running on port", PORT)

# Start the server
httpd.serve_forever()

