
-------------------------------------------
Setting up the IDE
-------------------------------------------

See the Forge Documentation online for more detailed instructions:
http://mcforge.readthedocs.io/en/latest/gettingstarted/

1) 
Install Java17+.
Version 17 is minimum requirement for Minecraft 19.2.

2) 
Install VSCode.
Install the extensions:
- Extension Pack for Java
- Language Support for Java(TM) by Red Hat
- Project Manager for Java
- Gradle for Java
- GitHub Pull Requests and Issues
- gitignore (by michelmelluso)

3)
Install GitHub Desktop.

4)
In GitHub Desktop:
Clone the repository athrane/monomagic to 
Windows: C:\Users\<user>\git\monomagic

5) 
In CMD:
gradlew genVSCodeRuns

6)
In VSCode:
Select "Get Started with Java development"
Select "Get your runtime ready"
Select "Install JDK" | Select "Edoptium's Temurin" | 17 (LTS) | Download
Wait for JDK to download.

In VSCode:
Windows: 
- Select File | Preferences | Settings
- Search for "JDK"
- Select the Workspace tab
- Edit Java Home in settings.json
(Please note: On Windows, backslashes must be escaped, i.e. "java.jdt.ls.java.home":"C:\\Program Files\\Java\\jdk-17.0_3")
- Set "java.jdt.ls.java.home":"C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.4.101-hotspot"
								
In VSCode:
On the "Get your runtime ready"
Select "Reload Window" .

8)
In VSCode:
Open java project folder:
Windows: C:\Users\<user>\git\monomagic

9) 
In VSCode:
Save workspaceAs:
Windows: C:\project\monomagic-vscode-ws\monomagic.code-workspace

10)
In VSCode:
Select Gradle 
Run Gradle build: clean build

11)
In VSCode:
Select Run | Open Configurations 
Find the "runClient" Configuration 
Do a Search and replace for "examplemod" with "monomagic".

Links:
https://dev.to/drazisil/setting-up-a-minecraft-mod-enviroment-in-vscode-it-s-easier-than-you-think-5bfc
http://mcforge.readthedocs.io/en/latest/gettingstarted/


-------------------------------------------
EOF
