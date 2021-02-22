![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/dev.magicmq/itemapi?nexusVersion=3&server=https%3A%2F%2Frepo.magicmq.dev)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/SpongePowered/Configurate/Build%20And%20Test/master)
[![Documentation Status](https://readthedocs.org/projects/itemapi-documentation/badge/?version=latest)](https://itemapi-documentation.readthedocs.io/en/latest/?badge=latest)
[![MIT License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)

# ItemAPI

ItemAPI was written to fill the need for a complete, clean, efficient, and most importantly standardized format for defining items in configuration files. Never worry about writing code to parse items from config files again: ItemAPI has you covered!

The library has many features, including:
* A standardized and easy-to-read format for defining a wide variety of items in configuration files
* Full support for defining custom NBT tags on items
* Full support for all existing item metadatas: banners, books, fireworks, leather armor, maps, player heads, potions, shields, shulker boxes, spawn eggs, spawners, and tropical fish buckets are all supported
* Support for serializing existing items into the standardized format in a config file automatically
* Support for serialization of items into a compact and efficient base64 format for storage in databases and other space-sensitive mediums
  * Items stored in this format are also guaranteed to load in future versions as well, unlike Bukkit's Base64 serialization
* Support for multiple configuration APIs, including Bukkit's YAML API and Configurate 
* Fully-documented formatting so end users know exactly how to define items, NBT tags, and metadata
* Complete [Javadocs](https://docs.magicmq.dev/itemapi) for developers
* Complete [wiki](https://github.com/magicmq/ItemAPI/wiki) for developers
* Full support for Minecraft `1.12.2` through `1.16.4`
* *Free* and *open-source*!
* And much, much more!

## For Users

The vast majority of relevant information for users of the API can be found in the [documentation](https://itemapi-documentation.readthedocs.io/en/latest/). The documentation contains all formatting information on defining items in configuration files.

## For Developers

### General Information

The Javadocs for ItemAPI can be viewed [here](https://docs.magicmq.dev/itemapi). View the [wiki](https://github.com/magicmq/ItemAPI/wiki) for a getting started page and some advanced tutorials.

ItemAPI supports the usage of either the [Bukkit Configuration API](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/configuration/ConfigurationSection.html) or [Configurate](https://github.com/SpongePowered/Configurate). This means that while YAML is the preferred method of storage, JSON, HOCON, and XML are also supported configuration formats through Configurate.

ItemAPI utilizes the [Item-NBT-API](https://github.com/tr7zw/Item-NBT-API) for adding NBT data to and extracting it from items. This library is automatically shaded into ItemAPI. You do not need to import/shade it yourself. I am considering implementing my own code to interface with NBT in the future.

### Planned Features (Not in order of priority):

* RGB/hex color support for color codes in item display names, lores, and book titles, authors, and pages.
* Utilizing the new methodology for setting MapMeta via the [MapView](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/map/MapView.html) while stil providing backwards compatibility for setting map IDs.
* Implementing standalone code for interfacing with item NBT data without requiring a third-party API (Item-NBT-API).
* Expanded API for serializing: addition of methods to convert item config section to a single-line string as well as methods to parse a single-line string (much like the Essnetials format)
* &#9989; ~~Expanded API for serializing: addition of methods to convert WrappedItem and all associated data to a forwards-compatible base64 string for more compact/efficient storage~~
* &#9989; ~~Expanded API for WrappedItem: addition of methods to extract custom data from the item config section. This will eventually lead to removal of ChancedWrappedItem and GuiWrappedItem~~
* &#9989; ~~Support for shulker boxes~~

### Using ItemAPI as a Dependency

&#9888;&nbsp;*Note: This dependency must be shaded into your plugin to work correctly! This is not a standalone plugin. See the getting started page for information on how to shade this into your plugin.*

#### Maven

Add the following repository:
``` maven
<repositories>
    <repository>
        <id>magicmq-repo</id>
        <url>https://repo.magicmq.dev/repository/maven-releases/</url>
    </repository>
</repositories>
```
Then, add the following dependency:
``` maven
<dependency>
    <groupId>dev.magicmq</groupId>
    <artifactId>itemapi</artifactId>
    <version>{VERSION}</version>
</dependency>
```
Replace `{VERSION}` with the version shown above.

#### Gradle

Add the following repository:
``` groovy
repositories {
    ...
    magicmq-repo { url 'https://repo.magicmq.dev/repository/maven-releases/' }
}
```
Then, add the following dependency:
``` groovy
dependencies {
        implementation 'dev.magicmq.itemapi:{VERSION}'
}
```
Replace `{VERSION}` with the version shown above.

#### Manual Usage

Releases are also published on GitHub [here](https://github.com/magicmq/ItemAPI/releases). You may download the JAR and import it yourself into your IDE of choice, or you may install it into your local repository.

### Building

Building requires [Maven](https://maven.apache.org/) and [Git](https://git-scm.com/). Maven 3+ is recommended for building the project. Follow these steps:

1. Clone the repository: `git clone https://github.com/magicmq/ItemAPI.git`
2. Enter the repository root: `cd ItemAPI`
3. Build with Maven: `mvn clean package`
4. Built files will be located in the `target` directory.

### Issues/Suggestions

Do you have any issues or suggestions? [Submit an issue report.](https://github.com/magicmq/ItemAPI/issues/new)
