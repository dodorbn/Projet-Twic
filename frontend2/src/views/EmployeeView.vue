<template>
  <div class="employee">
    <h2>{{ isNew ? 'New employee' : 'Employee Datas' }}</h2>

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
          <option value="">Select a department</option>
          <option v-for="dept in sortedDepartments" :key="dept.deptNo" :value="dept.deptNo">
            {{ dept.deptName }}
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
import axios from 'axios'
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'EmployeeView',
  setup() {
    const router = useRouter()
    const employee = ref({
      id: '',
      first_name: '',
      last_name: '',
      DeptNo: '',
      birth_date: '',
      hire_date: '',
      title: '',
      salary: ''
    })
    const departments = ref([])
    const isNew = ref(true)

    const sortedDepartments = computed(() => {
      return [...departments.value].sort((a, b) => a.deptName.localeCompare(b.deptName))
    })

    const fetchDepartments = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/departments')
        departments.value = response.data
      } catch (error) {
        console.error('Erreur lors du chargement des départements :', error)
      }
    }

    const loadEmployee = async (id) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/employees/${id}`)
        const data = response.data
        employee.value = {
          id: data.id,
          first_name: data.firstName,
          last_name: data.lastName,
          DeptNo: data.department || '',
          birth_date: data.birthDate,
          hire_date: data.hireDate,
          title: data.title || '',
          salary: data.salary || ''
        }
        console.log(data)
      } catch (error) {
        console.error('Erreur lors du chargement de l\'employé :', error)
      }
    }

    const handleSubmit = async () => {
      try {
        if (isNew.value) {
          await axios.post('http://localhost:8080/api/v1/employees', employee.value)
        } else {
          await axios.put(`http://localhost:8080/api/v1/employees/${employee.value.id}`, employee.value)
        }
        router.push('/')
      } catch (error) {
        console.error(error)
      }
    }

    const deleteEmployee = async () => {
      if (confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')) {
        try {
          await axios.delete(`http://localhost:8080/api/v1/employees/${employee.value.id}`)
          router.push('/')
        } catch (error) {
          console.error(error)
        }
      }
    }

    onMounted(async () => {
      await fetchDepartments();
      const id = router.currentRoute.value.params.id; // Récupère l'ID depuis l'URL
      if (id) {
        isNew.value = false;
        await loadEmployee(id); // Charge les données de l'employé
      }
    })

    return {
      employee,
      departments,
      sortedDepartments,
      isNew,
      fetchDepartments,
      handleSubmit,
      deleteEmployee
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