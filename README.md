# Compilation

```
mvn package
```

# Usage
```
java -jar target/TresorQuest-1.0-SNAPSHOT.jar {inputFile} {outputFile}
```

# TODO
Improvements to consider:
- Add a map to the Board class to index every entity by their position to improve performance (eg `Map<Position, Set<Entity>>`)
- Integration tests to test main class with I/O
- Generify the entity parsing in a similar way as the serializer to make adding new entity types easier