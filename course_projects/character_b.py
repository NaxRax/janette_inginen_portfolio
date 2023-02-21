"""
COMP.CS.100 Ohjelmointi 1 / Programming 1

This program models a character adventuring in a video game.
"""

class Character:
    """
    This class defines what a character is int he game and what
    he or she can do.
    """

    def __init__(self, name, hitpoints):
        """
        A character object is initialized with a name.

        :param name: str, the name of the character
        """
        self.name = name
        self.hp = hitpoints
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
            self.items_dict[item] = self.items_dict[item] + 1
        else:
            self.items_dict[item] = 1

    def remove_item(self, item):
        """
        Removes an item from character inventory.

        :param item: str, item to be removed from inventory
        """
        if item in self.items_dict:
            self.items_dict[item] = self.items_dict[item] - 1
            if self.items_dict[item] == 0:
                self.items_dict.pop(item)
        else:
            self.items_dict.pop(item)

    def printout(self):
        """Prints out the characters information."""
        print("Name:", self.name)
        print("Hitpoints:", self.hp)
        if self.items_dict == {}:
            print("  --nothing--")
        else:
            for item in sorted(self.items_dict):
                print(" ", self.items_dict[item], item)


    def pass_item(self, item, target):
        """
        Passes (i.e. transfers) an item from one person (self)
        to another (target).

        :param item: str, the name of the item in self's inventory
                     to be given to target.
        :param target: Character, the target to whom the item is to
                     to be given.
        :return: True, if passing the item to target was successful.
                 False, it passing the item failed for any reason.
        """

        if item in self.items_dict:
            if self.items_dict[item] == 1:
                self.items_dict.pop(item)
                if item in target.items_dict:
                    target.items_dict[item] = target.items_dict[item] + 1
                else:
                    target.items_dict[item] = 1
            else:
                self.items_dict[item] = self.items_dict[item] -1
                if item in target.items_dict:
                    target.items_dict[item] = target.items_dict[item] + 1
                else:
                    target.items_dict[item] = 1
            return True
        else:
            return False


    def attack(self, target, weapon):
        """
        A character (self) attacks the target using a weapon.
        This method will also take care of all the printouts
        relevant to the attack.

        There are three error conditions:
          (1) weapon is unknown i.e. not a key in WEAPONS dict.
          (2) self does not have the weapon used in the attack
          (3) character tries to attack him/herself.
        You can find the error message to printed in each case
        from the example run in the assignment.

        The damage the target receives if the attack succeeds is
        defined by the weapon and can be found as the payload in
        the WEAPONS dict. It will be deducted from the target's
        hitpoints. If the target's hitpoints go down to zero or
        less, the target is defeated.

        The format of the message resulting from a successful attack and
        the defeat of the target can also be found in the assignment.

        :param target: Character, the target of the attack.
        :param weapon: str, the name of the weapon used in the attack
                       (must be exist as a key in the WEAPONS dict).
        :return: True, if attack succeeds.
                 False, if attack fails for any reason.
        """

        # TODO: the implementation of the method
        if weapon not in WEAPONS:
            print('Attack fails: unknown weapon "' + weapon + '".')
            return False
        if self.name == target.name:
            print("Attack fails:", self.name, "can't attack him/herself.")
            return False
        if weapon in self.items_dict:
            print(self.name, "attacks", target.name, "delivering", WEAPONS[weapon], "damage.")
            target.hp = target.hp - WEAPONS[weapon]
            if target.hp <=0:
                print(self.name, "successfully defeats", target.name + '.')
            return True
        else:
            print("Attack fails:", self.name, "doesn't", 'have "' + weapon + '".')
            return False


WEAPONS = {
    # Weapon          Damage
    #--------------   ------
    "elephant gun":     15,
    "gun":               5,
    "light saber":      50,
    "sword":             7,
}


def main():
    conan = Character("Conan the Barbarian", 10)
    deadpool = Character("Deadpool", 45)


    # Testing the pass_item method

    for test_item in ["sword", "sausage", "plate armor", "sausage", "sausage"]:
        conan.give_item(test_item)

    for test_item in ["gun", "sword", "gun", "sword", "hero outfit"]:
        deadpool.give_item(test_item)

    conan.pass_item("sword", deadpool)
    deadpool.pass_item("hero outfit", conan)
    conan.pass_item("sausage", deadpool)
    deadpool.pass_item("gun", conan)
    conan.pass_item("sausage", deadpool)
    deadpool.pass_item("gun", conan)

    print("-" * 5, "How are things after passing items around", "-" * 20)
    conan.printout()
    deadpool.printout()


    # Testing a fight i.e. the attack method

    print("-" * 5, "Let's see how a fight proceeds", "-" * 32)

    # Conan's turn
    conan.attack(deadpool, "sword") # Conan doesn't have a sword.
    conan.attack(conan, "gun")      # A character is not allowed to attack himself.
    conan.attack(conan, "pen")      # Pen is not a known weapon in WEAPONS dict.
    conan.attack(deadpool, "gun")   # Attack with a gun.

    # Deadpool retaliates
    deadpool.attack(conan, "sword") # Deadpool has a sword.

    # Conan's 2nd turn
    conan.attack(deadpool, "gun")   # Attack with a gun again.

    # Deadpool strikes back again and Conan drops "dead".
    deadpool.attack(conan, "sword")

    print("Are You Not Entertained?!")

    print("-" * 5, "How are things after beating each other up", "-" * 20)

    conan.printout()
    deadpool.printout()


if __name__ == "__main__":
    main()
