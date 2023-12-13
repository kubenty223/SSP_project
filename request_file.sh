#!/bin/bash

# Sprawdzanie liczby argumentów
if [ "$#" -ne 2 ]; then
    echo "Użycie: $0 <adres_url> <nazwa_lokalnego_pliku>"
    exit 1
fi

# Przypisanie argumentów do zmiennych
adres_url=$1
nazwa_lokalnego_pliku=$2

# Pobieranie pliku za pomocą polecenia wget
wget "$adres_url" -O "$nazwa_lokalnego_pliku"

# Sprawdzanie, czy pobieranie się powiodło
if [ "$?" -eq 0 ]; then
    echo "Pobieranie zakończone sukcesem. Plik zapisany jako: $nazwa_lokalnego_pliku"
else
    echo "Błąd podczas pobierania pliku."
fi
