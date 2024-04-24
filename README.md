# 💰 **Bank Account** 💰

## Description

- **Bank Account** est une **application orientée microservices** avec des ***microservices métiers*** et des ***microservices utilisataires***
- Les microservices métiers: ***customer***, ***account*** et ***operation***
    - chaque microservice métier est implementé dans une ***achitecture hexagonale***
    - les microcroservices (***customer*** , ***account***) communiquent: c-à-d un **account** a besoin des données d'un **customer** pour être géré
    - les microcroservices (***account*** , ***operation***) communiquent: c-à-d une **operation** a besoin des données d'un **account** pour être géré
    - chaque microservice métier possède ses propres ressources (**db**,**dépendances**, **configurations**, ..), il peut évoluer dans son propre env 
- Les microservices utilitaires: ***config-server*** et ***gateway-service***
    - *config-server*: pour externaliser et distribuer les configurations aux autres microservices
    - *gateway-service*: pour router dans les deux sens les requêtes entre le front et le back
- Le frontend est une ***application en Angular***

## Architecure
![application-archi](./assets/exalt-bank-account-archi.png)

## Conceptual model
![modeling](./assets/exalt-bank-account-conception.png)

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
    - au démarrage, les microservices demandent leur configurations au serveur **microservices-config-server**
    - le serveur de configuration récupère les config depuis le git repo dans le dossier ***configurations-center*** et les distribuent aux microservices
- ***gateway-service***: *backend/utils-microservices/gateway-service*
    - le service gateway route les requêtes http dans les deux sens entre le frontend et la backend
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

## Deploiement en containers docker
- Nous utilisons actuellement l'environnement *dev*: **application-dev.yml**, **bootstrap-dev.yml**
- Après nous déployons tous les microservices de **Bank Account** dans des containers docker:
    - Nous allons utiliser l'environement *integ*: **application-integ.yml**, **bootstrap-integ.yml**
- voir les fichiers de configurations de chaque microservice


## Frontend
Le frontend est une ***application en Angular*** (V16) utilisant le pattern ***observeur de RxJs***
