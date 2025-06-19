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
        const payload = {
          id: employee.value.id,
          firstName: employee.value.first_name,
          lastName: employee.value.last_name,
          department: employee.value.DeptNo,
          birthDate: employee.value.birth_date,
          hireDate: employee.value.hire_date,
          title: employee.value.title,
          salary: employee.value.salary
        };
        if (isNew.value) {
          await axios.post('http://localhost:8080/api/v1/employees', payload)
        } else {
          await axios.put(`http://localhost:8080/api/v1/employees/${employee.value.id}`, payload)
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
.employee {
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(60, 60, 60, 0.06);
  padding: 28px 20px;
  margin: 32px auto;
  max-width: 600px;
  border: 1px solid #e6e8ec;
}

h2 {
  color: #222;
  margin-bottom: 18px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-align: center;
}

.employee-form {
  max-width: 500px;
  margin: 0 auto;
  padding: 0;
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 7px;
  font-weight: 600;
  color: #222;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 9px 12px;
  border: 1px solid #d1d5db;
  border-radius: 7px;
  background: #f9fafb;
  font-size: 1rem;
  transition: border 0.2s;
}

.form-group input:focus,
.form-group select:focus {
  border: 1.5px solid #7bb7fa;
  outline: none;
  background: #fff;
}

.button-group {
  margin-top: 22px;
  display: flex;
  gap: 14px;
  justify-content: center;
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

.create {
  /* même style que les autres boutons pour l'harmonie */
}

.modify {
  /* même style que les autres boutons pour l'harmonie */
}

.delete {
  background: #fff0f0;
  color: #d32f2f;
  border: 1px solid #f44336;
}

.delete:hover:not(:disabled) {
  background: #fbe9e9;
  border: 1.5px solid #d32f2f;
  color: #b71c1c;
}
</style>