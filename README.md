![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/dev.magicmq/itemapi?nexusVersion=3&server=https%3A%2F%2Frepo.magicmq.dev)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/SpongePowered/Configurate/Build%20And%20Test/master)
[![Documentation Status](https://readthedocs.org/projects/itemapi-documentation/badge/?version=latest)](https://itemapi-documentation.readthedocs.io/en/latest/?badge=latest)
[![MIT License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)

# ItemAPI

ItemAPI is a library designed to make defining/parsing items from configurations simple. The entire API was written with *readability* in mind. This means that while item definitions in config files can be verbose and take up much space, this was intentionally done such that item configuration could be read as easily as possible.

ItemAPI currently officially supports Minecraft versions `1.12.2` through `1.16.4`.

## For Users

The vast majority of relevant information for users of the API can be found in the [documentation](https://itemapi-documentation.readthedocs.io/en/latest/). The documentation contains all information on how to define items in configuration files.

## For Developers

### General Information

The Javadocs for ItemAPI can be viewed [here](https://docs.magicmq.dev/something). A Wiki with tutorials/examples is coming soon.

ItemAPI supports the usage of *either* the [Bukkit Configuration API](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/configuration/ConfigurationSection.html) or [Configurate](https://github.com/SpongePowered/Configurate). This means that while YAML is the preferred method of storage, JSON, HOCON, and XML are also supported configuration formats through Configurate.

ItemAPI utilizes the [Item-NBT-API](https://github.com/tr7zw/Item-NBT-API) for adding NBT data to and extracting it from items. I am considering implementing my own code to interface with NBT in the future, but for now, this library will be automatically shaded in with ItemAPI as it is required.

#### Planned Features:

* RGB color support for color codes in item display names, lores, and book titles, authors, and pages.
* Utilizing the new methodology for setting MapMeta via the [MapView](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/map/MapView.html) while stil providing backwards compatibility for setting map IDs.
* Implementing standalone code for interfacing with item NBT data without requiring a third-party API (Item-NBT-API).

### Using Rappu as a Dependency

&#9888;&nbsp;*Note: This dependency must be shaded into your plugin to work correctly! This is not a standalone plugin.*

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

### Issues/Suggestions

Do you have any issues or suggestions? [Submit an issue report.](https://github.com/magicmq/ItemAPI/issues/new)
