import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8081/api/v1', // adapte le port et le chemin si nécessaire
    headers: {
        'Content-Type': 'application/json'
    }
});

// Requête pour la recherche d'employés
export const searchEmployees = async (query,  page = 0, size = 20) => {
    const response = await apiClient.get(`/employees/search`, {
        params: { 
            query,
            page,
            size
        }
    });
    return response.data;
};

export const getEmployeeById = async (id) => {
    const response = await apiClient.get(`/employees/${id}`);
    return response.data;
};

export const getDepartments = async () => {
    const response = await apiClient.get(`/departments`);
    return response.data;
};

// Export par défaut de toutes les fonctions
export default {
    searchEmployees,
    getEmployeeById,
    getDepartments
};
