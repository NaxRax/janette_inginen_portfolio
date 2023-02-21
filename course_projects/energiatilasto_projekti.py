"""
COMP.CS.100 Ohjelmointi 1 / Programming 1
Name:       Janette Inginen
Email:      janette.inginen(at)tuni.fi
Student Id: 434500

Koodipohja energiankulutushistogrammiohjelmalle.
"""

"""
Ohjelma piirtää käyttäjän antamien syötteiden perusteella histogrammin.
Ensin ohjelma pyytää käyttäjältä arvot. Arvoja on oltava vähintään yksi ja
niiden on oltava positiivisia, jotta histogrammi voidaan piirtää.
Histogrammi esittää arvot luokittain. Luokien rajat eroavat toisistaan
kertoimella 10. Yksi tähti '*' tarkoittaa yhtä arvoa, joka on suuruudeltaan
luokkaan kuuluva. Kolme tähteä '***' tarkoittaa kolmea luokkaan kuuluvaa
arvoa.
"""

def print_single_histogram_line(class_number, count, largest_class_number):
    """
    Tämä on luultavasti projektin haastavin funktio, joten tässä se on
    valmiina.  Funktio tulostaa oikean muotoisen histogrammin rivin,
    kuhan kutsut sitä oikeilla parametrien arvoilla.

    :param class_number: int,
        Mitä kulutuskatergoriaa tulostettava rivi kuvaa (1, 2, 3, ...)
        Parametria <class_number> käytetään päättämään, mikä arvoväli
        (0-9, 10-99, 100-999, ...) riville tulostuu ennen diagrammin
        "*"-merkkejä.

    :param count: int,
        Kuinka monta "*"-merkkiä riville on tarpeen tulostaa, eli
        kuinka monta käyttäjän syöttämää arvoa kuuluu <class_number>-
        parametrin kuvaamalle välillä.

    :param largest_class_number: int,
        Mikä on kaikkein suurin kategorian numero.  Riippuu
        suurimmasta käyttäjän syöttämästä kulutusarvosta.
        Esimerkiksi jos suurin käyttäjän syöttämä luku
        oli 91827364 (8 numeromerkkiä) <largest_class_number>-parametrin
        arvon tulisi myös olla 8.  Parametrin arvoa käytetään
        määriteltäessä, kuinka monta välilyöntiä muiden kuin viimeisen
        histogrammin rivin eteen pitäisi tulostaa.
    """

    # <range_string>-muuttujaan talletetaan merkijonona rivin
    # histogrammissa kuvaama arvoväli. Esimerkiksi "1000-9999".
    # Apufunktiot class_minimum_value ja class_maximum_value
    # sinun on määriteltävä itse.

    min_value = class_minimum_value(class_number)
    max_value = class_maximum_value(class_number)
    range_string = f"{min_value}-{max_value}"


    # Kun histogrammin viimeinen rivi tulostetaa, kuinka monta
    # merkkiä leveä tulee <range_string> silloin olemaan.
    # Jos esimerkiksi <largest_class_number> on 7, tarkoittaisi
    # se, että arvoväliksi tulostetaan "1000000-9999999" eli
    # muuttujaan <largest_width> pitää tallentaa arvo 15.
    # Kaikkien arvovälien <range_string> tulostetaan tämän
    # levyisen kentän oikeaan laitaan.

    largest_width = 2 * largest_class_number + 1


    # Kaikki valmistelun on tehty, voidaan tulostaa rivi,
    # jonka alussa on oikea määrä välilyöntejä, niiden perässä
    # arvoväli ja lopulta oikea määrä "*"-merkkejä.
    # Merkki ">" seuraavassa f""-merkkijonossa tulostaa
    # <range_string>:in arvon tulostuskentän oikeaan laitaan
    # (täytevälilyönnit tulostetaan alkuun).

    print(f"{range_string:>{largest_width}}: {'*' * count}")

def print_histogram(values_list):
    """Tulostaa histogrammin rivi riviltä. Ennen tulostusta laskee suurimman luokan
        numeron ja kuinka monta arvoa kussakin luokassa on eli kuinka monta tähteä
        täytyy kuhunkin luokkaan tulostaa. Parametreina käyttäjän antamien arvojen lista."""
    # Ensin lasketaan suurimman luokan numero
    largest_class_number = get_largest_class(max(values_list))
    # Koska pythonissa indeksi alkaa nollasta, lisätään saatuun arvoon 1
    largest_class_number += 1
    # selvitetään "tähtien" määrä eli kuinka monta arvoa kussakin luokassa on
    frequencies_list = class_frequencies(values_list, largest_class_number)
    # Tulostetaan histogrammi rivi riviltä
    # tulostetaan niin monta kertaa, että tulostetaan luokka, johon suurin annettu luku kuuluu
    for i in range(0, largest_class_number):
        # kutsutaan metodia ja annetaan parametreina, tulostettavan luokan numero, luokkaan kuuluvien
        # "tähtien" määrä ja suurimman luokan numero
        print_single_histogram_line(i, frequencies_list[i], largest_class_number)

