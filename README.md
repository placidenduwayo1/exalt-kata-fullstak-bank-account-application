# 💰 **Bank Account** 💰

# Sujet

Ce kata est un challenge d'[architecture hexagonale](https://fr.wikipedia.org/wiki/Architecture_hexagonale), il s'implémente par étape avec un **1er focus sur le domaine métier**.
 

### Etape 1 - Le modèle métier

Implémentation de la logique métier d'un compte en banque:

```
Fonctionnalités attendues:

1. Dépot d'argent
2. Retrait d'argent
3. Consulter le solde actuel
4. Consulter les transactions précédentes

```

Proposer une modélisation objet adaptée des entités necéssaires à ces fonctionnalités.


### Etape 2 - Adapteur API

Exposition des différentes fonctionnalités sur une API REST _(ex: Java -> Spring, .NET -> ASP.NET)_

### Etape 3 - Adapteur de Persistence

Implémentation d'un adapteur de persistence de votre choix _(ex: SQLlite, H2, ...)_.


## ⚠️ Modalités de candidatures ⚠️

>Le kata a volontairement un scope très large : il a vocation à être affiné tout au long de votre montée en compétence chez Exalt. Pour une candidature cependant, concentrez-vous sur **l’implémentation d’un domaine métier complet**, testé, et accessible depuis **une API Rest ou une interface graphique**.

> **Pour le rendu, Poussez sur une branche git, ouvrez une merge request vers Main, et notifiez votre interlocuteur par message que le kata est fini.**

## Bonne chance !


![archi-hexa](./assets/hexa-schema.png)



___



# Objectif & contexte: mise en place d'un projet en architecture héxagonale.




Dans certains pans de l’industrie, **la logique métier n’a pas le droit à l’erreur**. Ex : Secteur bancaire. Dans ces cas, le code responsable du métier doit être aussi isolé que possible, afin d’être **protégé** de la lourdeur des dépendances aux frameworks externes. 

C’est la promesse de l’[architecture hexagonale](https://fr.wikipedia.org/wiki/Architecture_hexagonale). On définit dès le début la logique métier stricte de notre application, et tout composant externe (ex : Base de données, Controler API, CLI, …) est référencé **au travers de l’abstraction d’une interface**. 

Par exemple, on ne se souciera pas de savoir si notre base de données est Postgresql ou SQLite. Notre code métier interagit avec une interface « RelationalDatabase », et le choix d’une solution de base de données plutôt qu’une autre intervient aussi tard que possible dans le processus de développement. Comme dans une équation mathématique, où on remplace les x et les y au dernier moment. 

Ainsi, on isole la logique du métier des dépendances aux différents frameworks qui composent le software. **La logique métier peut donc être modifiée, testée, validée indépendamment.**


# Specification [RFC2119](https://microformats.org/wiki/rfc-2119-fr) du kata

**1. Implémentation de la logique métier d’un compte bancaire, de manière isolée et protégée** 



* Les fonctions implémentant la logique du compte bancaire, par exemple « DepositMoney(int amount) » `DOIVENT` fonctionner indépendamment de toute notion d’API / de base de données. Pour ce faire, ces composants externes `DOIVENT` être représentés de manière abstraite, par des Interfaces. 



* Les fonctions métier `DEVRAIENT` être transparentes vis-à-vis des use-cases métier qu’elles implémentent _(pattern Use-Case, Spec as Code)_



**2. Créations d’adapteurs autour de notre noyau métier**

- Adapteur Web : création d’un contrôleur API. Routes http servant les fonctions du domaine métier. Les routes `DEVRAIENT` suivre les conventions de nommage [OpenAPI](https://restfulapi.net/resource-naming/) (verbes, URI, codes de retour, …) 



- Adapteur Persistance : implémentation de l’interface de persistance de données. L’implémentation `PEUT` être par exemple: 

    * Une base de données gérée avec un ORM 
    * Un fichier .csv, ça fait le travail ;) 



L’architecture hexagonale, contrairement à l’architecture MVC, impose de développer & valider le domaine métier avant de travailler toute autre brique logicielle. Votre historique de commit `DEVRAIT` refléter cet ordre. Ce domaine `DOIT` être validé par des tests unitaires _(exemple Java : Junit5 + @parameterizedTest)_. Pour l’implémentation des tests, le candidat `PEUT` utiliser une approche [TDD](https://fr.wikipedia.org/wiki/Test_driven_development). 

# Réalisation

## Description

- **BankAccount** est une application orientée microservices avec des microservices métiers et des microservices utilisataires
- Les microservices métiers: ***customer***, ***account*** et ***operation***
    - chaque microservice métier est implementé dans une achitecture hexagonale
    - les microcroservices métiers ***customer*** et ***account*** communiquent entre eux
    - les microcroservices métiers ***account*** et ***operation*** communiquent aussi entre eux
    - chaque microservice métier possède sa propre base de données et peut évoluer en environnement différent des autres microservices
- Les microservices utilitaires: ***config-server*** et ***gateway-service***
    - config-server: pour externaliser et distribuer les configurations aux autres microservices
    - gateway-service: pour router dans les deux sens les requêtes entre le front et le back
- Le frontend est une application en Angular

## Modélisation conceptuelle
![modeling](./assets/exalt-bank-account-conception.png)

## Architecure de l'application orientée microservice
![application-archi](./assets/exalt-bank-account-archi.png)

L'application orientée microservice **BankAccount** est dimensionnée comme suit:

## Backend
- 3 microservices métiers (business microservices)
- chaque microservice métier mappe une base données ***PostgreSQL*** déployée dans  un ***docker container***
- 2 microservices utilitaires (utils microservices)

### Microservices métiers

- ***business-microservice-customer***
- ***business-microservice-account***
- ***business-microservice-operation***

### Microservices utilitaires

- ***microservices-config-server***
    - cet utilitaire récupère les configurations depuis la branche ***feature/configurations*** et les distribuent aux autres microservices à leur démarrage
- ***gateway-service***
    - cet utilitaire route les requêtes http dans les deux sens entre le frontend et la backend

### Les api exposeés par les microservices métiers
Pour accéder au microservices métiers backend on passe par la gateway : ```http://localhost:8101```
- ***business-microservice-customer***
    
    - [POST], [PUT]: ```http://localhost:8101/api-customer/customers```
    
        payload:  
        ```{
            "customerDto":{
                "firstname":"string value",
                "lastname":"string value",
                "state":"string value"
            },
            "addressDto":{
                "streetNum":num value,
                "streetName":"string value",
                "poBox":num value,
                "city":"string value",
                "country":"string value"
            }
        }
        ```
   - [GET] : ```http://localhost:8101/api-customer/customers```  
   - [GET] : ```http://localhost:8101/api-customer/addresses```

## Frontend
Le frontend est une application en Angular (V16) utilisant le pattern observeur de RxJs
