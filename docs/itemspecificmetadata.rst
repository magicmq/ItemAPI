.. _itemmetadata:

Item-Specific Metadata
======================

The following sections describe how to define item-specific metadata.

.. note:: Several of these metadata only apply to certain items, and an error will result if another item is used. Be sure to make note of which items are acceptable in each section.

Banner Metadata
###############

* **Parameter:** ``banner-patterns``
* **Type:** Config Section (Each subsection is a pattern)
* **Default:** Nothing (No patterns)
* **Required:** No

The following section describes how to add patterns to banners. Each pattern consists of a pattern type and a color. Multiple patterns can be defined and will be stacked on top of each other to produce more unique overall designs.

All of the following values are defined under a configuration section titled ``banner-patterns``. Each subsection in ``banner-patterns`` is considered a new pattern.

Pattern Type
************

* **Parameter:** ``type``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

When defining each pattern, a pattern type must be specified. The pattern type is how the pattern will appear on the banner. For a list of acceptable pattern types, see the :ref:`table on patterns <patterns>` in the :ref:`appendix`.

The pattern type is defined as a string via the ``type`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'BANNER'
      amount: 1
      banner-patterns:
        '1':
          type: 'GRADIENT'

Pattern Color
*************

* **Parameter:** ``color``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

Each pattern must also contain a color. The color is the color of the actual pattern. For a list of acceptable pattern colors, see the :ref:`table on dye colors <dyecolors>` in the :ref:`appendix`.

The pattern color is defined as a string via the ``color`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'BANNER'
      amount: 1
      banner-patterns:
        '1':
          type: 'GRADIENT'
          color: 'ORANGE'

Combining Patterns
******************

Another pattern would be added by adding a new section under ``banner-patterns``, like so:

.. code-block:: yaml

    test-item:
      material: 'BANNER'
      amount: 1
      banner-patterns:
        '1':
          type: 'GRADIENT'
          color: 'ORANGE'
        '2':
          type: 'STRIPE_BOTTOM'
          color: 'RED'

.. note:: The section name of each pattern is irrelevant, but they must be different. Patterns should not have the same configuration section name. In the preceding examples, numbers were used.

.. note:: Patterns are parsed in the *order that they appear* in the ``banner-patterns`` section. This means that the second pattern defined above would be *stacked on top of* the first pattern. If a third pattern were defined, it would stack on top of the second pattern.

Book Metadata
#############

The following describes how to add various metadata to written books.

All of the following values are defined under a configuration section titled ``book-data``.

Title
*****

* **Parameter:** ``title``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

This value works exactly the same as the :ref:`name <meta_name>` of an item. To use color/formatting codes in the book title, use an ampersand (&) followed by either the letter or number corresponding to the desired color/text format. See this `list of color/formatting codes <https://minecraft.gamepedia.com/Formatting_codes#Color_codes>`__.

.. note:: If a book has both a name and a title, the name will take priority and will be shown.

The title of a book is defined as a string via the ``title`` parameter in the ``book-data`` section. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'WRITTEN_BOOK'
      amount: 1
      book-data:
        title: '&a&lThe Title of the Book'

Author
******

* **Parameter:** ``author``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The author of the book will be shown in the lore section of the written book. To use color/formatting codes in the book author, use an ampersand (&) followed by either the letter or number corresponding to the desired color/text format. See this `list of color/formatting codes <https://minecraft.gamepedia.com/Formatting_codes#Color_codes>`__.

.. note:: If a book has both an author and lore, the lore will take priority and will be shown.

The author of a book is defined as a string via the ``author`` parameter in the ``book-data`` section. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'WRITTEN_BOOK'
      amount: 1
      book-data:
        title: '&a&lThe Title of the Book'
        author: '&6magicmq'

Generation
**********

* **Parameter:** ``generation``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The generation of a book refers to its copy tier. As books are copied, their generation increases.

There are four acceptable values for this property:

* ``ORIGINAL``
* ``COPY_OF_ORIGINAL``
* ``COPY_OF_COPY``
* ``TATTERED``

Only books that are denoted ``ORIGINAL`` and ``COPY_OF_ORIGINAL`` can be copied. If a book has no generation, it is assumed to be ``ORIGINAL``.

