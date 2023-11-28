#!/bin/bash


# Utwórz plik o rozmiarze 50 MB
dd if=/dev/zero of=f50mb.txt bs=1M count=50

# Utwórz plik o rozmiarze 100 MB
dd if=/dev/zero of=f100mb.txt bs=1M count=100

# Utwórz plik o rozmiarze 150 MB
dd if=/dev/zero of=f150mb.txt bs=1M count=150
