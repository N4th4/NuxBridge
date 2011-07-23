NuxBridge
===========

NuxBridge is a plugin that link your forum's ranks and Permissions (It only works with SMF).


Installation
------------

* First, you need the Permissions (3) plugin ([here](http://forums.bukkit.org/threads/admn-dev-permissions-3-1-6-the-plugin-of-tomorrow-935.18430/)).
* Download the latest jar [here](https://github.com/N4th4/NuxBridge/downloads).
* Copy the downloaded jar file into the plugins folder and rename it to "NuxBridge.jar".

Configuration
-------------

The configuration file is : plugins/NuxBridge/config.yml

Example :

    url: mysql://localhost:3306/forum
    user: forum
    passwd: forum_passwd
    default_id: 0
    worlds :
        - world
        - world_nether
    groups :
        '0': Noob
        '1': Admin
        '2': Moderateor
        '9': Players
        '10': InJail