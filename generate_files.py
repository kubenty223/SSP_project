import os

def generate_file(file_path, size_in_mb):
    # 1 MB is 1024 * 1024 bytes
    size_in_bytes = size_in_mb * 1024 * 1024

    with open(file_path, 'wb') as file:
        # Write zeros to fill the file with the desired size
        file.write(b'\0' * size_in_bytes)

# Specify file sizes in megabytes
file_sizes = [10, 50, 100]

for size in file_sizes:
    file_name = "{}mb_file.txt".format(size)
    generate_file(file_name, size)
    print("Generated {}".format(file_name))
