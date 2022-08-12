# Dungeon Crawl

## what is it?
This is a 2 sprint team project, a Roguelike game using JavaFX canvas.
The character fights and can pickup items and move to another level.
The game has save and load functions.
It uses PSQL as database and DAO design pattern for back-end.

Requirements:
- JAVA 17
- Postgresql 42.2.5
- OpenJFX 12.0.2
- Maven 3.8.0

Open pom.xml in Intellij and setup Java 17 SDK and com.codecool.dungeoncrawl.App for main to run.
You need to connect to a Posgresql database.
Use environment variables:
- PSQL_USER_NAME
- PSQL_PASSWORD
- PSQL_DB_NAME

#### How to play:
- Ctrl + S: Save game
- Move: Arrows

- **Left side panel:**
- Pick up: pick up item
- Load button: load previous saved game

- If you try to move into enemies you can attack them.
- Enemies can also attack first. When that happens your character automatically hits back.
- If your health goes down , you die.
- If you kill and enemy it will disappear.
- Weapons can give you more damage.
- Pick up keys to open doors.

