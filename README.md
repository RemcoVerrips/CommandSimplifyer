
# What is CommandSimplifyer

So as the name suggests it Simplify the use of commands.
This lightweight library makes it possible to give every command its own ```class``` and simplify the use to stop the use of 


# So, how do I use this ? (the tutorial)

In this tutorial im using [PermissionEX](https://dev.bukkit.org/projects/permissionsex) as example (not all commands are 100% pex (some are simplified))


## Step 1. Add de jar to your project

#### Option 1. Gradle (highly recommended)
1. [Download](https://github.com/RemcoVerrips/CommandSimplifyer/releases/) the latest release
2. Create a folder called ```libs``` in the root folder of gradle
3. Add the jar in ```build.gradle``` file
```gradle
dependencies {
    compile files('libs/CommandSimplifyer.jar')
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
            <systemPath>${project.basedir}/src/main/resources/CommandSimplifyer-1.0.jar</systemPath>
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

```java
import me.darkrossi.commands.ICommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PexUserCommand implements ICommand {

    public Boolean onCommand(Player player, HashMap<String, String> args) {
        player.sendMessage(args.get("user")); //"user" is the defined name argument from step 3.
        return true;
    }

    public Boolean missingArguments(Player player, String arg){
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
private void commandRegisterPex() {
    // Arguments
    CommandArgument userArgument = new CommandArgument("user", 1, true);
    CommandArgument groupArgument = new CommandArgument("group", 1, true);

    // (Sub)Commands
    CommandTree<String> pex = new CommandTree<String>("pex", new PexCommand());
    CommandTree<String> pexUser = new CommandTree<String>("user", new PexUserCommand(), userArgument);
    CommandTree<String> pexUserGroup = new CommandTree<String>("group", new PexUserGroupCommand(), groupArgument);
    CommandTree<String> pexGroup = new CommandTree<String>("group", new PexGroupCommand(), groupArgument);

    // Set Child's
    pex.addChild(pexUser);
    pex.addChild(pexGroup);
    pexUser.addChild(pexUserGroup);

    // Initialize Commands
    new CommandHandler(pex);
}
```
