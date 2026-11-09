# Atelier 4 — vérification et captures

Lancer l’application puis ouvrir `http://localhost:8081/autoloc/swagger-ui/index.html`.
Importer `postman/AutoLoc.postman_collection.json` dans Postman et exécuter les
requêtes dans l’ordre (création, consultation, modification, affectation,
désaffectation, suppression). Réaliser des captures de Swagger (liste des
endpoints et schémas) et de Postman pour les réponses 2xx, puis des réponses
404 (identifiant inexistant) et 400 (JSON invalide ou double affectation).

La dépendance `springdoc-openapi-starter-webmvc-ui` reste en version **3.0.0** :
elle est alignée sur Spring Boot 4.1.1 présent dans le build. La version 2.1.0
viserait Spring Boot 3 et n'est donc pas retenue.

## Scénarios de démonstration

Dans la collection, renseigner les identifiants réels dans les variables
`agenceTunisId`, `agenceSfaxId`, `employeId`, `vehiculeNeufId` et
`equipementSiegeBebeId`. Exécuter ensuite dans cet ordre : désaffecter
l'employé, l'affecter à Tunis, rattacher le véhicule neuf à Sfax, installer le
siège bébé puis le retirer.

## Captures à réaliser

- Swagger UI : capture de la page d'accueil, de chaque groupe `Clients`,
  `Agences`, `Employés` et `Véhicules`, puis de l'essai d'un endpoint GET,
  POST, PUT, DELETE et des cinq affectations.
- Postman : capture de chaque réponse CRUD (200/201/204), du scénario
  désaffectation puis affectation à Tunis, du rattachement à Sfax, de
  l'installation/retrait du siège bébé, ainsi que d'un GET 404 et d'une double
  affectation employé en 400.
