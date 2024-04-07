**Introduction**
	- Project is build using Gradle
	- Java Version: 21
	- Used libraries: Jackson for JSON parsing
**Getting Started**
If there is no Gradle tool present on your computer, after downloading and unpacking the zip-file open project location in terminal and run `./gradlew build` or `./gradlew.bat build` for Windows.
For the first time it may fail, then after running command again everything should be fine.

**If you don't want to use Gradle**- all the essentials are provided outside the project as tasked, for that  see section ***Specification***.

**Running Program**
After build and changing the absolute path of config files we can run project simply using .`\gradlew run`.

**Specification**
In case of my project after run command there will be displayed output for 2 baskets that were provided with the exercise.

- My source code for library is located in ==*app/src/main/java/com/ocado/basket*== and also outside the project workspace in **Source_Codes** folder
	- Provided baskets that are used by the class are in ==*app/src/main/resources*==

- My **fat jar** file is located outside the project
- My tests source code is located in *==app/src/test/java/com/ocado/basket==* and outside the project workspace in **Source_Codes** folder
	- My config files used in tests are in *==app/src/test/resources==* and outside of the project in **Test_configs** folder
	
**Sources**
*Gradle Docs*: https://docs.gradle.org/current/userguide
*Jackson Repo*: https://github.com/FasterXML/jackson-databind/#5-minute-tutorial-streaming-parser-generator