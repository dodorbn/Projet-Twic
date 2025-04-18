<template>
  <nav>
    <router-link to="/">Départements</router-link> |
    <router-link to="/search">Recherche</router-link> |
    <router-link to="/employee">Nouvel Employé</router-link>
  </nav>
  <router-view/>
</template>

<style>
nav {
  padding: 30px;
  text-align: center;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
  margin: 0 10px;
  cursor: pointer; /* Ajout du curseur pointer */
}

nav a.router-link-exact-active {
  color: #42b983;
}

/* Style pour les séparateurs */
nav span {
  color: #2c3e50;
  margin: 0 5px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

tr:hover {
  background-color: #f5f5f5;
  cursor: pointer;
}
</style>