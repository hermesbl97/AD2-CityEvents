# Para lanzar newman en local:
newman run -e pruebas.postman_environment.json cityEvents2.postman_collection.json

# Para ejecutar el proyecto 
mvn spring-boot:run

# Para empaquetar el proyecto
mvn package

# Para borrar el empaquetado
mvn clean package

# Para ejecutar el jar 
java -jar cityEvents2-0.1.jar

# Para crear una imagen (damos nombre de city-events2-api)
docker build -t city-events2-api . 
