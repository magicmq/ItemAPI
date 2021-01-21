.. _metadata:

*******************
Basic Item Metadata
*******************

In addition to the basic item properties outlined `here <basic_config>`_, items can also be assigned metadata. All values in this section can be applied to *any* item, but there are also item-specific metadata values that can only be applied to certain items. That metadata will be covered later.

.. _meta_name:

Name
####

* **Parameter:** ``name``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The *name* of an item will be shown when the item is either selected in the hotbar or when the cursor hovers over the item in an inventory (at the top). To use color/formatting codes in the item name, use an ampersand (&) followed by either the letter or number corresponding to the desired color/text format. See this `list of color/formatting codes <https://minecraft.gamepedia.com/Formatting_codes#Color_codes>`_.

The item name is defined as a string via a the ``name`` parameter. Continuing the example from the `basic item properties <base_config>`_ section, it would look like this: 

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'

There are some issues that could be encountered with item name:

* The ``name`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``name`` parameter correctly in the config (correct indentation, spelling, etc.).
* Color/formatting codes were not defined correctly. Ensure you are defining color/formatting codes with an ampersand (&) followed by the color.

.. todo:: RGB color support was added by Mojang in Minecraft 1.16. This is not yet supported in ItemAPI but is a planned feature and will be added in the future.

Lore
####

* **Parameter:** ``lore``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The *lore* of an item can consist of multiple lines and will show when the cursor hovers over the item in an inventory. The lore will show directly below the name of the item. The lore can consist of multiple lines of text. To use color/formatting codes in the item lore, use an ampersand (&) followed by either the letter or number corresponding to the desired color/text format. See this `list of color/formatting codes <https://minecraft.gamepedia.com/Formatting_codes#Color_codes>`_. Because the lore is defined on a line-by-line basis, color codes will need to be redefined for each line of text.

The item lore is defined as a list of strings via the ``lore`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'
      lore:
      - '&6This is the first line of the lore.'
      - '&6This is the second line of the lore'
      - '&6This is the third line of the lore.'

There are some issues that could be encountered with item lore:

* The ``lore`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``lore`` parameter correctly in the config (correct indentation, spelling, etc.).
* Color/formatting codes were not defined correctly. Ensure you are defining color/formatting codes with an ampersand (&) followed by the color.

.. todo:: RGB color support was added by Mojang in Minecraft 1.16. This is not yet supported in ItemAPI but is a planned feature and will be added in the future.

Breakability
############

* **Parameter:** ``unbreakable``
* **Type:** ``boolean`` (true/false)
* **Default:** ``false``
* **Required:** No

For items that have finite *durability* such as tools (pickaxes, axes, shovels, hoes, flint and steel, and shears) and weapons (swords, bows), breakability refers to whether or not the item can break and despawn if it runs out of available uses. This parameter has no effect on items that do not have finite durability.

The breability of tools/weapons is defined as a boolean (true/false) value via the ``unbreakable`` parameter. Continuing the example from above, it would look like this: 

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'
      lore:
      - '&6This is the first line of the lore.'
      - '&6This is the second line of the lore'
      - '&6This is the third line of the lore.'
      unbreakable: true

There are some issues that could be encountered with breakability:

* The ``unbreakable`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``unbreakable`` parameter correctly in the config (correct indentation, spelling, etc.).
* Unbreakability was defined to an item that does not have finite durability. Ensure that unbreakability is only defined to items with finite durability such as tools and weapons.

Item Flags
##########

* **Parameter:** ``item-flags``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

Item flags can be applied to items and confer upon them various properties. They are usually used to hide certain properties of an item. A list of item flags can be found in the :ref:`item flags table <itemflags>` in the :ref:`appendix`.

Item flags are defined as a list of strings via the ``item-flags`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'
      lore:
      - '&6This is the first line of the lore.'
      - '&6This is the second line of the lore'
      - '&6This is the third line of the lore.'
      unbreakable: true
      item-flags:
      - 'HIDE_ATTRIBUTES'

