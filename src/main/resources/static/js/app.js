/* ========================================
   SUNDAY SCHOOL — JavaScript API Module
   ======================================== */

// ===== API Base URL =====
const API = '';

// ===== Token Management =====
function getToken() { return localStorage.getItem('jwt_token'); }
function getRole() { return localStorage.getItem('user_role'); }
function getUsername() { return localStorage.getItem('username'); }

function saveAuth(token, username, role) {
    localStorage.setItem('jwt_token', token);
    localStorage.setItem('username', username);
    localStorage.setItem('user_role', role);
}

function clearAuth() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('username');
    localStorage.removeItem('user_role');
}

function isLoggedIn() { return !!getToken(); }
function isTeacher() { return getRole() === 'ROLE_TEACHER'; }

function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}

// ===== API Fetch Helper =====
async function apiFetch(path, options = {}) {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;

    const res = await fetch(API + path, { ...options, headers });

    if (res.status === 401 || res.status === 403) {
        clearAuth();
        window.location.href = '/login';
        throw new Error('Session expired');
    }

    if (!res.ok) {
        const err = await res.json().catch(() => ({ message: 'Request failed' }));
        throw new Error(err.message || 'Request failed');
    }

    const text = await res.text();
    return text ? JSON.parse(text) : null;
}

// ===== Toast Notifications =====
function showToast(message, type = 'info') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    toast.className = 'toast toast-' + type;
    toast.innerHTML = (type === 'success' ? '✅' : type === 'error' ? '❌' : 'ℹ️') + ' ' + message;
    container.appendChild(toast);
    setTimeout(() => { toast.style.opacity = '0'; setTimeout(() => toast.remove(), 300); }, 3500);
}

// ===== Init Layout =====
function initLayout() {
    if (!requireAuth()) return;

    const username = getUsername() || 'User';
    const role = getRole() || '';

    // Set body class for role-based visibility
    if (isTeacher()) document.body.classList.add('role-teacher');

    // Update sidebar user info
    const userNameEl = document.querySelector('.user-name');
    const userRoleEl = document.querySelector('.user-role');
    const userAvatarEl = document.querySelector('.user-avatar');
    if (userNameEl) userNameEl.textContent = username;
    if (userRoleEl) userRoleEl.textContent = role.replace('ROLE_', '');
    if (userAvatarEl) userAvatarEl.textContent = username.charAt(0).toUpperCase();

    // Highlight active nav
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        if (link.getAttribute('href') === currentPath) link.classList.add('active');
    });

    // Logout
    const logoutBtn = document.querySelector('.btn-logout');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            clearAuth();
            window.location.href = '/login';
        });
    }

    // Dynamically populate sidebar class links
    const classLinksEl = document.getElementById('sidebarClassLinks');
    if (classLinksEl) {
        const CLASS_ICONS = ['🌱', '🌼', '⭐', '🎓', '🏆'];
        apiFetch('/api/classes').then(classes => {
            if (!classes || classes.length === 0) {
                classLinksEl.innerHTML = '<span style="font-size:12px;color:var(--text-muted);padding:6px 12px;display:block">No classes found</span>';
                return;
            }
            classLinksEl.innerHTML = classes.map((c, i) => {
                const icon = CLASS_ICONS[i % CLASS_ICONS.length];
                const isActive = window.location.pathname === '/class/' + c.classId;
                return `<a href="/class/${c.classId}" class="nav-link${isActive ? ' active' : ''}">
                    <span class="nav-icon">${icon}</span> ${c.className}
                </a>`;
            }).join('');
        }).catch(() => {
            classLinksEl.innerHTML = '';
        });
    }
}

// ===== Modal Helpers =====
function openModal(id) { document.getElementById(id).classList.add('show'); }
function closeModal(id) { document.getElementById(id).classList.remove('show'); }

// ===== Format Date =====
function formatDate(dateStr) {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' });
}

// ===== Debounce =====
function debounce(fn, delay) {
    let timer;
    return function (...args) {
        clearTimeout(timer);
        timer = setTimeout(() => fn.apply(this, args), delay);
    };
}
