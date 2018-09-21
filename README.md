
# What is CommandSimplifyer

So as the name suggests it Simplify the use of commands.
This lightweight library makes it possible to give every command easily its own ```class``` and simplify the use to stop the use of hundreds of nesteld if statements


# So, how do I use this ? (the tutorial)

In this tutorial im going to next commands:

- ```/shop```
- ```/shop message <player> [message]```
- ```/shop admin```
- ```/shop admin open <player>```


## Step 1. Add de jar to your project

#### Option 1. Gradle (highly recommended)
1. [Download](https://github.com/RemcoVerrips/CommandSimplifyer/releases/) the latest release
2. Create a folder called ```libs``` in the root folder of gradle
3. Add the jar in ```build.gradle``` file
```gradle
dependencies {
    compile files('libs/CommandSimplifyerAPI-1.1.jar')
}
```
#### Option 2. Maven
1. [Download](https://github.com/RemcoVerrips/CommandSimplifyer/releases/) the latest release
2. Add the jar to the ```resource``` folder
3. Add the dependency to the ```pom.xml```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    ...
    <dependencies>
        <dependency>
            <groupId>me.darkrossi</groupId>
            <artifactId>commands</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/CommandSimplifyerAPI-1.1.jar</systemPath>
        </dependency>
    </dependencies>
</project>

```

#### Option 3.  add as Library in Intellij IDEA
1. [Download](https://github.com/RemcoVerrips/CommandSimplifyer/releases/) the latest release
2. Create a folder called ```libs``` in the root folder of gradle
3. Open Intellij IDEA and go to File → Product Structure... → Libraries
4. Click on the green ```+```  icon and choose ```Java``` 
5. Browse to the place where u saved the jar (<rootfolder>/libs)
6. Make sure that u include the code to the final jar file!

## Step 2. Define the Commands 
First u have to create a CommandClass for example ```PexCommand``` (i use <command name>Command as nameing convention for my command classes).

requirements class:
- (Implents) ICommand
- (method) onCommand
- (method) missingArguments

##### example:


```ShopCommand.java```
```java

import me.darkrossi.commands.ICommand;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class ShopCommand implements ICommand {

    public Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArgs) {
        Main.menu.show(paramPlayer);
        return true;
    }

    public void missingArguments(Player paramPlayer, String paramMissingArg) {
        paramPlayer.sendMessage("Missing argument override " + paramMissingArg);
    }

    public void noPermission(Player paramPlayer){
        paramPlayer.sendMessage("No permission message override");
    }
}

```

```ShopMessageCommand.java```
```java

import me.darkrossi.commands.ICommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ShopMessageCommand implements ICommand {

    public Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArgs) {
        paramPlayer.sendMessage(paramArgs.get("user") + ": " + paramArgs.get("text"));
        return true;
    }

}

```

```ShopAdminCommand.java```
```java

import me.darkrossi.commands.ICommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ShopAdminCommand implements ICommand {
    public Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArgs) {
        return true;
    }
}


```


```ShopAdminOpenCommand.java```
```java

import me.darkrossi.commands.ICommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ShopAdminOpenCommand implements ICommand {
    public Boolean onCommand(Player paramPlayer, HashMap<String, String> paramArgs) {
        paramPlayer.sendMessage("Admin open: ");
        paramPlayer.sendMessage(paramArgs.get("user"));

        return true;
    }
}

```


### Step 3. Register the commands

There are 4 things u can to do:
- Create commands
- Initalize Commands
- Create arguments (optional)
- set childs (optional)

##### example:
```java
import me.darkrossi.commands.CommandArgument;
import me.darkrossi.commands.CommandHandler;
import me.darkrossi.commands.CommandTree;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Mainer extends JavaPlugin {

    @Override
    public void onEnable() {
        commandRegister();
    }

    @Override
    public void onDisable() {
        // Code
    }

    private void commandRegister() {
        // Define Arguments
        CommandArgument userArgument = new CommandArgument("user", false, true);
        CommandArgument messageArgument = new CommandArgument("text", true, true);

        // Bundeld arguments
        List<CommandArgument> shopMessageArguments = new ArrayList<>();
        shopMessageArguments.add(userArgument);
        shopMessageArguments.add(messageArgument);

        // Define Commands
        CommandTree<String> shop = new CommandTree<String>("shop", new ShopCommand(), "shop.shop");
        CommandTree<String> shopMessage = new CommandTree<String>("message", new ShopMessageCommand(), shopMessageArguments, "shop.message");
        CommandTree<String> shopAdmin = new CommandTree<String>("admin", new ShopCommand(), "shop.admin");
        CommandTree<String> shopAdminOpen = new CommandTree<String>("open", new ShopAdminOpenCommand(), userArgument, "shop.admin.open");

        // Connect Commands
        shop.addChild(shopMessage);
        shop.addChild(shopAdmin);
        shopAdmin.addChild(shopAdminOpen);

        // Done
        new CommandHandler(shop);
    }
}
```