#include <iostream>
#include <algorithm>
#include <random>
#include <vector>
#include <string>
#include <cctype>


/* Mysteerimatto
 *
 * Kuvaus:
 *   Ohjelma toteuttaa mysteerimaton, jossa sovelletaan mallintunnistusta
 * (pattern matching). Matto koostuu erivärisistä ruuduista ja samoin
 * malli, mutta mallin koko on aina 2 x 2, eli se koostuu neljästä
 * väriruudusta. Ohjelma etsii mallin esiintymiä matosta.
 *   Ohjelma kysyy ensin maton kokoa (leveys ja pituus). Käyttäjältä
 * kysytään myös, täytetäänkö matto (ruudukko) satunnaisesti arvottavilla
 * vai käyttäjän luettelemilla väreillä. Ensimmäisessä vaihtoehdossa
 * käyttäjältä kysytään satunnaislukugeneraattorin siemenlukua ja
 * jälkimmäisessä häntä pyydetään syöttämään niin monta väriä, kuin matossa
 * on ruutuja, jolloin luetellaan värien alkukirjaimet yhtenä pitkänä
 * merkkijonona.
 *   Joka kierroksella käyttäjältä kysytään etsittävää mallia (neljän
 * merkin/värin muodostamaa merkkijonoa). Ohjelma tulostaa, kuinka monta
 * mallin esiintymää matosta löytyi ja mistä kohdista ne löytyivät.
 *   Ohjelma tarkistaa, oliko käyttäjän antamat värikoodit hyväksyttäviä.
 * Ohjelma päättyy, kun käyttäjä antaa lopetuskomennon (merkki 'q' tai 'Q').
 *
 * Ohjelman kirjoittaja
 * Nimi: Janette Inginen
 * Opiskelijanumero: 434500
 * Käyttäjätunnus: kcjain
 * E-Mail: janette.inginen@tuni.fi
 *
 * Huomioita ohjelmasta ja sen toteutuksesta (jos sellaisia on):
 *
 * */

using namespace std;
//Create colors
enum Color {RED, GREEN, BLUE, YELLOW, WHITE, NUMBER_OF_COLORS};

struct ColorObject {
    Color color_;
    string abbreviation;
};

const vector<ColorObject> Colors = {
    { RED, "R" },
    { GREEN, "G" },
    { BLUE, "B" },
    { YELLOW, "Y" },
    { WHITE, "W" },
};

void find_matches(int heigth, int width, vector<vector<Color>> board, string& guess){
    int count = 0;
    // Go through the whole carpet to check for matches
    for (int x=0 ; x<heigth-1 ; x++){
        for (int y=0 ; y<width-1 ; y++){
            // Checking if all of the guess' colors were correct
            if (Colors[board[x][y]].abbreviation == string(1, toupper(guess[0]))){
                if (Colors[board[x][y+1]].abbreviation == string(1, toupper(guess[1]))){
                    if(Colors[board[x+1][y]].abbreviation == string(1, toupper(guess[2]))){
                        if (Colors[board[x+1][y+1]].abbreviation == string(1, toupper(guess[3]))){
                            // if the guess was correct, we count it in and print its location
                            count = count+1;
                            cout << "- Found at (" <<y+1<< ", "<<x+1<< ")" << endl;
                        }
                    }
                }
            }
        }
    }
    // print how many matches were found
    cout << "= Matches found: " << count << endl;
}

// Keeps asking for a guess and checks for a correct input
// exits if given "q" for quit or when receives a correct input
string enter_colors(){
    bool wrong_input = true;
    string input = "";
    // Continues asking colors from user
    while(wrong_input){
        input = "";
        cout << "Enter 4 colors, or q to quit: ";
        cin >> input;
        cin.clear();
        cin.ignore(numeric_limits<streamsize> ::max(), '\n');
        // Check if input is correct length and includes allowed colors
        // if incorrect prints an error message
        // if correct stops the while loop
        if (input.length() == 4){
            for(char color : input){
                bool is_real_color = false;
                // Checks if color can be found in allowed colors
                for (ColorObject c : Colors){
                    if(c.abbreviation == string(1,toupper(color))){
                        is_real_color = true;
                    }
                }
                // If color isn't found in allowed colors, prints an error
                // loop continues
                if (!is_real_color){
                    cout << "Error: Unknown color." << endl;
                    wrong_input = true;
                    break;
                }
                // if input is correct, exit loop
                else{
                    wrong_input = false;
                }
            }
        }
        // Input q for quit returns empty string meaning quit program
        else if (input == "q" or input == "Q"){
            return "";
        }
        // If user input isn't the correct length and isn't q for quit
        // then input is incorrect length and prints an error message
        else{
            cout << "Error: Wrong amount of colors." << endl;
        }
    }
    // if loop has been exited, the input must be correct
    return input;
}

// Fills a carpet of given size with random colors, returns said carpet
vector <vector <Color>> init_random_carpet(int height, int width){
    // Initialization
    std::vector< std::vector< Color > > board;
    default_random_engine rand_gen;
    // Ask user for a seed value for random generator
    cout << "Enter a seed value: ";
    int seed_value = 0;
    cin >> seed_value;
    rand_gen.seed( seed_value );
    // Filling the board with random numbers between 0 and 4
    // These random numbers correspond to a Color
    std::uniform_int_distribution<int> distribution(0,4);
    // Fill carpet to the given size (heigth x width) with random colors
    for( int y = 0; y < height; ++y){
        // Initialize a row of the carpet
        vector< Color > row;
        for( int x = 0; x < width; ++x){;
            // add a random color to row vector
            row.push_back(static_cast<Color>(distribution(rand_gen)));
        }
        // add row to carpet(=board)
        board.push_back( row );
    }
    // Once board is filled with colors, return board(=carpet)
    return board;
}