There are some issues that could be encountered with item flags:

* The ``item-flags`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``item-flags`` parameter correctly in the config (correct indentation, spelling, etc.).
* The item flag was not one in the list of item flags in the item flags table. Ensure you are using one of the item flags listed in the :ref:`item flags table <itemflags>`.
* The item flag was spelled incorectly. Ensure the item flag is spelled correctly according to how it appears in the :ref:`item flags table <itemflags>`.
* The item flag you used does not exist in the Minecraft version you are running. Ensure the item flag exists in the Minecraft version you are running according to the "Version" column in the :ref:`item flags table <itemflags>`.

Enchantments
############

* **Parameter:** ``enchantments``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

Enchantments can be added to many (but not all) items. Many enchantments are available, and their names can be viewed in the :ref:`enchantments table <enchantments>` in the :ref:`appendix`. Also included with an enchantment is its level, which has a multiplicative effect on the power of the enchantment. See `this <https://minecraft.gamepedia.com/Enchanting/Levels>`__ page for more details on this mechanic.

.. note:: Many enchantments exist only in certain versions of Minecraft and above. To be sure the enchantment you wish to use is available in the version you are using, see the "Version" column in the table on the `enchantments`_ page.

Enchantments are defined as a list of strings via the ``enchantments`` parameter in the format ``<enchantment type>:<enchantment level>``. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'
      lore:
      - '&6This is the first line of the lore.'
      - '&6This is the second line of the lore'
      - '&6This is the third line of the lore.'
      unbreakable: true
      item-flags:
      - 'HIDE_ATTRIBUTES'
      enchantments:
      - 'SHARPNESS:3'
      - 'FIRE_ASPECT:2'

There are some issues that could be encountered with item flags:

* The ``enchantments`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``enchantments`` parameter correctly in the config (correct indentation, spelling, etc.) and that each enchantment is in the format ``<enchantment type>:<enchantment level>``.
* The enchantment was not one in the list of enchantment in the table above. Ensure you are using one of the enchantments listed in the table on the `enchantments`_ page.
* The enchantment was spelled incorectly. Ensure the enchantment is spelled correctly according to how it appears in the table on the `enchantments`_ page.
* The enchantment you used does not exist in the Minecraft version you are running. Ensure the enchantment exists in the Minecraft version you are running according to the "Version" column in the table on the `enchantments`_ page.

Item Model
##########

* **Parameter:** ``model``
* **Type:** Number (``integer``)
* **Default:** Nothing (``null``)
* **Required:** No

The item model parameter is a number that can be used in conjunction with a custom resource pack to give items custom models.

.. note:: Custom model data is only supported in Minecraft versions 1.14 and above.

The item model is defined as a number via the ``model`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      damage: 50
      name: '&c&lThis is a test name.'
      lore:
      - '&6This is the first line of the lore.'
      - '&6This is the second line of the lore'
      - '&6This is the third line of the lore.'
      unbreakable: true
      item-flags:
      - 'HIDE_ATTRIBUTES'
      enchantments:
      - 'SHARPNESS:3'
      - 'FIRE_ASPECT:2'
      model: 134

There are some issues that could be encountered with the item model:

* The ``item-model`` parameter was not defined correctly in the config section being parsed. Ensure you are defining the ``item-model`` parameter correctly in the config (correct indentation, spelling, etc.).
* The number does not correspond to an existing model. Ensure that the number you are using corresponds to an an existing model in Minecraft or in a custom resource pack.
* The Minecraft version you are using is below Minecraft 1.14. Custom item models are not supported in versions lower than Minecraft 1.14.

Concluding Remarks
##################

This section covered basic metadata assignable to almost every item in Minecraft. For more item-specific metadata, return to the :ref:`homepage <home>` and scroll down to the Navigation section.