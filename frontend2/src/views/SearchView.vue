<template>
  <div class="search">
    <h1>Search</h1>

    <div class="search-form">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Search by name, surname or id..."
        @keyup.enter="search"
      />
      <button @click="search">Search</button>
    </div>

    <div v-if="searchDone">
      <div v-if="!results?.content || results.content.length === 0" class="no-results">
        No results found.
      </div>

      <table v-else>
        <thead>
          <tr>
            <th>Employee N°</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
            <th>Position</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="employee in results.content"
            :key="employee.id"
            @click="goToEmployee(employee.id)"
            style="cursor: pointer"
          >
            <td>{{ employee.id }}</td>
            <td>{{ employee.firstName }}</td>
            <td>{{ employee.lastName }}</td>
            <td>{{ getDepartmentName(employee.department) }}</td>
            <td>{{ employee.title }}</td>
          </tr>
        </tbody>
      </table>

      <!-- Pagination pour les recherches -->
      <div class="pagination">
        <button :disabled="currentPage === 0" @click="loadSearchPage(currentPage - 1)">Previous</button>
        <span>Page {{ currentPage + 1 }} of {{ Math.ceil(allResults.length / pageSize) }}</span>
        <button :disabled="!hasNextPage" @click="loadSearchPage(currentPage + 1)">Next</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { searchEmployees } from '@/services/api';
import axios from 'axios';

const router = useRouter();
const searchQuery = ref('');
const results = ref([]);
const searchDone = ref(false);
const departments = ref([]);
const pageSize = ref(20); // Limit results per page
const currentPage = ref(0);
const allResults = ref([]); // Store all results

const displayedResults = computed(() => {
  const start = currentPage.value * pageSize.value;
  const end = start + pageSize.value;
  return {
    content: allResults.value.slice(start, end),
    hasNext: allResults.value.length > end
  };
});

const hasNextPage = computed(() => {
  return (currentPage.value + 1) * pageSize.value < allResults.value.length;
});

const fetchDepartments = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/departments');
    departments.value = response.data;
  } catch (error) {
    console.error('Erreur lors du chargement des départements :', error);
  }
};

const getDepartmentName = (deptId) => {
  const dept = departments.value.find(d => d.deptNo === deptId);
  return dept ? dept.deptName : deptId;
};

const search = async () => {
  if (!searchQuery.value.trim()) return;
  currentPage.value = 0;

  try {
    const data = await searchEmployees(searchQuery.value);
    console.log('Search results:', data);
    
    // Store all results
    allResults.value = Array.isArray(data) ? data : (data.content || []);
    
    // Update results with paginated view
    results.value = displayedResults.value;
    searchDone.value = true;
  } catch (error) {
    console.error('Search error:', error);
    allResults.value = [];
    results.value = { content: [] };
    searchDone.value = true;
  }
};

const goToEmployee = (id) => {
  router.push(`/employee/${id}`);
};

const loadSearchPage = async (page) => {
  if (page < 0 || page * pageSize.value >= allResults.value.length) return;
  
  currentPage.value = page;
  results.value = displayedResults.value;
};

onMounted(fetchDepartments);
</script>

<style scoped>
.search {
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(60, 60, 60, 0.06);
  padding: 28px 20px;
  margin: 32px auto;
  max-width: 900px;
  border: 1px solid #e6e8ec;
}

h1 {
  color: #222;
  margin-bottom: 16px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.search-form {
  margin-bottom: 24px;
  display: flex;
  gap: 12px;
}

.search-form input {
  padding: 9px 12px;
  border: 1px solid #d1d5db;
  border-radius: 7px;
  background: #f9fafb;
  width: 320px;
  transition: border 0.2s;
}

.search-form input:focus {
  border: 1.5px solid #7bb7fa;
  outline: none;
  background: #fff;
}

.search-form button {
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

.search-form button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.search-form button:hover:not(:disabled) {
  background: #e6f0fa;
  border: 1.5px solid #7bb7fa;
  color: #1976d2;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 18px;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
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

tr:hover {
  background: #f1f7fd;
  cursor: pointer;
  transition: background 0.13s;
}

.no-results {
  padding: 24px;
  text-align: center;
  color: #666;
  font-size: 1.1rem;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 14px;
  gap: 14px;
  width: 100%;
}

.pagination button {
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

.pagination button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background: #e6f0fa;
  border: 1.5px solid #7bb7fa;
  color: #1976d2;
}
</style>