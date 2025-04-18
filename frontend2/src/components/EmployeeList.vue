<script>
import api from '@/services/api'; // Assurez-vous que le chemin est correct

export default {
  name: 'EmployeeList',
  data() {
    return {
      employees: [],
      loading: true,
      error: null
    };
  },
  async mounted() {
    try {
      const response = await api.get('/employees');
      this.employees = response.data.content || response.data;
    } catch (err) {
      console.error(err);
      this.error = 'Erreur lors du chargement des employés';
    } finally {
      this.loading = false;
    }
  }
};
</script>
