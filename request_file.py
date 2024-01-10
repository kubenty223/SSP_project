import random
import requests
import argparse
import sys
import os
import time

def pobierz_plik(url, nazwa_lokalna):

        os.system("wget '{}' -O '{}'".format(url, nazwa_lokalna))
        print('Plik pobrany i zapisany jako {}'.format(nazwa_lokalna))




if __name__ == '__main__':

        while(1):
                adres = sys.argv[1]
                host = str(sys.argv[2])
                liczba = random.randint(1, 3)
                nazwa_pliku = 'file_{}.txt'.format(str(liczba))


                url = 'http://{}/file/{}'.format(adres, nazwa_pliku)

                nazwa_lokalna = '{}/zapis_{}'.format(host, nazwa_pliku)

                pobierz_plik(url, nazwa_lokalna)
                time.sleep(random.randint(1, 5))
