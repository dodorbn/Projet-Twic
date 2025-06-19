<template>
  <div class="pokemon">
    <h2>Search a Pokemon</h2>
    <input v-model="pokemonSearch" placeholder="Enter a name (ex: pikachu)" />
    <button @click="fetchSearchedPokemon">Search</button>

    <div v-if="searchedPokemon" class="pokemon-result">
      <img :src="searchedPokemon.image" :alt="searchedPokemon.name" />
      <p><strong>{{ searchedPokemon.name }}</strong> (Type: {{ searchedPokemon.types.join(' / ') }})</p>
    </div>

    <div v-else-if="pokemonSearch">
      <p>Pokemon unfined</p>
    </div>
  </div>

  <div class="departments">
    <h2>Select a department</h2>
    <!-- Liste déroulante des départements -->
    <select v-model="selectedDepartment" class="department-select">
      <option value="">Select a department</option>
      <option v-for="dept in sortedDepartments" :key="dept.deptNo" :value="dept">
        {{ dept.deptNo }} - {{ dept.deptName }}
      </option>
    </select>

    <!-- Affichage des tableaux uniquement si un département est sélectionné -->
    <div v-if="selectedDepartment" class="tables-container">
      <!-- Tableau du Manager -->
      <div class="manager-table">
        <h3>Manager</h3>
        <table>
          <thead>
          <tr>
            <th>Employee N°</th>
            <th>First Name</th>
            <th>Last Name</th>
          </tr>
          </thead>
            <tbody>
              <tr v-for="manager in currentManagers" :key="manager.id" @click="goToEmployee(manager.id)">
                <td>{{ manager.id }}</td>
                <td>{{ manager.firstName }}</td>
                <td>{{ manager.lastName }}</td>
              </tr>
            </tbody>
        </table>
      </div>

      <!-- Tableau des Employés -->
      <div class="employees-table">
        <h3>Employees</h3>
        <table>
          <thead>
          <tr>
            <th>Employee N°</th>
            <th>First Name</th>
            <th>Last Name</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="employee in currentEmployees"
              :key="employee.id"
              @click="goToEmployee(employee.id)">
            <td>{{ employee.id}}</td>
            <td>{{ employee.firstName }}</td>
            <td>{{ employee.lastName }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination pour les employés -->
      <div class="pagination">
        <button :disabled="currentPage === 0" @click="loadEmployeesPage(currentPage - 1)">Previous</button>
        <span>Page {{ currentPage + 1 }}</span>
        <button @click="loadEmployeesPage(currentPage + 1)">Next</button>
      </div>

    </div>
  </div>
</template>

<script setup>
  import { ref, computed, watch, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import axios from 'axios'

  const router = useRouter()
  const departments = ref([])
  const selectedDepartment = ref(null)
  const currentPage = ref(0)
  const pageSize = ref(20)
  const currentEmployees = ref([])
  const currentManagers = ref([])
  const totalEmployees = ref(0)


  // Tri des départements
  const sortedDepartments = computed(() => {
    return [...departments.value].sort((a, b) => {
      const labelA = `${a.deptNo} - ${a.deptName}`
      const labelB = `${b.deptNo} - ${b.deptName}`
      return labelA.localeCompare(labelB)
  })
  })

  // Chargement initial des départements
  const fetchDepartments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/v1/departments')
      departments.value = response.data
    } catch (error) {
      console.error('Erreur lors du chargement des départements:', error)
    }
  }

  // Chargement des détails du département
  const loadDepartmentDetails = async (dept) => {
    if (!dept) {
      currentManagers.value = []
      currentEmployees.value = []
      totalEmployees.value = 0
      return
    }
    try {
      // Chargement des managers
      const managersResponse = await axios.get(
        `http://localhost:8080/api/v1/departments/${dept.deptNo}/managers`
      )
      currentManagers.value = managersResponse.data

      // Chargement des employés actuels avec le count total
      const employeesResponse = await axios.get(
        `http://localhost:8080/api/v1/departments/${dept.deptNo}/employees/current`
      )
      currentEmployees.value = employeesResponse.data
      // Supposons que l'API renvoie le total dans un header ou dans la réponse
      totalEmployees.value = parseInt(employeesResponse.headers['x-total-count']) || employeesResponse.data.length
    } catch (error) {
      console.error('Erreur lors du chargement des détails:', error)
    }
  }

  // Navigation vers la page de l'employé
  const goToEmployee = (empNo) => {
    router.push(`/employee/${empNo}`)
  }

  const loadEmployeesPage = async (page) => {
    if (!selectedDepartment.value) return;
    if (page < 0) return;

    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/departments/${selectedDepartment.value.deptNo}/employees/current`,
        { params: { page, size: pageSize.value } }
      );

      currentEmployees.value = response.data;
      currentPage.value = page;

      // Ici on essaye de lire le total depuis les headers, sinon fallback sur taille locale
      totalEmployees.value = parseInt(response.headers['x-total-count']) || response.data.length;

    } catch (error) {
      console.error("Erreur lors du chargement des employés paginés :", error);
    }
  };


  const pokemonSearch = ref('')
  const searchedPokemon = ref(null)

  const fetchSearchedPokemon = async () => {
    const name = pokemonSearch.value.trim().toLowerCase()
    if (!name) return

    try {
      const response = await axios.get(`https://pokeapi.co/api/v2/pokemon/${name}`)
      searchedPokemon.value = {
        name: response.data.name,
        image: response.data.sprites.front_default,
        types: response.data.types.map(t => t.type.name)
      }
    } catch (error) {
      console.error("Pokémon non trouvé :", error)
      searchedPokemon.value = null
    }
  }

  watch(selectedDepartment, (newDept) => {
    if (newDept) {
      loadDepartmentDetails(newDept);
      loadEmployeesPage(0);
    } else {
      currentManagers.value = []
      currentEmployees.value = []
    }
  })

  // Chargement initial
  onMounted(() => {
    fetchDepartments()
  })