.. note:: Tattered does not exist in normal gameplay and is functionally identical to ``COPY_OF_COPY``.

The generation of a book is defined as a string via the ``generation`` parameter in the ``book-data`` section. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'WRITTEN_BOOK'
      amount: 1
      book-data:
        title: '&a&lThe Title of the Book'
        author: '&6magicmq'
        generation: 'COPY_OF_ORIGINAL'

Pages
*****

* **Parameter:** ``title``
* **Type:** List of ``String``
* **Default:** N/A
* **Required:** Yes

The pages of a book are its actual contents. To use color/formatting codes in the item name, use an ampersand (&) followed by either the letter or number corresponding to the desired color/text format. See this `list of color/formatting codes <https://minecraft.gamepedia.com/Formatting_codes#Color_codes>`__.

The pages of a book are defined as a list of strings via the ``pages`` parameter in the ``book-data`` section. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'WRITTEN_BOOK'
      amount: 1
      book-data:
        title: '&a&lThe Title of the Book'
        author: '&6magicmq'
        generation: 'COPY_OF_ORIGINAL'
        pages:
        - '&6This is the first page.'
        - '&6This is the second page.'
        - '&6This is the third page.'
        - '&6This is the fourth page.'

Enchanted Book Metadata
#######################

* **Parameter:** ``book-enchantments``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

Enchanted books can be defined either by using the ``enchantments`` parameter as outlined in the :ref:`enchantments <enchantments>` section or via the ``book-enchantments`` parameter. Enchantments cannot be added in the same way as tools, armor, etc.

.. note:: Many enchantments exist only in certain versions of Minecraft and above. To be sure the enchantment you wish to use is available in the version you are using, see the "Version" column in the table on the `enchantments`_ page.

Enchantments that should be applied to the book are defined as a list of strings via the ``book-enchantments`` parameter in the format ``<enchantment type>:<enchantment level>``. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'ENCHANTED_BOOK'
      amount: 1
      book-enchantments:
      - 'SHARPNESS:5'
      - 'FIRE_ASPECT:2'

Firework Metadata
#################

The following describes how to add various metadata to firework rockets.

Power
*****

* **Parameter:** ``power``
* **Type:** Number (``integer``)
* **Default:** 1
* **Required:** No

The power of a firework rocket corresponds to its flight duration (how high it will go before exploding).

The power of a firework rocket is defined as a number via the ``power`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10

Firework Effects
****************

* **Parameter:** ``firework-effects``
* **Type:** Config Section (Each subsection is a firework effect)
* **Default:** Nothing (No firework effects)
* **Required:** No

The effects of a firework rocket are what will be shown in the sky when the firework rocket explodes. Each effect contains an effect type and a color at the least. Firework effects also can contain a fade color. In addition, you may also define whether the effect should have a flicker effect and a trail effect.

All of the following values should be contained within their own configuration section within the ``firework-effects`` section.

Type
----

* **Parameter:** ``type``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

The firework type refers to the shape of the firework effect in the sky as the firework rocket explodes. For a list of available types, see the :ref:`table of firework types <fireworkeffecttypes>` in the :ref:`appendix`.

The firework type is defined as a string via the ``type`` parameter in its corresponding configuration section under ``firework-effects``. Continuing the example from above, it would look something like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10
      firework-effects:
        '1':
          type: 'BALL_LARGE'

Colors
------

* **Parameter:** ``colors``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The colors of a firework effect refer to the colors shown when the firework rocket explodes. For a list of available colors, see the :ref:`table of firework colors <colors>` in the :ref:`appendix`.

Firework effect colors are defined as a list of strings via the ``colors`` parameter in its corresponding configuration section under ``firework-effects``. Continuing the example from above, it would look something like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10
      firework-effects:
        '1':
          type: 'BALL_LARGE'
          color:
          - 'RED'
          - 'WHITE'
          - 'BLUE'

Fade Colors
-----------

* **Parameter:** ``fade-colors``
* **Type:** List of ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The fade colors of a firework effect refer to the colors shown when the firework begins to fade some time after exploding. For a list of available colors, see the :ref:`table of firework colors <colors>` in the :ref:`appendix`.

Firework effect fade colors are defined as a list of strings via the ``fade-colors`` parameter in its corresponding configuration section under ``firework-effects``. Continuing the example from above, it would look something like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10
      firework-effects:
        '1':
          type: 'BALL_LARGE'
          colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'
          fade-colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'

Flicker and Trail
-----------------

* **Parameter:** ``flicker``, ``trail``
* **Type:** ``boolean``, ``boolean`` (true/false)
* **Default:** ``false``, ``false``
* **Required:** No, No
 
The flicker effect refers to the "twinkle" effect a firework rocket has as it begins to fade. The trail effect refers to a trail left behind by each individual firework star as it explodes outwards. See `this <https://minecraft.gamepedia.com/Firework_Rocket>`__ page for more details.

