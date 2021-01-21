.. _nbtdata:

NBT Data
========

NBT Data is a powerful tool that allows developers and users to add entirely custom data to items. This data will stick with the item until it is destroyed and will also persist across restwarts. ItemAPI supports adding a multitude of different NBT data to items. The possibilities are infinite!

All NBT data for an item should be contained within the ``nbt`` section.

NBT Tags
########

* **Parameter:** ``nbt``
* **Type:** Config Section
* **Default:** Nothing (No NBT data)
* **Required:** No

**At the very least, all NBT Tags require a type:**

* **Parameter:** ``type``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

**And a key:**

* **Parameter:** ``key``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

**More data is required depending on the NBT Tag Type. See below.**

An NBT Tag is the most fundamental unit when NBT Data is concerned. Taken simply, an NBT tag contains a single "unit" of data and can exist alongside other tags. All tags have an associated ``key`` and ``value``. The key is the tag's identifier and is used to lookup that tag. The value is the *actual data being stored*. Take this example:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'INT'
          key: 'first-tag'
          value: 20

In the example above, the diamond sword now has a custom NBT tag named "first-tag" with a value of 20. As you may be able to tell, each tag is defined under a new configuration section in the ``nbt`` section. The section name can be anything and has no effect on the tag itself, as long as the same name is not used more than once. The ``type`` field will be discussed in the next section.

Value-Based NBT Tags
####################

* **Parameter:** ``value``
* **Type:** Varies based on NBTTagType, see `Value-Based NBT Tags`_ and :ref:`NBT Type table <nbttypes>`
* **Default:** N/A
* **Required:** Yes

There are several different data types available for storing NBT data. These types can all be viewed in the :ref:`NBT Type table <nbttypes>` in the :ref:`appendix`. Compound types will be discussed later.

.. note:: The data type of the tag you are creating **must** be specified using the ``type`` parameter!

Types that Contain a Single Value
*********************************

Types that contain a single value all follow the same format. These types are ``BOOLEAN``, ``BYTE``, ``SHORT``, ``INT``, ``LONG``, ``FLOAT``, ``DOUBLE``, and ``STRING``.

They are defined by using the ``value`` field. For example, to define a tag of type ``DOUBLE``:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'DOUBLE'
          key: 'first-tag'
          value: 20.5129

Array Types
***********

``BYTE_ARRAY`` and ``INT_ARRAY`` are both defined in the same way.

They are defined by creating a list under the ``value`` field. For example, to define a tag of type ``INT_ARRAY``:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'INT_ARRAY'
          key: 'first-tag'
          value:
          - 0
          - 1
          - 2
          - 3
          - 4

List Types
**********

* **Parameter:** ``list-type``
* **Type:** ``String``
* **Default:** N/A
* **Required:** Yes

List types are defined somewhat differently than other types. As mentioned in the :ref:`NBT Type table <nbttypes>` in the :ref:`appendix`, lists can contain elements of type ``INT``, ``FLOAT``, ``DOUBLE``, ``LONG``, or ``STRING``.

.. note:: The type that a list contains **must** be specified using the ``list-type`` parameter!

The type that a list contains is specified using the ``list-type`` parameter. For example, to define a tag that contains a list of strings:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'LIST'
          list-type: 'STRING'
          key: 'first-tag'
          value:
          - 'First element'
          - 'Second element'
          - 'Third element'
          - 'Fourth element'
          - 'Fifth element'

.. note:: Color codes are not automtically parsed for ``STRING`` types or ``LIST`` types containing strings to allow for greater flexibility over the data being stored.

Compound Tags
#############

* **Parameter:** ``children``
* **Type:** Config Section (Each subsection is another NBT Tag)
* **Default:** N/A
* **Required:** Yes

Compound tags give real power and endless possibilities to NBT tags. Put simply, compound tags allow you to define NBT tags within NBT tags. This allows for NBT tags to be nested *recursively*. Take this example:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'COMPOUND'
          list-type: 'STRING'
          key: 'first-tag'
          children:
            '1':
              type: 'INT'
              key: 'first-inner-tag'
              value: 20
            '2':
              type: 'STRING'
              key: 'second-inner-tag'
              value: 'Value of the second-inner-tag'


The ``COMPOUND`` tag type has allowed for the creation of the ``children`` section, in which more NBT tags can be defined.

Therefore, instead of the ``value`` parameter discussed before, compound tags should have a ``children`` section instead. Subsequently, more tags can be defined within the ``children`` section. In the example above, ``first-inner tag`` and ``second-inner tag`` are also NBT tags, but they exist under the ``first-tag`` compound tag, not the root tag of the item.

Multiple compound tags can be nested within each other as well. Take this example:

.. code-block:: yaml

    test-item:
      material: 'DIAMOND_SWORD'
      amount: 1
      nbt:
        '1':
          type: 'COMPOUND'
          list-type: 'STRING'
          key: 'first-tag'
          children:
            '1':
              type: 'INT'
              key: 'first-inner-tag'
              value: 20
            '2':
              type: 'STRING'
              key: 'second-inner-tag'
              value: 'Value of the second-inner-tag'
            '3':
              type: 'COMPOUND'
              key: 'third-inner-tag'
              children:
                '1':
                  type: 'INT'
                  key: 'first-inner-inner-tag'
                  value: 50

``first-inner-inner-tag`` is nested within *two* tags.

Concluding Remarks
##################

In this section, NBT data was discussed. Return to the :ref:`homepage <home>` or continue to the :ref:`appendix`.