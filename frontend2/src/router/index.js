import { createRouter, createWebHistory } from 'vue-router'
import DepartmentView from '@/views/DepartmentView.vue'
import SearchView from '@/views/SearchView.vue'
import EmployeeView from '@/views/EmployeeView.vue'

const routes = [
  {
    path: '/',
    name: 'departments',
    component: DepartmentView
  },
  {
    path: '/search',
    name: 'search',
    component: SearchView
  },
  {
    path: '/employee/:id',
    name: 'employee',
    component: EmployeeView
  },

  {
    path: '/employee',
    name: 'newEmployee',
    component: EmployeeView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router