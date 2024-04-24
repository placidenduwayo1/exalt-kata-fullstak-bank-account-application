# 💰 **Bank Account** 💰

## Description

- **Bank Account** est une application orientée microservices avec des microservices métiers et des microservices utilisataires
- Les microservices métiers: ***customer***, ***account*** et ***operation***
    - chaque microservice métier est implementé dans une ***achitecture hexagonale***
    - les microcroservices métiers ***customer*** et ***account*** communiquent entre eux
    - les microcroservices métiers ***account*** et ***operation*** communiquent aussi entre eux
    - chaque microservice métier possède sa propre base de données et peut évoluer en environnement différent des autres microservices
- Les microservices utilitaires: ***config-server*** et ***gateway-service***
    - config-server: pour externaliser et distribuer les configurations aux autres microservices
    - gateway-service: pour router dans les deux sens les requêtes entre le front et le back
- Le frontend est une ***application en Angular***

## Modélisation conceptuelle
![modeling](./assets/exalt-bank-account-conception.png)

## Architecure de l'application orientée microservice
![application-archi](./assets/exalt-bank-account-archi.png)

L'application orientée microservice **Bank Account** est dimensionnée comme suit:

## Backend
- 3 microservices métiers (business microservices)
- chaque microservice métier mappe une base données ***PostgreSQL*** déployée dans  un ***docker container***
    - le fichier ***postgresql.yml*** sert de lancer le container docker de PostgreSQL: ```docker compose -f ./postgresql.yml up -d```
- 2 microservices utilitaires (utils microservices)

### Microservices métiers

- ***business-microservice-customer***
    - *backend/business-micorservices/business-microservice-customer*
- ***business-microservice-account***
    - *backend/business-micorservices/business-microservice-account*
- ***business-microservice-operation***
    - *backend/business-micorservices/business-microservice-operation*

### Microservices utilitaires

- ***microservices-config-server***: *backend/utils-microservices/microservices-configuration-server*
    - cet utilitaire récupère les configurations dans le dossier ***configurations-center*** et les distribuent aux autres microservices à leur démarrage
    - voir la configuration ***application-dev.properties*** du microservice 
- ***gateway-service***: *backend/utils-microservices/gateway-service*
    - cet utilitaire route les requêtes http dans les deux sens entre le frontend et la backend
    - voir la configuration ***bootstrap-dev.yml*** du microservice 
    - au déploiement dans une image docker, on va utilise ***bootstrap-integ.yml***

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
Le frontend est une ***application en Angular*** (V16) utilisant le pattern ***observeur de RxJs***