Both of these parameters are defined as a boolean (true/false) via the ``flicker`` and ``trail`` parameters in their corresponding configuration section under ``firework-effects``. Continuing the example from above, it would look something like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10
      firework-effects:
        '1':
          type: 'BALL_LARGE'
          colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'
          fade-colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'
          flicker: true
          trail: true

.. note:: The default values for these is false. If no flicker or trail is defined for an effect, it will be assumed that they are not wanted.

Combining Effects
-----------------

Multiple fireworks can be combined on a single firework rocket by creating a new configuration section under ``firework-effects`` for each effect. Continuing the example from above, it would look something like this: 

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_ROCKET'
      amount: 1
      power: 10
      firework-effects:
        '1':
          type: 'BALL_LARGE'
          colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'
          fade-colors:
          - 'RED'
          - 'WHITE'
          - 'BLUE'
          flicker: true
          trail: true
        '2':
          type: 'CREEPER'
          colors:
          - 'LIME'
          - 'BLACK'
          fade-colors:
          - 'LIME'
          - 'BLACK'
          flicker: true
          trail: true

Firework Star Metadata
######################

A firework star can be thought of as an individual "unit" of a firework rocket. Therefore, firework stars can contain an *individual* firework effect, unlike firework rockets, which can contain multiple. See the `Firework Effects`_ section above for available parameters for firework stars.

Instead of using the ``firework-effects`` section, the singular ``firework-effect`` section is used instead. This would look something like this:

.. code-block:: yaml

    test-item:
      material: 'FIREWORK_STAR'
      amount: 1
      firework-effect:
        type: 'BALL_LARGE'
        colors:
        - 'RED'
        - 'WHITE'
        - 'BLUE'
        fade-colors:
        - 'RED'
        - 'WHITE'
        - 'BLUE'
        flicker: true
        trail: true

Leather Armor Metadata
######################

* **Parameter:** ``armor-color``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

Leather armor can be dyed, changing its color. Predefined colors in the :ref:`dye colors table <dyecolors>` and :ref:`colors table <colors>` can be used. In addition, custom RGB value can be defined for greater flexibility over the color you want.

The color of leather armor is defined as a string via the ``armor-color`` parameter. It would look something like this:

.. code-block:: yaml

    test-item:
      material: 'LEATHER_CHESTPLATE'
      amount: 1
      armor-color: 'MAROON'

To define the color as an RGB value, separate the red, green, and blue value with commas. This would look something like this:

.. code-block:: yaml

    test-item:
      material: 'LEATHER_CHESTPLATE'
      amount: 1
      armor-color: '155,26,203'

Map Metadata
############

* **Parameter:** ``map-data``
* **Type:** Config Section
* **Default:** Nothing (No map data)
* **Required:** No

The following sections describe how to manipulate various metadata pertaining to maps.

All of the following values are defined under a configuration section titled ``map-data``.

Map Color
*********

* **Parameter:** ``color``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The color of a map can be changed. The color can be set as a predefined color in the :ref:`dye colors table <dyecolors>` or by using a comma-separated RGB value, in the same way as leather armor above.

..note:: An RGB value (with red, green, and blue value separated by commas) can be used in lieu of a predefined color. See `Leather Armor Metadata`_.

