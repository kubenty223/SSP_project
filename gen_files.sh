#!/bin/bash


# Utwórz plik o rozmiarze 50 MB
dd if=/dev/zero of=file_1.txt bs=1M count=50

# Utwórz plik o rozmiarze 100 MB
dd if=/dev/zero of=file_2.txt bs=1M count=100

# Utwórz plik o rozmiarze 150 MB
dd if=/dev/zero of=file_3.txt bs=1M count=150
