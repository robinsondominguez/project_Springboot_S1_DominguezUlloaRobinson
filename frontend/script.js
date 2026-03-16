const app = {

    backend: "http://localhost:8080",


    login: async () => {
        const user = document.getElementById('user').value;
        const pass = document.getElementById('pass').value;

        try {
            const response = await fetch(`${app.backend}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: user, password: pass })
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('token', data.token); 
                app.updateStatus(true);
                app.print("Login exitoso. Token guardado.");
                app.request('GET', '/api/productos'); 
            } else {
                app.print("Error en el login. Verifica credenciales.");
            }
        } catch (error) {
            app.print("Error de conexión con el servidor.");
        }
    },

    request: async (method, endpoint) => {
        const token = localStorage.getItem('token');
        if (!token && endpoint !== '/auth/login') {
            app.print("No hay token. Inicia sesión primero.");
            return;
        }

        const id = document.getElementById('prod-id').value;
        const url = (method === 'DELETE' && id) ? `${app.backend}${endpoint}/${id}` : `${app.backend}${endpoint}`;

        const options = {
            method: method,
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        };

        if (method === 'POST' || method === 'PUT') {
            options.body = JSON.stringify(app.getFormData());
        }

        try {
            const response = await fetch(url, options);
            if (response.status === 204) { 
                app.print("Eliminado correctamente");
                app.request('GET', '/api/productos');
                return;
            }
            
            const data = await response.json();
            app.print(data);


            if (Array.isArray(data)) {
                app.fillTable(data);
            } else if (method !== 'GET') {

                app.request('GET', '/api/productos');
                app.clearForm();
            }
        } catch (error) {
            app.print("Error en la petición: " + error.message);
        }
    },


    getFormData: () => ({
        id: document.getElementById('prod-id').value || null,
        nombre: document.getElementById('prod-nombre').value,
        categoria: document.getElementById('prod-categoria').value,
        stock: parseInt(document.getElementById('prod-stock').value) || 0,
        precio: parseFloat(document.getElementById('prod-precio').value) || 0.0
    }),

    fillTable: (productos) => {
        const tbody = document.getElementById('tabla-productos');
        tbody.innerHTML = '';

        productos.forEach(p => {
            const stockClass = p.stock < 10 ? 'text-danger fw-bold' : 'text-success';
            tbody.innerHTML += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nombre}</td>
                    <td><span class="badge bg-light text-dark">${p.categoria || 'N/A'}</span></td>
                    <td class="${stockClass}">${p.stock}</td>
                    <td>$${p.precio ? p.precio.toFixed(2) : '0.00'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-warning" onclick="app.loadToForm(${JSON.stringify(p).replace(/"/g, '&quot;')})">📝</button>
                        <button class="btn btn-sm btn-outline-danger" onclick="document.getElementById('prod-id').value=${p.id}; app.request('DELETE', '/api/productos')">🗑️</button>
                    </td>
                </tr>
            `;
        });
    },

    loadToForm: (p) => {
        document.getElementById('prod-id').value = p.id;
        document.getElementById('prod-nombre').value = p.nombre;
        document.getElementById('prod-categoria').value = p.categoria;
        document.getElementById('prod-stock').value = p.stock;
        document.getElementById('prod-precio').value = p.precio;
    },

    clearForm: () => {
        document.getElementById('prod-id').value = '';
        document.getElementById('prod-nombre').value = '';
        document.getElementById('prod-categoria').value = '';
        document.getElementById('prod-stock').value = '';
        document.getElementById('prod-precio').value = '';
    },

    updateStatus: (connected) => {
        const status = document.getElementById('auth-status');
        if (connected) {
            status.classList.add('status-connected');
            status.innerHTML = 'Estado: <span>Conectado</span>';
        }
    },

    print: (data) => {
        const output = document.getElementById('json-output');
        output.innerText = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
    }
};