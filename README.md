# TP11 - Projet Spring Boot avec Spring Data REST

Ce projet a été créé avec Spring Initializr et inclut les dépendances suivantes :

## Dépendances

- **Spring Data REST** : Simplifie l'exposition des repositories en tant que services RESTful
- **Spring Data JPA** : Fournit une abstraction pour l'accès aux données avec JPA
- **H2 Database** : Base de données en mémoire pour le développement
- **Lombok** : Génère automatiquement les getters, setters, constructeurs, etc.
- **DevTools** : Outils de développement pour le rechargement automatique

## Structure du projet

```
TP11/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/tp11/
│   │   │       ├── Tp11Application.java
│   │   │       ├── Compte.java
│   │   │       ├── Client.java
│   │   │       ├── TypeCompte.java
│   │   │       ├── CompteRepository.java
│   │   │       ├── ClientRepository.java
│   │   │       ├── CompteProjection.java
│   │   │       └── ClientProjection.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/tp11/
│               └── Tp11ApplicationTests.java
├── pom.xml
└── README.md
```

## Configuration

Le fichier `application.properties` contient la configuration suivante :

- **Base de données H2** : Base de données en mémoire nommée `banque` accessible via `/h2-console`
- **Spring Data REST** : API REST exposée sous le chemin `/api`
- **Port du serveur** : `8082`
- **JPA** : Configuration pour créer automatiquement les tables avec `ddl-auto=update`

## Comment utiliser

1. **Compiler le projet** :
   ```bash
   mvn clean install
   ```

2. **Lancer l'application** :
   ```bash
   mvn spring-boot:run
   ```

3. **Accéder à la console H2** :
   - URL : http://localhost:8082/h2-console
   - JDBC URL : `jdbc:h2:mem:banque`
   - Username : `sa`
   - Password : (laisser vide)

4. **Accéder à l'API REST** :
   - Base path : http://localhost:8082/api

## Entités

### Compte
- `id` : Identifiant unique (Long)
- `solde` : Solde du compte (double)
- `dateCreation` : Date de création (Date)
- `type` : Type de compte (TypeCompte : COURANT ou EPARGNE)
- `client` : Client propriétaire du compte (relation ManyToOne)

### Client
- `id` : Identifiant unique (Long)
- `nom` : Nom du client (String)
- `email` : Email du client (String)
- `comptes` : Liste des comptes du client (relation OneToMany)

## Endpoints REST disponibles

### Comptes

#### Liste tous les comptes
- **GET** `http://localhost:8082/api/comptes`
- **Réponse** : Liste paginée de tous les comptes avec liens HATEOAS

#### Récupérer un compte par ID
- **GET** `http://localhost:8082/api/comptes/{id}`
- **Exemple** : `http://localhost:8082/api/comptes/1`
- **Réponse** : Détails complets du compte avec liens vers le client associé

#### Recherche par type de compte
- **GET** `http://localhost:8082/api/comptes/search/byType?t={TYPE}`
- **Exemples** :
  - `http://localhost:8082/api/comptes/search/byType?t=EPARGNE` - Tous les comptes épargne
  - `http://localhost:8082/api/comptes/search/byType?t=COURANT` - Tous les comptes courants
- **Réponse** : Liste des comptes filtrés par type

#### Créer un compte
- **POST** `http://localhost:8082/api/comptes`
- **Body** (JSON) :
  ```json
  {
    "solde": 5000.0,
    "dateCreation": "2024-12-10",
    "type": "EPARGNE",
    "client": "http://localhost:8082/api/clients/1"
  }
  ```

#### Mettre à jour un compte
- **PUT** `http://localhost:8082/api/comptes/{id}`
- **Body** (JSON) : Objet Compte complet

#### Supprimer un compte
- **DELETE** `http://localhost:8082/api/comptes/{id}`

### Clients

#### Liste tous les clients
- **GET** `http://localhost:8082/api/clients`

#### Récupérer un client par ID
- **GET** `http://localhost:8082/api/clients/{id}`

#### Récupérer les comptes d'un client
- **GET** `http://localhost:8082/api/clients/{id}/comptes`

#### Récupérer le client d'un compte
- **GET** `http://localhost:8082/api/comptes/{id}/client`

## Projections

Les projections permettent de personnaliser les données retournées dans les réponses JSON.

### Projection "solde" pour Compte
- **URL** : `http://localhost:8082/api/comptes/{id}?projection=solde`
- **Exemple** : `http://localhost:8082/api/comptes/1?projection=solde`
- **Réponse** : Retourne uniquement l'ID et le solde du compte
  ```json
  {
    "id": 1,
    "solde": 4907.521867096101,
    "_links": {
      "self": {
        "href": "http://localhost:8082/api/comptes/1"
      },
      "compte": {
        "href": "http://localhost:8082/api/comptes/1{?projection}",
        "templated": true
      },
      "client": {
        "href": "http://localhost:8082/api/comptes/1/client{?projection}",
        "templated": true
      }
    }
  }
  ```

