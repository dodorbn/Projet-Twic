# Projet TP TWIC

## Directives

### 1. Serveur

Créer un serveur API REST en Java.

Ce serveur doit se connecter à la base de données *employees* fournie.

Le serveur doit exposer les opérations CRUD pour les ressources `employee`, `salary` et `title`.

À noter que les ressources `salary` et `title` sont dépendantes de la ressource `employee` auquelle elles sont attachées.
Ça se traduit par une arborescence des URLs du format `/employees/{empNo}/salaries/{fromDate}`.

Astuce : penser à exposer le salaire actuel à l'URL `/employees/{empNo}/salaries/last`. Pareil pour le titre.

À cause du grand volume de données, utilisez de la pagination.

Le serveur doit aussi appeler un API public tiers pour récupérer une donnée (service météo, cotation boursière, etc.) et exposer cette donnée à un endpoint.

### 2. Client

Créer un client du serveur ci-dessus.

Le client doit exposer un site web avec 3 pages ou une page avec 3 onglets.

#### 2.1 Page des départements

La première page (ou onglet) présente une liste déroulante avec tous les départements.
Le libellé affiché est du format `{dept_no} - {dept_name}`.
La liste est ordonnée en mode croissant en fonction du libellé ci-dessus.

Lorsque l'utilisateur sélectionne une valeur dans la liste, deux tableaux s'affichent.
Les tableaux contiennent les colonnes suivantes : `Employee No`, `First Name`, `Last Name`.

Le premier tableau est intitulé `Manager` et contient une seule ligne valorisée avec le manager actuel du département sélectionné dans la liste déroulante.

Le deuxième tableau est intitulé `Employees` et contient des lignes valorisées avec la liste des employés actuels du département.

Lorsque l'utilisateur clique sur une ligne d'un tableau, il est redirigé vers la page de l'employé (page 3) avec les valeurs de l'employé pré-remplis.

Cette première page doit aussi afficher la donnée récupérée par le serveur à partir de l'API publique.

#### 2.2 Page de recherche

La deuxième page (ou onglet) présente un champ de saisi texte avec un bouton intitulé `Search`.

Lorsque l'utilisateur remplis le champ et appuie sur le bouton, un tableau s'affiche avec des résultats.
Le tableau est intitulé `Results` et contient les colonnes suivantes : `Employee No`, `First Name`, `Last Name`.
Il contient des lignes valorisées avec les employés trouvés lors de la recherche.

Le mécanisme de recherche : la valeur saisie dans le champ doit se trouver dans l'un des champs `emp_no`, `first_name` ou `last_name` de la ressource `employee`.

Si aucun résultat n'est trouvé, le message `No results found.` est affiché à la place du tableau.

Lorsque l'utilisateur clique sur une ligne du tableau, il est redirigé vers la page de l'employé (page 3) avec les valeurs de l'employé pré-remplis.

#### 2.3 Page de l'employé

La troisième page (ou onglet) présente plusieurs libellés chacun avec un mode de saisie.

Les libellés et leurs modes de saisie associés sont :

| Libellé     | Mode de saisie   |
|-------------|------------------|
| Employee No | champs numéro    |
| First Name  | champs texte     |
| Last Name   | champs texte     |
| Department  | liste déroulante |
| Birth Date  | champs date      |
| Hire Date   | champs date      |
| Title       | champs texte     |
| Salary      | champs numéro    |

Lorsque les données sont pré-remplis suite à une action sur une des autres pages, les valeurs `Title` et `Salary`correspondent aux ressources actuelles associées à l'employé.

La page (ou onglet) présente aussi 3 boutons intitulés `Create`, `Modify`, `Delete`.

Lorsque l'utilisateur appuie sur un bouton, l'action correspondante est executée.

### 3. Annexe

Une chaîne d'intégration continue avec Jenkins doit accompagnier le projet.

La définition de la chaîne d'intégration continue doit être faite dans un fichier `Jenkinsfile` à la racine du projet.

Une analyse de qualimétrie avec SonarQube doit être faite lors de cette intégration.

## À rendre

- code source du projet serveur
- code source du projet client
- fichier Jenkinsfile pour la chaîne d'intégration continue
- fichier OpenAPI (en .json ou .yaml) décrivant l'API REST du serveur
- fichier de log de l'exécution Jenkins
- rapport de qualimétrie SonarQube (export ou capture d'écran)
- petite touche personnelle (avec documentation)

Tous les items sont à fournir sur GitHub.
