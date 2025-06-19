<template>
  <nav class="navbar">
    <router-link to="/">Departments</router-link>
    <router-link to="/search">Search</router-link>
    <router-link to="/employee">New Employee</router-link>
  </nav>
  <router-view />
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: center;
  gap: 40px;
  padding: 20px 0;
  background-color: #f9f9f9;
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 1000;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
}

.navbar a {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  text-decoration: none;
  position: relative;
  padding: 6px 4px;
  transition: color 0.2s ease;
}

.navbar a::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: -2px;
  height: 2px;
  width: 0;
  background-color: #333;
  transition: width 0.3s ease;
}

.navbar a:hover::after {
  width: 100%;
}

.navbar a.router-link-exact-active {
  color: #000;
  font-weight: 600;
}

.navbar a.router-link-exact-active::after {
  width: 100%;
}

@media (max-width: 600px) {
  .navbar {
    flex-direction: column;
    gap: 15px;
    padding: 15px;
  }
}
</style>