def get_values():
    """Ottaa ylös käyttäjältä saadut arvot ja palauttaa ne listassa."""
    # Luodaan tyhjä lista
    values = []
    # Pyydetään arvoja kunnes saadaan käyttäjältä tyhjä syöte
    while True:
        # Kysytään arvoa
        value = input("Enter energy consumption (kWh): ")
        # Jos arvo on tyhjä, palautetaan lista
        if value == "":
            return values
        # Muussa tapauksessa muutetaan arvo int luvuksi
        else:
            value = int(value)
        # Jos luku on pienempi kuin nolla, käyttäjä on antanut väärän syötteen
        if value < 0:
            print("You entered:", str(value) + ". Enter non-negative numbers only!")
        # Muussa tapauksessa lisätään luku listaan
        else:
            values.append(value)


def class_minimum_value(class_num):
    """Hakee pienimmän luokkaan kuuluvan arvon. Parametrina luokan numero."""
    # Jos luokan numero on 0 eli luokka on ensimmäinen, palautetaan valmiiksi
    # asetettu ensimmäisen luokan pienin arvo eli 0
    if class_num == 0:
        return 0
    # muussa tapauksessa lasketaan luokan numeron perusteella oikea pienin arvo
    # ja palautetaan se.
    else:
        return 10**class_num

def class_maximum_value(class_num):
    """Hakee suurimman luokkaan kuuluvan arvon. Parametrina luokan numero."""
    # Jos luokan numero 0 eli luokka on ensimmäinen, palautetaan valmiiksi
    # asetetti ensimmäisen luokan suurin arvo eli 9
    if class_num == 0:
        return 9
    # muussa tapauksessa lasketaan luokan numeron perusteella oikea suurin arvo
    # ja palautetaan se.
    else:
        return 10**(class_num+1) -1

def class_frequencies(values_list, largest_class_number):
    """Laskee kuinka monta käyttäjän arvoa löytyy kustakin luokasta ja palauttaa
        ne listana. Parametreina käyttäjän antamien arvojen lista ja suurimman
        luokan numero."""
    # frequencies list, lista johon lisätään "tähtien" määrä eli kuinka monta arvoa
    # kussakin luokassa on
    f_list = []
    # Aluksi lukuja/"tähtiä" esiintyy 0. Jos arvo kuuluu luokaan, tämä counter nousee yhdellä.
    frequencies_count = 0
    # Käydään läpi kaikki luokat suurimpaan luokkaan asti. i on läpi käytävän luokan numero
    for i in range(0, largest_class_number):
        # käydään jokaisen luokan kohdalla jokainen käyttäjän antama arvo
        for value in values_list:
            # Jos arvo kuuluu luokkaan, counteri nousee yhdellä
            if class_minimum_value(i) <= value <= class_maximum_value(i):
                frequencies_count = frequencies_count + 1
        # Lisätään luokkaan kuuluvien arvojen määrä. 0, jos luokkaan ei kuulunut yhtäkään arvoa
        f_list.append(frequencies_count)
        # Nollataan counteri
        frequencies_count = 0
    # Palautetaan lista
    return f_list

def get_largest_class(max_value):
    """Lasketaan suurimman luokan numero ja palautetaan se. Parametrina suurin käyttäjän antama arvo."""
    # Laskee monennessako luokassa mennään
    class_count = 0
    # Jatketaan kunnes suurin arvo kuuluu johonkin luokkaan.
    while True:
        # Jos arvo kuuluu luokkaan, palautetaan monennessako luokassa mennään
        if class_minimum_value(class_count)<= max_value <= class_maximum_value(class_count):
            return class_count
        # muussa tapauksessa luokka nousee yhdellä
        else:
            class_count += 1


def main():
    # Tulostetaan tietoa ohjelman toiminnasta käyttäjälle
    print("Enter energy consumption data.")
    print("End by entering an empty line.")
    print()

    # Pyydetään käyttäjältä arvot
    values_list = get_values()
    # Jos käyttäjä ei antanut yhtäkään arvoa, lopetetaan ohjelman toiminta.
    if values_list == []:
        print("Nothing to print. Done.")
    # Muussa tapauksessa aloitetaan histogrammin tulostus.
    else:
        print_histogram(values_list)


if __name__ == "__main__":
    main()
