import random
import requests
import argparse
import sys

def pobierz_plik(url, nazwa_lokalna):
    response = requests.get(url)
    if response.status_code == 200:
        with open(nazwa_lokalna, 'wb') as plik:
            plik.write(response.content)
	print('Plik pobrany i zapisany jako {}'.format(nazwa_lokalna))
    else:
	print(response)
	print("XD")

if __name__ == '__main__':

	adres = sys.argv[1]
	liczba = random.randint(1, 3)
	nazwa_pliku = 'file_{}.txt'.format(str(liczba))


	url = 'http://{}/{}'.format(adres, nazwa_pliku)
	
	nazwa_lokalna = 'zapis_{}'.format(nazwa_pliku)

	pobierz_plik(url, nazwa_lokalna)


