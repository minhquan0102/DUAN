// Lấy element
const root = document.documentElement;
const rootApp = document.querySelector('.app');
const toggleBtn = document.getElementById('toggleSidebar');
const themeBtn = document.getElementById('themeBtn');

// Key lưu trữ
const THEME_KEY = 'ps_theme';
const SIDEBAR_KEY = 'ps_sidebar_collapsed';

// ----- Theme -----
function applyTheme(t) {
    t === 'light' ? root.classList.add('light') : root.classList.remove('light');
}
applyTheme(localStorage.getItem(THEME_KEY) || '');
themeBtn.addEventListener('click', () => {
    const nowLight = !root.classList.contains('light');
    applyTheme(nowLight ? 'light' : '');
    localStorage.setItem(THEME_KEY, nowLight ? 'light' : '');
});

// ----- Sidebar -----
if (localStorage.getItem(SIDEBAR_KEY) === 'true') {
    rootApp.classList.add('collapsed');
}
toggleBtn.addEventListener('click', () => {
    const collapsed = rootApp.classList.toggle('collapsed');
    localStorage.setItem(SIDEBAR_KEY, collapsed);
});

// ----- Chart (nếu có) -----
const chartEl = document.getElementById('revenueChart');
if (chartEl) {
    const ctx = chartEl.getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Ngày 1','Ngày 2','Ngày 3','Ngày 4','Ngày 5','Ngày 6','Ngày 7'],
            datasets: [{
                label: 'Doanh thu',
                data: [1,2,1.5,3,4,6,5],
                backgroundColor: 'rgba(0,123,255,0.2)',
                borderColor: '#007bff',
                borderWidth: 2,
                fill: true,
                tension: 0.3,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {responsive:true, plugins:{legend:{display:false}}, scales:{y:{beginAtZero:true}}}
    });
}