### Projection "clientDetails" pour Client
- **URL** : `http://localhost:8082/api/comptes/{id}/client?projection=clientDetails`
- **Exemple** : `http://localhost:8082/api/comptes/1/client?projection=clientDetails`
- **Réponse** : Retourne uniquement le nom et l'email du client
  ```json
  {
    "nom": "Amal",
    "email": null,
    "_links": {
      "self": {
        "href": "http://localhost:8082/api/clients/1"
      },
      "client": {
        "href": "http://localhost:8082/api/clients/1{?projection}",
        "templated": true
      },
      "comptes": {
        "href": "http://localhost:8082/api/clients/1/comptes{?projection}",
        "templated": true
      }
    }
  }
  ```

## Exemples d'utilisation avec Postman

### 1. Recherche de comptes par type EPARGNE

**Requête** : `GET http://localhost:8082/api/comptes/search/byType?t=EPARGNE`

**Réponse** (200 OK) :
```json
{
  "_embedded": {
    "comptes": [
      {
        "id": 1,
        "solde": 4907.521867096101,
        "dateCreation": "2025-12-10",
        "type": "EPARGNE",
        "_links": {
          "self": {
            "href": "http://localhost:8082/api/comptes/1"
          },
          "compte": {
            "href": "http://localhost:8082/api/comptes/1{?projection}",
            "templated": true
          },
          "client": {
            "href": "http://localhost:8082/api/comptes/1/client{?projection}",
            "templated": true
          }
        }
      },
      {
        "id": 3,
        "solde": 5520.365349347941,
        "dateCreation": "2025-12-10",
        "type": "EPARGNE",
        "_links": { ... }
      }
    ]
  }
}
```

### 2. Recherche de comptes par type COURANT

**Requête** : `GET http://localhost:8082/api/comptes/search/byType?t=COURANT`

**Réponse** (200 OK) :
```json
{
  "_embedded": {
    "comptes": [
      {
        "id": 2,
        "solde": 4707.714626871024,
        "dateCreation": "2025-12-10",
        "type": "COURANT",
        "_links": {
          "self": {
            "href": "http://localhost:8082/api/comptes/2"
          },
          "compte": {
            "href": "http://localhost:8082/api/comptes/2{?projection}",
            "templated": true
          },
          "client": {
            "href": "http://localhost:8082/api/comptes/2/client{?projection}",
            "templated": true
          }
        }
      }
    ]
  }
}
```

### 3. Utilisation de la projection "solde"

**Requête** : `GET http://localhost:8082/api/comptes/1?projection=solde`

**Headers requis** :
- `Accept: application/hal+json` ou `Accept: application/json`

**Réponse** (200 OK) :
```json
{
  "id": 1,
  "solde": 4907.521867096101,
  "_links": {
    "self": {
      "href": "http://localhost:8082/api/comptes/1"
    },
    "compte": {
      "href": "http://localhost:8082/api/comptes/1{?projection}",
      "templated": true
    },
    "client": {
      "href": "http://localhost:8082/api/comptes/1/client{?projection}",
      "templated": true
    }
  }
}
```

### 4. Récupération du client avec projection

**Requête** : `GET http://localhost:8082/api/comptes/1/client?projection=clientDetails`

**Réponse** (200 OK) :
```json
{
  "nom": "Amal",
  "email": null,
  "_links": {
    "self": {
      "href": "http://localhost:8082/api/clients/1"
    },
    "client": {
      "href": "http://localhost:8082/api/clients/1{?projection}",
      "templated": true
    },
    "comptes": {
      "href": "http://localhost:8082/api/clients/1/comptes{?projection}",
      "templated": true
    }
  }
}
```

## Configuration Postman

Pour utiliser l'API avec Postman, configurez les headers suivants :

- **Accept** : `application/hal+json` ou `application/json`
- **Content-Type** : `application/json` (pour les requêtes POST/PUT)

**Note importante** : Si vous obtenez une erreur `406 Not Acceptable`, assurez-vous que le header `Accept` est configuré avec `application/hal+json` ou `application/json`.

## Pagination et Tri

### Pagination
- **URL** : `http://localhost:8082/api/comptes?page=0&size=2`
- **Paramètres** :
  - `page` : Numéro de page (commence à 0)
  - `size` : Nombre d'éléments par page

### Tri
- **URL** : `http://localhost:8082/api/comptes?sort=solde,desc`
- **Paramètres** :
  - `sort` : Champ de tri et direction (asc/desc)
  - Exemples : `sort=solde,desc`, `sort=dateCreation,asc`, `sort=type,asc&sort=solde,desc`

## Données initialisées

Au démarrage de l'application, les données suivantes sont automatiquement créées :

- **Client 1** : Amal
- **Client 2** : Ali
- **Compte 1** : Type EPARGNE, associé à Amal
- **Compte 2** : Type COURANT, associé à Amal
- **Compte 3** : Type EPARGNE, associé à Ali

## Remarque

Spring Data REST simplifie considérablement l'exposition des repositories en tant que services RESTful, réduisant le besoin de créer manuellement des contrôleurs pour les opérations CRUD de base. Les relations entre entités sont automatiquement exposées via les liens HATEOAS, permettant une navigation facile entre les ressources.
