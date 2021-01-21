.. ItemAPI Documentation documentation master file, created by
   sphinx-quickstart on Sat Jan 16 22:46:47 2021.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

.. _home:

Welcome to ItemAPI's documentation!
===================================

The aim of ItemAPI is to both standardize and simplify the definition of Minecraft items in configuration files.

The entire API was written with simplicity and readibility in mind.

.. warning:: This API was only built to support Minecraft versions 1.12 and above. While ItemAPI theoretically *should* support versions lower than 1.12, there is no guarantee of full functionality no support will be offered.

For Users
#########

View documentation for how to define items :ref:`here <baseconfig>`. Note: all documentation on this site assumes you will be defining items in your config according to the `YAML specification index <https://yaml.org/spec/>`__. Because this project also supports JSON, HOCON, and XML as well, contact the specific developer on instructions for how to use these formats to define items.

Documentation on how to define general metadata such as name and lore can be found on the :ref:`metadata <metadata>` page. For more item-specific metadata, see the `Navigation`_ section below.

If you encounter any issues with parsing your config and you are using the YAML format, I highly recommend passing your config through a YAML parser such as `this one <https://yaml-online-parser.appspot.com/>`__. This will allow you to quickly identify any issues with formatting.

For Developers
##############

* Javadocs for this project can be viewed `here <https://docs.magicmq.dev/something>`__.
* Including the library in your own projects is outlined on the ItemAPI's `Github Page <https://github.com/itemapi>`__.
* This project also supports Configurate, meaning JSON, HOCON, and XML formats are also supported in addition to the traditional Bikkit YAML format. For information on Configurate, see the Javadocs as well as the `Configurate Github Page <https://github.com/SpongePowered/Configurate>`__.
* For licensing information, see the :ref:`license` page.

Navigation
##########

.. toctree::
   :maxdepth: 2

   baseconfig.rst
   metadata.rst
   itemspecificmetadata.rst
   nbtdata.rst
   appendix.rst
   license.rst
