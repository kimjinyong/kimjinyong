- 👋 Hi, I’m @kimjinyong
- 👀 I’m interested in ...
- 🌱 I’m currently learning ...
- 💞️ I’m looking to collaborate on ...
- 📫 How to reach me ...

<!---
kimjinyong/kimjinyong is a ✨ special ✨ repository because its `README.md` (this file) appears on your GitHub profile.
You can click the Preview link to take a look at your changes.
--->


# jnpf-java-boot
test test jenkins MR TEST
> Special note: The storage path of source code, JDK, MySQL, Redis, etc. is prohibited to contain Chinese, spaces, special characters, etc.

## Environmental requirements

> Official recommendation: JDK version is not lower than `1.8.0_281`, `OpenJDK 8`, `Alibaba Dragonwell 8`, `BiShengJDK 8` can be used

Project | Recommended Version | Description
-----|-----------------------------------| -------- -----
JDK | 1.8.0_281 | JAVA environment dependencies (environment variables need to be configured)
Maven | 3.6.3 | Project build (need to configure environment variables)
Redis | 3.2.100(Windows)/6.0.x(Linux,Mac) |
MySQL | 5.7.x+ | database choice (default)
SQLServer | 2012+ | Database of your choice
Oracle | 11g+ | Database of your choice
PostgreSQL | 12+ | Database of your choice
Dream Database | DM8 | Choose one database
National People's Congress Vault | KingbaseES V8 R6 | Choose one database

## Tool recommendation
> To prevent Maven from being downloaded normally, please use the following IDE versions

IDEA version | Maven version
-----|-------- |
IDEA2020 and above | Maven 3.6.3 and above |

## IDEA plugin

- `Lombok`
- `Alibaba Java Coding Guidelines`
- `MybatisX`

## Maven private server configuration

> After downloading the dependencies through the official private server, there may be some red flags due to IDEA's cache, just restart IDEA

#### The difference between JNPF official Maven private server and Alibaba Cloud Maven private server and Maven official package

- com.sqlserver:sqljdbc4:4.0
- com.oracle:ojdbc6:11.2.0
- com.dm:DmJdbcDriver18:1.8.0
- com.kingbase8:kingbase8-jdbc:1.0
- dingtalk-sdk-java:taobao-sdk-java-source:1.0
- dingtalk-sdk-java:taobao-sdk-java:1.0
- yozo:signclient:3.0.1

1. Open the `conf/settings.xml` file under `maven`

2. Add in `<servers></servers>`

````xml
  <server>
  <id>maven-releases</id>
  <username>jnpf-user</username>
  <password>HLrQ0MA%S1nE</password>
</server>
<server>
<id>maven-snapshots</id>
<username>jnpf-user</username>
<password>HLrQ0MA%S1nE</password>
</server>
````

3. Add in `<mirrors></mirrors>`

````xml
<mirror>
  <id>maven-snapshots</id>
  <mirrorOf>*</mirrorOf>
  <name>maven-snapshots</name>
  <url>https://repository.jnpfsoft.com/repository/maven-public/</url>
</mirror>
````

## Environment configuration
- Open `jnpf-admin/src/main/resources/application.yml`

> Environment variables
> - dev development environment
> - test test environment
> - preview pre-release environment
> - pro production environment

```` yml
  #environment dev|test|pro|preview
  profiles:
  active: dev
````

- Open `application-x.yml` (`x` means environment variable), configure the following
  - service port (`port`)
  - Table space (`tableSpace`, when the database is `Oracle`, `Dameng`, `Jincang`, the table space must be specified, other databases can be empty)
  - Database linkage
  - Redis
  - Static resources

## Startup project
- `jnpf-admin/src/main/java/JnpfAdminApplication.java`, right click to run.

### Project release

- Double-click `clean` in `Maven`-`jnpf-java-boot(root)`-`Lifecycle` on the right side of `IDEA` to clean up the project
- Double click on `package` to package the project
- Open the project directory, open `jnpf-java-boot\jnpf-admin\target` in turn, and upload `jnpf-admin-{version}-RELEASE.jar` to the server

### swagger interface documentation
- `http://localhost:30000/swagger-ui/`
