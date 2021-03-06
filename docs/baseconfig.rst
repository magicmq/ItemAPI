.. _baseconfig:

Base Item Definition
====================

The following section will explain how to define items at the most basic level (metadata).

Items are defined according to configuration sections. They look like this:

.. code-block:: yaml

    test-item:
      #all properties relating to this item would be defined here, within the test-item section.


All parameters related to this item will be defined within this section. The section name can be anything and will have no effect on the item being generated.

There are additional subsections that may be defined within this base section.

A Note About Reading the Documentation
**************************************

At the beginning of each section, there will be parameter, type, default, and required values. Each of these are mostly self-explanatory, and can be used as a tldr; for each parameter.

* **Parameter:** The name of the parameter in the config
* **Type:** The type of the config value, usually a number or a string (sequence of characters)
* **Default:** The default value, if there is one. Usually ``null``
* **Required:** If this value is a required value.

Material Name
#############

* **Parameter:** ``material``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes


At the very least, all items must have a *Material name* that defines what the material actually is. For a list of material names, see the Bukkit `Material <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html>`__ class. Failure to define a material will result in failure to parse the item, as this is a requirement.

.. note:: Mojang fundamentally changed the way items are defined in Minecraft 1.13 and above. Ensure you are using the correct material names if you are running Minecraft 1.13 and above. Damage values for certain items **only** need to be included in version 1.12 and below.

The material name is defined via the ``material`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'

If parsing fails for some reason, you will see an error that mentions a ``NullPointerException``. There are several reasons why parsing the material might fail:

* The ``material`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``material`` parameter correctly in the config (correct indentation, spelling, etc.).
* The item name was not one in the list of Material names. Ensure you are using a material name on `this <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html>`__ page.
* The item name was spelled incorectly. Ensure the item name is spelled correctly.
* The item name you used does not exist in the Minecraft version you are running. Ensure the item exists in the Minecraft version you are running.

Amount
######

* **Parameter:** ``amount``
* **Type:** Number (``integer``)
* **Default:** 1
* **Required:** No

The amount of an item represents *how much* of this item there will be when it is generated. If the config section does not contain an amount, the library will automatically set the amount of the item to one. The minimum value for this parameter is one.

The amount is defined as a number via the ``amount`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1

There are a couple reasons why parsing the amount might fail:

* The ``amount`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``amount`` parameter correctly in the config (correct indentation, spelling, etc.).
* An amount of less than zero was used. All items must have a positive amount.

Damage
######

* **Parameter:** ``damage``
* **Type:** Number (``short``)
* **Default:** 0
* **Required:** No

Damage values have different meaning depending on the Minecraft version you are using as well as the item of concern.

For items that have finite *durability* such as tools (pickaxes, axes, shovels, hoes, flint and steel, and shears) and weapons (swords, bows), damage has the same meaning regardless of the Minecraft version. In this instance, damage refers to the amount of "uses" left on an item, and counts upwards with each use, starting at zero.

In 1.12 and below, durability can also be used to distinguish between similar items that have different colors, wood types, etc. For example, different types of wool and stained clay use the damage value to specify which color the item/block is. 

.. note:: Mojang fundamentally changed the way items are defined in Minecraft 1.13 and above. In 1.13 and above, damage values are no longer used to distinguish between items such as colored wool and different types of wood. Each of these now have a different *material name*.

The damage value is defined as a number via the ``damage`` parameter. Continuing the example from above, it would look like this: 

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50

This example would spawn a diamond sword with the number of uses equal to the maximum number of uses minus 50 (2032 - 50), since, as stated previously, damage counts upwards, not downwards.

There are some issues that could be encountered with item damage:

* The ``damage`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``amount`` parameter correctly in the config (correct indentation, spelling, etc.).
* See the note above on using durability to define items of different colors, wool types, etc. This system no longer applies in 1.13+.
* A damage value of less than zero was used. All items must have a positive damage.
* A damage value that exceeds the maximum durability of a tool/weapon was used. Ensure that the damage value you define is less than the maximum durability for that item.

Concluding Remarks
##################

This section covered the most basic properties associated with an item. To continue onto metadata such as as name, lore, and item-specific properties, see the :ref:`metadata` page.                                                    