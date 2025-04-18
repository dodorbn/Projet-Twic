<template>
  <div class="employee">
    <h2>{{ isNew ? 'Nouvel employé' : 'Détails employé' }}</h2>

    <form @submit.prevent="handleSubmit" class="employee-form">
      <div class="form-group">
        <label>Employee No</label>
        <input type="number" v-model="employee.id" :disabled="!isNew">
      </div>

      <div class="form-group">
        <label>First Name</label>
        <input type="text" v-model="employee.first_name" required>
      </div>

      <div class="form-group">
        <label>Last Name</label>
        <input type="text" v-model="employee.last_name" required>
      </div>

      <div class="form-group">
        <label>Department</label>
        <select v-model="employee.DeptNo" required>
          <option value="">Sélectionnez un département</option>
          <option v-for="dept in departments" :key="dept.deptNo" :value="dept.deptNo">
            {{ dept.deptNo }} - {{ dept.deptName }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Birth Date</label>
        <input type="date" v-model="employee.birth_date" required>
      </div>

      <div class="form-group">
        <label>Hire Date</label>
        <input type="date" v-model="employee.hire_date" required>
      </div>

      <div class="form-group">
        <label>Title</label>
        <input type="text" v-model="employee.title" required>
      </div>

      <div class="form-group">
        <label>Salary</label>
        <input type="number" v-model="employee.salary" required>
      </div>

      <div class="button-group">
        <button type="submit" class="create" v-if="isNew">Create</button>
        <template v-else>
          <button type="submit" class="modify">Modify</button>
          <button type="button" class="delete" @click="deleteEmployee">Delete</button>
        </template>
      </div>
    </form>
  </div>
</template>

<script>
import api from '@/services/api'

export default {
  name: 'EmployeeView',
  data() {
    return {
      employee: {
        id: '',
        first_name: '',
        last_name: '',
        DeptNo: '',
        birth_date: '',
        hire_date: '',
        title: '',
        salary: ''
      },
      departments: [],
      isNew: true
    }
  },
  async created() {
    await this.loadDepartments()
    const id = this.$route.params.id
    if (id) {
      this.isNew = false
      await this.loadEmployee(id)
    }
  },
  methods: {
    async loadDepartments() {
      try {
        const response = await api.get('/api/v1/departments')
        this.departments = response.data
      } catch (error) {
        console.error(error)
      }
    },
    async loadEmployee(id) {
      try {
        const response = await api.get(`/api/v1/employees/${id}`)
        this.employee = response.data
      } catch (error) {
        console.error(error)
      }
    },
    async handleSubmit() {
      try {
        if (this.isNew) {
          await api.post('/api/v1/employees', this.employee)
        } else {
          await api.put(`/api/v1/employees/${this.employee.id}`, this.employee)
        }
        this.$router.push('/')
      } catch (error) {
        console.error(error)
      }
    },
    async deleteEmployee() {
      if (confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')) {
        try {
          await api.delete(`/api/v1/employees/${this.employee.id}`)
          this.$router.push('/')
        } catch (error) {
          console.error(error)
        }
      }
    }
  }
}
</script>

<style scoped>
.employee-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.button-group {
  margin-top: 20px;
}

button {
  padding: 8px 16px;
  margin-right: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.create {
  background-color: #4CAF50;
  color: white;
}

.modify {
  background-color: #2196F3;
  color: white;
}

.delete {
  background-color: #f44336;
  color: white;
}
</style>