</script>

<style scoped>
body {
  background: #f7f9fb;
  font-family: 'Segoe UI', 'Roboto', Arial, sans-serif;
}

.pokemon,
.departments {
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(60, 60, 60, 0.06);
  padding: 28px 20px;
  margin: 32px auto;
  max-width: 900px;
  border: 1px solid #e6e8ec;
}

.pokemon button{
  margin-left: 20px;
  padding: 10px 16px;
}

.pokemon input {
  width: 100%;
  max-width: 300px;
  padding: 10px;
  border: 1px solid #d1d5db;
  border-radius: 7px;
  background: #f9fafb;
  transition: border-color 0.2s;
}

.pokemon ::placeholder {
  color: #9ca3af;
  font-style: italic;

}

.pokemon-result {
  margin-top: 20px;
  text-align: center;
}

h2, h3 {
  color: #222;
  margin-bottom: 16px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

input, select, button {
  font-size: 1rem;
  font-family: inherit;
}

input[type="text"], select {
  padding: 9px 12px;
  border: 1px solid #d1d5db;
  border-radius: 7px;
  margin-right: 8px;
  margin-bottom: 10px;
  background: #f9fafb;
  transition: border 0.2s;
}

input[type="text"]:focus, select:focus {
  border: 1.5px solid #7bb7fa;
  outline: none;
  background: #fff;
}

button {
  background: #f7f9fb;
  color: #222;
  border: 1px solid #d1d5db;
  border-radius: 7px;
  padding: 9px 22px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.18s, border 0.18s, color 0.18s;
  box-shadow: none;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background: #e6f0fa;
  border: 1.5px solid #7bb7fa;
  color: #1976d2;
}

.department-select {
  width: 260px;
  margin-bottom: 18px;
}

.tables-container {
  display: flex;
  gap: 28px;
  flex-wrap: wrap;
  margin-top: 18px;
}

.manager-table, .employees-table {
  flex: 1 1 340px;
  background: #f9fafb;
  border-radius: 10px;
  padding: 14px 8px;
  box-shadow: none;
  border: 1px solid #e6e8ec;
  display: flex;
  flex-direction: column;
  width: 100%;
  min-width: 320px;
  box-sizing: border-box;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 6px;
  background: #fff;
  border-radius: 8px;
  overflow: auto;
  font-size: 1rem;
}

th, td {
  padding: 9px 6px;
  text-align: center;
  border-bottom: 1px solid #f0f0f0;
}

th {
  background: #f4f7fa;
  color: #222;
  font-weight: 600;
}

tr:last-child td {
  border-bottom: none;
}

h3 {
  margin-bottom: 20px;
  font-size: 1.2rem;
  color: #333;
  text-align: center;
}

tr:hover {
  background: #f1f7fd;
  cursor: pointer;
  transition: background 0.13s;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 14px;
  gap: 14px;
  width: 100%;
}

.pokemon-result {
  margin-top: 14px;
  text-align: center;
}

.pokemon-result img {
  width: 90px;
  height: auto;
  margin-bottom: 6px;
  border-radius: 8px;
  background: #f4f7fa;
  padding: 6px;
}

@media (max-width: 900px) {
  .tables-container {
    flex-direction: column;
    gap: 14px;
  }
}
</style>