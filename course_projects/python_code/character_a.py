"""
COMP.CS.100 Ohjelmointi 1 / Programming 1

This program models a character adventuring in a video game,
or more like, the stuff that the character carries around.
Check character_b.py for an updated version.
"""

class Character:
    # TODO: the class implementation goes here.
    """This is a Game Character class"""

    def __init__(self, name):
        """
        A character object is initialized with a name.

        :param name: str, the name of the character
        """
        self.name = name
        self.items_dict = {}

    def get_name(self):
        """
        Returns the name of a character.

        :return: name: str, the name of the character
        """
        return self.name

    def has_item(self, item):
        """
        Checks if the character has a certain item. True if has, False if doesn't have.

        :param item: str, an item
        :return: True or False
        """
        if item in self.items_dict:
            return True
        else:
            return False

    def how_many(self, item):
        """
        Counts how many of one item the character has.

        :param item: str, an item
        :return: int, number of items in character inventory
        """
        if item in self.items_dict:
            return self.items_dict[item]
        else:
            return 0

    def give_item(self, item):
        """
        Adds an item to character inventory.

        :param item: str, item to be added to inventory
        """
        if item in self.items_dict:
            self.items_dict[item] = self.items_dict[item] +1
        else:
            self.items_dict[item] = 1

    def remove_item(self, item):
        """
        Removes an item from character inventory.

        :param item: str, item to be removed from inventory
        """
        if item in self.items_dict:
            self.items_dict[item] = self.items_dict[item] -1
            if self.items_dict[item] == 0:
                self.items_dict.pop(item)
        else:
            self.items_dict.pop(item)



    def printout(self):
        """Prints out the characters information."""
        print("Name:", self.name)
        if self.items_dict == {}:
            print("  --nothing--")
        else:
            for item in sorted(self.items_dict):
                print(" ", self.items_dict[item], item)



def main():
    character1 = Character("Conan the Barbarian")
    character2 = Character("Deadpool")

    for test_item in ["sword", "sausage", "plate armor", "sausage", "sausage"]:
        character1.give_item(test_item)

    for test_item in ["gun", "sword", "gun", "sword", "hero outfit"]:
        character2.give_item(test_item)

    character1.remove_item("sausage")
    character2.remove_item("hero outfit")

    character1.printout()
    character2.printout()

    for hero in [character1, character2]:
        print(f"{hero.get_name()}:")

        for test_item in ["sausage", "sword", "plate armor", "gun", "hero outfit"]:
            if hero.has_item(test_item):
                print(f"  {test_item}: {hero.how_many(test_item)} found.")
            else:
                print(f"  {test_item}: none found.")


if __name__ == "__main__":
    main()
