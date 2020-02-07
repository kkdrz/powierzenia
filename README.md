# powierzenia

## Co zostało zrobione

- szkielet aplikacji został wygenerowany na podstawie diagramu klas przy pomocy [jhipster]
- aplikacja posiada API REST uwzględniające wszystkie encje z diagramu klas (CRUD)
- aplikacja jest połączona z bazą danych (H2/Postgress/MySQL... zależy od konfiguracji)
- aplikacja zawiera testy jednostkowe oraz integracyjne, zarówno dla części backendowej jak i frontendu
- system jest zintegrowany z zewnętrznym serwerem autoryzacyjnym (substytut JSOSa)

### Wymagania funkcjonalne:
````
- CRU planu powierzeń zajęć z uwzględnieniem ograniczeń wynikających z rozporządzeń wewnętrznych.
- Definicja preferencji przez pracowników.
````

- wymagania funkcjonalne są w pełni zaimplementowane po stronie backendu, niestety nie starczyło czasu na dopracowanie części UI dla pierwszego z nich
- obydwie implementacje uwzględniają i sprawdzają reguły biznesowe i ograniczenia

### Wymagania jakościowe:
```
- Czas przywracania systemu po krytycznym błędzie poniżej 1 godziny w trakcie rekrutacji i na miesiąc przed nią, poza tym poniżej 3 godzin (Priorytet: Wysoki) 
    -   bez uwzględnienia replikacji bazy danych (schemat zostanie otworzony, dan przepadną)
    -   utworzono konfigurację kubernetes który automatycznie przywraca uszkodzone części systemu
- Wydajne pobieranie danych (zapytania, przeglądanie) – średni czas odpowiedzi systemu poniżej 5 s przy obciążeniu do 200 użytkowników (Priorytet: Średni)
    -   mocno zależy od sprzętu na którym aplikacja zostanie uruchomiona, ale na laptopie i7-8665U - 32GB RAM wymaganie zostało spełnione (sprawdzono za pomocą aplikacji JMeter)
- Migracja systemu na inny system operacyjny (np. do Linux z Windows) czy innego dostawcę serwera bazy danych (np. do Oracle z SQL Serwer) powinna zająć mniej niż 1 osobodzień pracy (Priorytet: Wysoki)
    -   wszystkie moduły aplikacji mogą być uruchomione w środowisku docker, które działa na każdym systemie operacyjnym
    -   zmiana dostawcy bazy danych sprowadza się jedynie do edycji parametrów w pliku C:\Users\drozdkon\projects\app-team10\src\test\resources\config\application.yml
```

## Development

Zalecane jest pracowanie w maszynie wirtualnej utworzonej przy pomocy Vagranta.
Maszyna ta zawiera wszystkie aplikacje potrzebne do rozwijania aplikacji.

    ./devMachine/Vagrantfile

Przed pierwszym uruchomieniem aplikacji i po każdej zmianie package.json, należy uruchomić:

    npm install

Aby aplikacja działała poprawnie należy uruchomić docker z serwerem autoryzacyjnym Keycloak:

    docker-compose -f .\src\main\docker\keycloak.yml up
    
Uruchom poniższe komendy w oddzielnych terminalach, aby uruchomić automatycznie odświeżające się moduły aplikacji.

    ./gradlew -x webpack
    npm start

## Budowanie na produkcję

### Pakowanie w jar

Ta komenda zbuduje finalny jar:

    ./gradlew -Pprod clean bootJar

Aby sprawdzić, czy budowanie zadziałało uruchom:

    java -jar build/libs/*.jar

Następnie przejdź do [http://localhost:8080](http://localhost:8080).

### Pakowanie jako war

Ta komenda zbuduje finalny war.

    ./gradlew -Pprod -Pwar clean bootWar

## Testy

Aby uruchomić wszystkie testy aplikacji uruchom:

    ./gradlew test integrationTest jacocoTestReport

### Testy części klienckiej

Testy jednostkowe są uruchamiane przy pomocy [Jest][] i napisane w  [Jasmine][]. Lokalizacja testów: [src/test/javascript/](src/test/javascript/). Uruchomienie:

    npm test

### Jakość kodu

Do oceny jakości kodu używamy Sonara. Uruchomienie dockera z Sonarem:
```
docker-compose -f src/main/docker/sonar.yml up -d
```

Możesz uruchomić analizę przy pomocy [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) lub przed gradle.

Uruchomienie analizy z gradlem:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

## Użycie Dockera

W lokalizacji [src/main/docker](src/main/docker) dostępne są pliki z różnymi serwisami, których można używać (baza danych, monitoring aplikacji, jenkins, sonar, keycloak).

Dla przykładu, uruchomienie Postgresql:

    docker-compose -f src/main/docker/postgresql.yml up -d

Aby zatrzymac i usunąć kontener:

    docker-compose -f src/main/docker/postgresql.yml down

Można też zapakowac aplikację do obrazu dockera:

    ./gradlew bootJar -Pprod jibDockerBuild

A następnie uruchomić:

    docker-compose -f src/main/docker/app.yml up -d

# Konfiguracja Kubernetes

## Przygotowanie

Musisz wypchnąć obrazy dockera do rejestru komendami:

```
$ docker image tag powierzenia pmorski/powierzenia
$ docker push pmorski/powierzenia
```

## Wdrożenie

Możesz wdrożyć aplikację uruchamiając skrypt:

```
./kubectl-apply.sh
```

## Skalowanie wdrożeń

Możesz skalować aplikację komendą:

```
$ kubectl scale deployment <app-name> --replicas <replica-count>
```

## zero-downtime deployments

The default way to update a running app in kubernetes, is to deploy a new image tag to your docker registry and then deploy it using

```
$ kubectl set image deployment/<app-name>-app <app-name>=<new-image>
```

Using livenessProbes and readinessProbe allow you to tell Kubernetes about the state of your applications, in order to ensure availablity of your services. You will need minimum 2 replicas for every application deployment if you want to have zero-downtime deployed.
This is because the rolling upgrade strategy first stops a running replica in order to place a new. Running only one replica, will cause a short downtime during upgrades.

[jhipster]: https://www.jhipster.tech
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