// Fills carpet with colors from user input
// param: the heigth and the width of the carpet(=board)
// returns a board filled with inputed colors
vector <vector <Color>> init_carpet_w_inputs(int heigth, int width){
    // Initialization
    string input = "";
    vector<int>::size_type size = heigth*width; //allowed size of the carpet(=board)
    //initiate a board for carpet and a failedboard
    // in case of an error
    std::vector< std::vector< Color > > board;
    std::vector< std::vector< Color > > failedboard;
    cout << "Input: ";
    cin.ignore();
    getline(cin, input);
    // if input is of correct size, then start adding colors
    if (input.length() == size){
        // counter for how many color have been already added, used as index
        int count = 0;
        // add color for whole board(=carpet) with the inputed size
        for(int y= 0; y < width; ++y){
            // initiate a row of colors for carpet
            vector< Color > row;
            for( int x = 0; x < heigth; ++x){
                // row size to check if a new color has been added or no
                vector<int>::size_type size = row.size();
                // for each color check if user inputed color is found in allowed colors
                for (ColorObject c : Colors){
                    if(c.abbreviation == string(1,toupper(input[count]))){
                            // if color is allowed, add it to row, increase count and stop checking
                            row.push_back(c.color_);
                            count = count+1;
                            break;
                    }
                }
                // if row size has not changed, then the color was not found in the allowed colors
                // meaning the color is incorrect. Print error message and return empty board(=carpet)
                if (row.size()==size){
                    cout << "Error: Unknown color." << endl;
                    return failedboard;
                }
            }
            // if row size has changed, there have been only allowed colors
            // and we can add them to carpet
            board.push_back( row );
        }
    }
    // Wrong amount of colors, print error, return empty board
    else {
            cout << "Error: Wrong amount of colors." << endl;
            return failedboard;
    }
    // if got to the end, then the filling of the board was a success
    // return filled board(=carpet)
    return board;
}

// Prints carpet. Receives the heigth, width and carpet vector.
void print_carpet(int heigth, int width, vector <vector <Color>> carpet){
    // Prints the equivalent letter for each color in the carpet
    for (int i =0; i < heigth; i++){
        for (int j =0; j < width; j++){
            cout << Colors[carpet[i][j]].abbreviation << " ";
        }
        cout << endl;
    }
}

// Splits string into parts and returns them in a vector
// gets a string, a seperator character and can receive a boolean to ignore empty parts
std::vector<std::string> split(std::string& line_, char& sep, bool ignore_empty = false){
    std::vector<std::string> palautus;
    std::string one_piece = "";
    // goes through all characters in string, adds them in pieces to vector
    for(char merkki : line_){
        //std::cout << merkki << std::endl;
        if (merkki == sep){
             //std::cout << one_piece << std::endl;
            if (ignore_empty){
                if(one_piece != ""){
                    palautus.push_back(one_piece);
                }
            }
            else {
                palautus.push_back(one_piece);
            }
            one_piece = "";
        }
        else {
            one_piece += merkki;
        }
    }
    // can ignore empty parts
    if (ignore_empty){
        if(one_piece != ""){
            palautus.push_back(one_piece);
        }
    }
    // or just adds all parts to vector
    else {
        palautus.push_back(one_piece);
    }
    // returns vector with split up parts
    return palautus;
}


int main()
{
    // Let's initialize some useful variables
    // Carpet will be represented with "board" in code
    vector < vector <Color> > board;
    string input = "";
    int width = 0; //carpet width
    int heigth = 0; // carpet height (will be misspelled in code)
    // Get carpet's size from user
    // Wanted input is "number + space + number"
    cout << "Enter carpet's width and height: ";
    getline(cin, input);
    // split input into two parts, width and heigth
    char seperator= ' ';
    vector<string> numbers = split(input, seperator);
    width = stoi(numbers[0]);
    heigth = stoi(numbers[1]);

    // Minimum carpet size is 2 x 2
    // A carpet too small will print an error
    if (width >=2 and heigth>=2){
        // Continue asking for input until we get a correct one
        bool wrong_input = true;
        while(wrong_input){
            input = "";
            cout << "Select start (R for random, I for input): ";
            cin >> input;
            // Input carpet
            if (input == "i" or input == "I"){
                // Fill up carpet with own colors
                board = init_carpet_w_inputs(width , heigth);
                // If the size of the returned board(=carpet) is 0,
                // then the initialization failed
                vector<int>::size_type size = 0;
                // If the board is larger than empty, then we exit the while
                if (board.size() != size){
                    wrong_input = false;
                }

            }
            // Random carpet
            else if (input == "r" or input == "R"){
                // method should always return a correct sized carpet
                board = init_random_carpet(width , heigth);
                // carpet has been filled, exit the while loop
                wrong_input = false;
            }
        }
    }
    // If carpet too small then print an error message and program exits with exit failure
    else{
        cout << "Error: Carpet cannot be smaller than pattern." << endl;
        return EXIT_FAILURE;
    }
    // After the board has been filled
    // the board(=carpet) is printed
    print_carpet(heigth, width, board);
    // Ask user for colors until they input Q to quit
    bool trying = true;
    while(trying){
        // Check if user gives the right kind of input
        string guess = enter_colors();
        // if user inputs "q" or "Q", method reurns an empty string
        // and the game ends
        if (guess == ""){
            trying = false;
        }
        // in succesful cases we try to find matches
        else{
            find_matches(heigth, width, board, guess);
        }
    }

    return EXIT_SUCCESS;
}

