# Spring Beavarts Application 
This is a project designed for the ISPP course. The main parts of the project are:
- Anuncios: Section where you can make new ads and get known by million of artists.
- Encargos: Section where you can make new petitions to several artists.
- Portfolio: Section in your main proffile where other people can see your crafts


## Understanding the Spring Beavarts application
<a href="https://beavartsispp.wixsite.com/home">Watch our main landing page here</a>

## Running beavarts locally
Beavarts is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/ivanhr3/BeavArts
cd spring-beavarts
./mvnw package
java -jar target/*.jar
```

You can then access beavarts here: http://localhost:8080/

<img width="1042" alt="beavarts-screenshot" src="https://i.gyazo.com/94db72ea2e57d8c5f8a4fca7f0474d5e.png">

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

## In case you find a bug/suggested improvement for Spring Beavarts
Our issue tracker is available here: https://github.com/ivanhr3/BeavArts/issues


## Database configuration

In its default configuration, Beavarts uses an in-memory database (H2) which
gets populated at startup with data. In development it can use the MySQL proffile, just modify the code line in application.properties.

## Working with Beavarts in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 8 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line
```
git clone https://github.com/ivanhr3/BeavArts
```
2) Inside Eclipse or STS
```
File -> Import -> Maven -> Existing Maven project
```

Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA

In the main menu, choose `File -> Open` and select the Beavarts [pom.xml](pom.xml). Click on the `Open` button.

CSS files are generated from the Maven build. You can either build them on the command line `./mvnw generate-resources`
or right click on the `spring-beavarts` project then `Maven -> Generates sources and Update Folders`.

A run configuration named `BeavartsApplication` should have been created for you if you're using a recent Ultimate
version. Otherwise, run the application by right clicking on the `BeavartsApplication` main class and choosing
`Run 'BeavartsApplication'`.

4) Navigate to Beavarts

Visit [http://localhost:8080](http://localhost:8080) in your browser.


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [BeavartsApplication](https://github.com/ivanhr3/BeavArts/blob/master/src/main/java/org/springframework/samples/petclinic/BeavartsApplication.java) |
|Properties Files | [application.properties](https://github.com/ivanhr3/BeavArts/blob/master/src/main/resources/application.properties) |

# Contributing

The [issue tracker](https://github.com/ivanhr3/BeavArts/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <https://editorconfig.org>. If you have not previously done so, please fill out and submit the [Contributor License Agreement](https://cla.pivotal.io/sign/spring).

# License

The Spring Beavarts application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[spring-petclinic]: https://github.com/spring-projects/spring-petclinic
[spring-framework-petclinic]: https://github.com/spring-petclinic/spring-framework-petclinic
[spring-petclinic-angularjs]: https://github.com/spring-petclinic/spring-petclinic-angularjs 
[javaconfig branch]: https://github.com/spring-petclinic/spring-framework-petclinic/tree/javaconfig
[spring-petclinic-angular]: https://github.com/spring-petclinic/spring-petclinic-angular
[spring-petclinic-microservices]: https://github.com/spring-petclinic/spring-petclinic-microservices
[spring-petclinic-reactjs]: https://github.com/spring-petclinic/spring-petclinic-reactjs
[spring-petclinic-graphql]: https://github.com/spring-petclinic/spring-petclinic-graphql
[spring-petclinic-kotlin]: https://github.com/spring-petclinic/spring-petclinic-kotlin
[spring-petclinic-rest]: https://github.com/spring-petclinic/spring-petclinic-rest