The color of a map is defined as a string via the ``color`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'MAP'
      amount: 1
      map-data:
        color: 'BLACK'

Map Scaling
***********

* **Parameter:** ``scaling``
* **Type:** ``boolean`` (true/false)
* **Default:** false
* **Required:** No

A map can be set as scaled to show a larger area than an unscaled map. The scale of a map is defined as a boolean (true/false) via the ``scaling`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'MAP'
      amount: 1
      map-data:
        color: 'BLACK'
        scaling: true

Map ID
******

* **Parameter:** ``id``
* **Type:** Number (``integer``)
* **Default:** 0
* **Required:** No

The primary use of maps in multiplayer Minecraft is to display custom images. Typically, this is accomplished by setting the ID of the map, as the ID of the map is used to interface with other plugins that actually change the image.

The ID of a map is defined as a number via the ``id`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'MAP'
      amount: 1
      map-data:
        color: 'BLACK'
        scaling: true
        id: 42

.. todo:: Mojang and Bukkit changed the way Map metadata is defined in 1.13. In this new format, there are more possibilities for drawing and displaying text and images. This new format is not currently supported but will be supported in the future.

Player Head Metadata
####################

* **Parameter:** ``player-head-data``
* **Type:** Config Section
* **Default:** Nothing (No head data)
* **Required:** No

Player heads can display either a skin of a player on the server or a custom skin for aesthetic purposes. Depending on which of these you choose, the configuration will be slightly different.

All parameters discussed in this section are defined within a section titled ``player-head-data``.

Player Heads for Existing Players
*********************************

* **Parameter:** ``armor-color``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

For player heads that show a skin belonging to an existing player, either the player's name or UUID can be used. This player is referred to as the skull owner.

The skull owner is defined as a string via the ``skull-owner`` parameter. It would look something like this:

.. code-block:: yaml

    test-item:
      material: 'PLAYER_HEAD'
      amount: 1
      player-head-data:
        skull-owner: '96c9a1d9-3def-4a5d-a1c5-d1c92cab3dcb'

.. note:: While player names can be used for the skull owner, UUIDs are preferred, as the lookup is quicker. Use the UUID if possible. Furthermore, using player names can produce inconsistent behavior with name changing.

Player Heads Having Custom Skins
********************************

* **Parameter:** ``skin-texture``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No
|
* **Parameter:** ``skin-name``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

For player heads that posess a custom skin not belonging to an existing player, the skin's Base64 texture value must be used. The skin's name can also be defined, but is not required. Base64 values for thousands of heads can be found at `minecraft-heads.com <https://minecraft-heads.com/custom-heads>`__. The Base64 texture value would be found in the "Value" field under the "Other" section of each skin on that site.

The skin value is defined as a string via the ``skin-texture`` parameter. The skin name is defined as a string via the ``skin-name`` parameter. It would look something like this:

.. code-block:: yaml

    test-item:
      material: 'PLAYER_HEAD'
      amount: 1
      player-head-data:
        skin-texture: 'eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWVmYmFiNWUzNDAxMDE3MzIyNjIyM2M3YTQ5NTEwMDI4ODlmNjkzNTdkYzIwODJiN2QyM2ZlZGUwMjA4YmMzNyJ9fX0='
        skin-name: 'Monitor'

Potion Metadata
###############

* **Parameter:** ``potion-data``
* **Type:** Config Section
* **Default:** Nothing (No potion data)
* **Required:** No

The following sections outline various metadata that can be applied to potions.

All of the parameters discussed in this section are defined within a section titled ``potion-data``.

Potion Type
***********

* **Parameter:** ``type``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The potion type is the effect that the potion should have on players once consumed or thrown. For a list of potion types, see the :ref:`potion type table <potiontypes>`. Note that any of the common names can be used in addition to the official name.

.. note:: Pay careful attention to the version of the potion type you are using. The potion types will not work if you are using a version lower than the listed version in the :ref:`potion type table <potiontypes>`.

The potion type is defined as a string via the ``type`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'SPLASH_POTION'
      amount: 1
      potion-data:
        type: 'REGEN'

Potion Level
************

* **Parameter:** ``level``
* **Type:** Number (``integer``)
* **Default:** 1 (Not upgraded)
* **Required:** No

The potion level refers to whether or not the potion is upgraded. An upgraded potion has stronger effects than its non-upgraded counterpart. See `this <https://minecraft.gamepedia.com/Brewing>`__ page for more details. Not all potion types are upgradeable, see the :ref:`potion type table <potiontypes>` to determine whether the potion type you are using is upgradeable. For any level greater than 1, the potion is considered to be upgraded.

