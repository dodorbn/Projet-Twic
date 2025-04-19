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
      <div v-if="results.length === 0" class="no-results">
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
            v-for="employee in results"
            :key="employee.id"
            @click="goToEmployee(employee.id)"
            style="cursor: pointer"
          >
            <td>{{ employee.id }}</td>
            <td>{{ employee.first_name }}</td>
            <td>{{ employee.last_name }}</td>
            <td>{{ employee.DeptName }}</td>
            <td>{{ employee.title }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { searchEmployees } from '@/services/api';

const router = useRouter();
const searchQuery = ref('');
const results = ref([]);
const searchDone = ref(false);

const search = async () => {
  if (!searchQuery.value.trim()) return;

  try {
    const data = await searchEmployees(searchQuery.value);
    results.value = data;
    searchDone.value = true;
  } catch (error) {
    console.error('Erreur de recherche:', error);
    results.value = [];
    searchDone.value = true;
  }
};

const goToEmployee = (id) => {
  router.push(`/employee/${id}`);
};
</script>

<style scoped>
.search {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
}

.search-form input {
  padding: 8px;
  margin-right: 10px;
  width: 300px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

tr:hover {
  background-color: #f5f5f5;
}

.no-results {
  padding: 20px;
  text-align: center;
  color: #666;
}
</style>