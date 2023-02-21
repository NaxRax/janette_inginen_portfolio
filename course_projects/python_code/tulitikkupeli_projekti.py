"""
Comp.cs.100
Tulitikkupeli projekti
janette.inginen(at)tuni.fi
434500
"""

def main():
    """Tulitikkupeli! Pelissä on kaksi pelaajaa ja 21 tulitikkua.
    pelaajat vuorotellen poistavat 1-3 tikkua kasasta. Pelaaja, joka
    poistaa viimeisen tikun on häviäjä."""
    print("Game of sticks")
        # Asetetaan tikkujen määrä
    sticks = 21
        # Counterin avulla seurataan kenen vuoro on menossa
    counter = 0
        # Käytetään player muuttujaa tulostuksissa. Pelin aloittaa Player 1.
    player = "Player 1"
    pelataan = True
    while pelataan:
            # Pyydetään pelaajaa poistamaan joku määrä tikkuja, num = numero/number
        num = int(input(player + " enter how many sticks to remove: "))
            # Jos käyttäjä antaa väärän syötteen tulostetaan huomautus
        if num < 1 or num > 3:
            print("Must remove between 1-3 sticks!")
        else:
                # Lasketaan pelissä olevien tikujen määrä
            sticks = sticks - num
                # Jos tikkuja ei enää ole niin pelaaja häviää ja peli loppuu
            if sticks <= 0:
                print(player + " lost the game!")
                pelataan = False
                # Jos tikkuja on jäljellä tulostetaan tikkujen määrä ja vuoro vaihtuu
            else:
                print("There are " + str(sticks) + " sticks left")
                # counter laskee kierrokset
                # parillisilla kierroksilla pelaa pelaaja 1
            counter = counter +1
            if counter % 2 == 0:
                player = "Player 1"
            else:
                player = "Player 2"


if __name__ == "__main__":
        main()