The potion level is defined as a number via the ``level`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'SPLASH_POTION'
      amount: 1
      potion-data:
        type: 'REGEN'
        level: 2

Extended Potions
****************

* **Parameter:** ``extended``
* **Type:** ``boolean`` (true/false)
* **Default:** ``false``
* **Required:** No

Whether or not a potion is extended refers to its duration. Extended potions have effects that last longer than their non-extended counterparts. See `this <https://minecraft.gamepedia.com/Brewing>`__ page for more details. Not all potion types are extendable, see the :ref:`potion type table <potiontypes>` to determine whether the potion type you are using is extendable.

.. warning:: Potions cannot be both upgraded and extended at the same time! You will receive an error if you try to do this.

Whether or not a potion is extended is defined as a boolean (true/false) via the ``extended`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'SPLASH_POTION'
      amount: 1
      potion-data:
        type: 'REGEN'
        extended: true

Shield Metadata
###############

* **Parameter:** ``base-color``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes
|
* **Parameter:** ``shield-patterns``
* **Type:** Config Section (Each subsection is a pattern)
* **Default:** Nothing (No patterns)
* **Required:** No

Shields, like banners, can be adorned with various patterns. In ItemAPI, shield patterns are defined the exact same way as banner patterns, except the ``shield-patterns`` section is used instead of the ``banner-patterns``. Therefore, see `Banner Metadata`_ for details on patterns. Additionally, like banners, patterns can be stacked on top of each other.

Unlike banners, where the base color is specified in the material name, I.E. ``RED_BANNER``, a base color should be defined for shields. This represents the color of the bottom palette upon which all patterns will be placed. For a list of acceptable colors for the base color, refer to the :ref:`table on dye colors <dyecolors>` in the :ref:`appendix`.

The base color is defined as a string via the ``base-color`` parameter. Defining a shield would look something like this:

.. code-block:: yaml

    test-item:
      material: 'SHIELD'
      amount: 1
      base-color: 'RED'
      shield-patterns:
        '1':
          type: 'GRADIENT'
          color: 'ORANGE'

Another pattern would be added by adding a new section, like so:

.. code-block:: yaml

    test-item:
      material: 'SHIELD'
      amount: 1
      base-color: 'RED'
      shield-patterns:
        '1':
          type: 'GRADIENT'
          color: 'ORANGE'
        '2':
          type: 'STRIPE_BOTTOM'
          color: 'RED'

Shulker Box Metadata
####################

* **Parameter:** ``shulker-box-items``
* **Type:** Config Section (Each subsection is an item inside the shulker box)
* **Default:** Nothing (No items inside the shulker box)
* **Required:** No

The following describes how to add items to Shulker Boxes. Each section within ``shulker-box-items`` is an item. The name of each item's section is unimportant.

.. warning:: Shulker boxes cannot be placed inside of each other. An error will result if you attempt to do this.

Item
****

For defining items, all of the ItemAPI methodology still applies. Treat each section within ``shulker-box-items`` as an item defined with ItemAPI. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'SHULKER_BOX'
      amount: 1
      shulker-box-items:
        '1':
          material: 'DIAMOND_SWORD'
          amount: 1
          damage: 50
          name: '&c&lThis is a test name.'
          lore:
          - '&6This is the first line of the lore.'
          - '&6This is the second line of the lore'
          - '&6This is the third line of the lore.'
        '2':
          material: 'STONE'
          amount: 64
        '3':
          material: 'DIAMOND'
          amount: 64

Slot
****

* **Parameter:** ``slot``
* **Type:** Number (``integer``)
* **Default:** Nothing (Item will be added in the first available slot)
* **Required:** No

The slot parameter gives control over where items are placed within the shulker box. For inventories, slots begin at zero, not one. Therefore, the first slot would be slot 0, the second slot would be slot 1, and so on. If no slot is specified, the item will be added to the shulker box inventory in the first available slot.

The slot of the item is defined as a number via the ``slot`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'SHULKER_BOX'
      amount: 1
      shulker-box-items:
        '1':
          material: 'DIAMOND_SWORD'
          amount: 1
          damage: 50
          name: '&c&lThis is a test name.'
          lore:
          - '&6This is the first line of the lore.'
          - '&6This is the second line of the lore'
          - '&6This is the third line of the lore.'
          slot: 0
        '2':
          material: 'STONE'
          amount: 64
          slot: 2
        '3':
          material: 'DIAMOND'
          amount: 64
          slot: 3

