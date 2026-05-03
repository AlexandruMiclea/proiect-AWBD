// ── Tab switching ──────────────────────────────────────────────
function switchTab(tab) {
    const tabs = document.querySelectorAll('.tab-btn');
    const panels = document.querySelectorAll('.form-section');

    tabs.forEach(t => {
        const isActive = t.id === 'tab-' + tab;
        t.classList.toggle('active', isActive);
        t.setAttribute('aria-selected', isActive);
    });

    panels.forEach(p => {
        const isVisible = p.id === 'form-' + tab;
        p.classList.toggle('visible', isVisible);
    });
}

// ── Password strength meter ────────────────────────────────────
function updateStrength(value) {
    const bars = [1, 2, 3, 4].map(i => document.getElementById('sb' + i));
    const colors = ['#c8472b', '#e8924a', '#d4b84a', '#3aaa6f'];

    let score = 0;
    if (value.length >= 8) score++;
    if (/[A-Z]/.test(value)) score++;
    if (/[0-9]/.test(value)) score++;
    if (/[^A-Za-z0-9]/.test(value)) score++;

    bars.forEach((bar, i) => {
        bar.style.background = i < score ? colors[score - 1] : 'var(--border)';
    });
}

// ── Activate correct tab on page load (server-driven) ──────────
// Reads the active tab state set by Thymeleaf via the class.
document.addEventListener('DOMContentLoaded', () => {
    const activePanel = document.querySelector('.form-section.visible');
    if (activePanel && activePanel.id === 'form-register') {
        document.getElementById('tab-register').classList.add('active');
        document.getElementById('tab-signin').classList.remove('active');
    }
});