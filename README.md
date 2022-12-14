# MiScript (misc)
### A separate scripting system for papermc, to get simple stuff working quickly and easily without complete new plugins

# Getting started
Use the .jar file in the releases to install the plugin onto the server.

## Making your first script
Creating scripts is not hard and done pretty quickly. To start, you should have run the server with the plugin installed at least ONCE.
From there, head into the `miscript` folder directly inside the minecraft server root directory. There, create a new file called `helloworld.toml`.
```toml
name = "helloworld"
description = "helloworld script that just greets the server on startup"
filepath = "miscript/helloworld.mi"
module = "hello"
authors = [ "crxyne" ]
```
Here you add all the info misc will need to execute your scripts. We will find out what the `module` here means exactly in a bit.
## Adding the code
As you have set in your script info file, there is another file path that points to the actual code of your script. For simple purposes of this quick start,
here is the code for a simple hello-world script:
```mi
mod hello {

    fn on_enable {
        use misc;
        console_log("&dhiiii! >:3");
    }

}
```
The first line dictates that we will be using a module called 'hello'. THIS is what you set in your script info file as the `module` key. It acts like a 
sort of "packaging" / categorizing for your code. The main function `on_enable` is always required (there is also `on_disable`, but it is not needed).
In this example, all the function does is message the console with 'hiiii! >:3' in a pink text.
## Testing the code
Once you have done this setup, simply reload or restart the server to see an effect (for now, there will be [re]loading commands set too, but later).