Spawn Egg Metadata
##################

Spawn eggs are defined differently depending on the Minecraft version you are running.

1.13 and Above
**************

For Minecraft 1.13 and above, each spawn egg has a different material name in the format ``<MOB>_SPAWN_EGG``, and no additional parameters are needed to set the entity type. It would look something like this:

.. code-block:: yaml

    test-item:
      material: 'COW_SPAWN_EGG'
      amount: 1

Therefore, see the `material names <https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html>`__ for a list of available spawn eggs.

1.12 and Below
**************

* **Parameter:** ``mob-type``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

For Minecraft 1.12 and below, the mob type is defined as a string via the ``mob-type`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'MONSTER_EGG'
      amount: 1
      mob-type: 'COW'

For a list of available mobs to use, see `this <https://papermc.io/javadocs/paper/1.12/org/bukkit/entity/EntityType.html>`__ page.

Spawner Metadata
################

* **Parameter:** ``mob-type``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The type of mob that a spawner spawns can be customized. For a list of available mob types, see the :ref:`mob type table <entitytypes>` in the :ref:`appendix`.

The mob type of a spawner is defined as a string via the ``mob-type`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'SPAWNER'
      amount: 1
      mob-type: 'COW'

Tropical Fish Bucket Metadata
#############################

* **Parameter:** ``tropical-fish-bucket-data``
* **Type:** Config Section
* **Default:** Nothing (No tropical fish bucket data)
* **Required:** No

The fish contained within a tropical fish bucket can be customized in several ways. Their body color, pattern, and pattern color can all be changed. For a list of available patterns, see the :ref:`tropical fish patterns table <tropicalfishpatterns>`. For a list of available colors, see the :ref:`dye colors table <dyecolors>`.

All of the following values are defined under a configuration section titled ``tropical-fish-bucket-data``.

Fish Color
**********

* **Parameter:** ``body-color``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The body color of the fish in a tropical fish bucket can be changed. For a list of available colors, see the :ref:`dye colors table <dyecolors>` in the :ref:`appendix`.

The body color is defined as a string via the ``body-color`` parameter. It would look like this:

.. code-block:: yaml

    test-item:
      material: 'TROPICAL_FISH_BUCKET'
      amount: 1
      tropical-fish-bucket-data:
        body-color: 'ORANGE'

Fish Pattern
************

* **Parameter:** ``pattern``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

The pattern of the fish in a tropical fish bucket can be changed. For a list of available patterns, see the :ref:`tropical fish patterns table <tropicalfishpatterns>` in the :ref:`appendix`.

The pattern is defined as a string via the ``pattern`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'TROPICAL_FISH_BUCKET'
      amount: 1
      tropical-fish-bucket-data:
        body-color: 'ORANGE'
        pattern: 'STRIPEY'

Pattern Color
*************

* **Parameter:** ``pattern-color``
* **Type:** ``String``
* **Default:** Nothing (``null``)
* **Required:** No

Finally, the pattern color of the fish in a tropical fish bucket can also be changed. For a list of available colors, see the :ref:`dye colors table <dyecolors>` in the :ref:`appendix`.

The pattern color is defined as a string via the ``pattern-color`` parameter. Continuing the example from above, it would look like this:

.. code-block:: yaml

    test-item:
      material: 'TROPICAL_FISH_BUCKET'
      amount: 1
      tropical-fish-bucket-data:
        body-color: 'ORANGE'
        pattern: 'STRIPEY'
        pattern-color: 'WHITE'

Concluding Remarks
##################

In this section, metadata pertaining to specific items was discussed. Return to the :ref:`homepage <home>` or continue onward to :ref:`defining custom NBT data for items <nbtdata>`.