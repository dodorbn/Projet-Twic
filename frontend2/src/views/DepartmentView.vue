<template>
  <div class="departments">
    <h2>Départements</h2>
    <select v-model="selectedDept" @change="loadEmployees">
      <option value="">Sélectionnez un département</option>
      <option v-for="dept in departments" :key="dept.deptNo" :value="dept.deptNo">
        {{ dept.deptNo }} - {{ dept.deptName }}
      </option>
    </select>

    <div v-if="selectedDept">
      <h3>Manager</h3>
      <table v-if="manager">
        <thead>
          <tr>
            <th>Employee No</th>
            <th>First Name</th>
            <th>Last Name</th>
          </tr>
        </thead>
        <tbody>
          <tr @click="goToEmployee(manager.id)">
            <td>{{ manager.id }}</td>
            <td>{{ manager.first_name }}</td>
            <td>{{ manager.last_name }}</td>
          </tr>
        </tbody>
      </table>

      <h3>Employés</h3>
      <table v-if="employees.length">
        <thead>
          <tr>
            <th>Employee No</th>
            <th>First Name</th>
            <th>Last Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="emp in employees" :key="emp.id" @click="goToEmployee(emp.id)">
            <td>{{ emp.id }}</td>
            <td>{{ emp.first_name }}</td>
            <td>{{ emp.last_name }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import api from '@/services/api'

export default {
  name: 'DepartmentView',
  data() {
    return {
      departments: [],
      selectedDept: '',
      manager: null,
      employees: []
    }
  },
  async created() {
    try {
      const response = await api.get('/api/v1/departments')
      this.departments = response.data
    } catch (error) {
      console.error(error)
    }
  },
  methods: {
    async loadEmployees() {
      if (!this.selectedDept) return

      try {
        const [managerResponse, employeesResponse] = await Promise.all([
          api.get(`/api/v1/departments/${this.selectedDept}/manager`),
          api.get(`/api/v1/departments/${this.selectedDept}/employees`)
        ])

        this.manager = managerResponse.data
        this.employees = employeesResponse.data
      } catch (error) {
        console.error(error)
      }
    },
    goToEmployee(id) {
      this.$router.push(`/employee/${id}`)
    }
  }
}
</script>