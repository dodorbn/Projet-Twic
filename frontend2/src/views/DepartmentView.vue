<template>
  <div class="departments">
    <h2>Department</h2>
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
            <th>Employee No</th>
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
            <th>Employee No</th>
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
        <button :disabled="currentPage === 0" @click="loadEmployeesPage(currentPage - 1)">Précédent</button>
        <button @click="loadEmployeesPage(currentPage + 1)">Suivant</button>
      </div>

    </div>
  </div>
</template>

<script>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'DepartmentPage',
  setup() {
    const router = useRouter()
    const departments = ref([])
    const selectedDepartment = ref(null)
    const currentManagers = ref([]) // Correction : Déclaration de currentManagers
    const currentEmployees = ref([])

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
        currentManagers.value = [] // Réinitialisation des managers
        currentEmployees.value = []
        return
      }

      try {
        // Chargement des managers
        const managersResponse = await axios.get(
            `http://localhost:8080/api/v1/departments/${dept.deptNo}/managers`
        )
        currentManagers.value = managersResponse.data

        // Chargement des employés actuels
        const employeesResponse = await axios.get(
            `http://localhost:8080/api/v1/departments/${dept.deptNo}/employees/current`
        )
        currentEmployees.value = employeesResponse.data
      } catch (error) {
        console.error('Erreur lors du chargement des détails:', error)
      }
    }

    watch(selectedDepartment, (newDept) => {
      if (newDept) {
        loadDepartmentDetails(newDept); // Utilisation ici
      }
    });

    // Navigation vers la page de l'employé
    const goToEmployee = (empNo) => {
      router.push(`/employees/${empNo}`)
    }

    const currentPage = ref(0); // Page actuelle
    const pageSize = ref(20); // Nombre d'employés par page

    const loadEmployeesPage = async (page) => {
      if (!selectedDepartment.value) {
        console.error("Aucun département sélectionné.");
        return;
      }

      if (page < 0) {
        console.warn("Page invalide : la page ne peut pas être négative.");
        return;
      }
      console.log(`Chargement de la page ${page} pour le département ${selectedDepartment.value.deptNo}`);

      try {
        const response = await axios.get(
            `http://localhost:8080/api/v1/departments/${selectedDepartment.value.deptNo}/employees/current`,
            { params: { page, size: pageSize.value } }
        );
        currentEmployees.value = response.data; // Met à jour les employés affichés
        currentPage.value = page; // Met à jour la page actuelle
      } catch (error) {
        console.error("Erreur lors du chargement des employés paginés :", error);
      }
    };

    // Surveillance des changements de département sélectionné
    watch(selectedDepartment, (newDept) => {
      if (newDept) {
        loadEmployeesPage(0); // Charge la première page
      }
    });

    // Chargement initial
    onMounted(() => {
      fetchDepartments()
    })

    return {
      departments,
      selectedDepartment,
      sortedDepartments,
      currentManagers, // Correction : Ajout de currentManagers dans le retour
      currentEmployees,
      goToEmployee,
      loadEmployeesPage,
      currentPage,
      pageSize,
    }
  }
}
</script>

<style scoped>
.department-page {
  padding: 20px;
}

.pagination {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.pagination button {
  padding: 10px 15px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  transition: background-color 0.5s;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.4;
}

.department-select {
  width: 300px;
  margin-bottom: 20px;
  padding: 8px;
}

.tables-container {
  display: flex;
  gap: 20px;
  flex-direction: column;
}

table {
  width: 90%;
  border-collapse: collapse;
  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  background-color: #fff;
  transition: box-shadow 0.3s ease;
}

th, td {
  border: 2px solid #ddd;
  padding: 8px;
  text-align: center;
}

tr:hover {
  background-color: #f5f5f5;
  cursor: pointer;
}

th {
  background-color: #f2f2f2;
  text-align: center;
}
</style>