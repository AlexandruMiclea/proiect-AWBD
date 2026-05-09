(function () {
    var btn = document.getElementById('hamburgerBtn');
    var drawer = document.getElementById('navDrawer');
    var overlay = document.getElementById('drawerOverlay');

    function openMenu() {
        drawer.classList.add('open');
        btn.classList.add('open');
        overlay.classList.add('open');
        btn.setAttribute('aria-expanded', 'true');
    }

    function closeMenu() {
        drawer.classList.remove('open');
        btn.classList.remove('open');
        overlay.classList.remove('open');
        btn.setAttribute('aria-expanded', 'false');
    }

    btn.addEventListener('click', function () {
        drawer.classList.contains('open') ? closeMenu() : openMenu();
    });

    overlay.addEventListener('click', closeMenu);

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') closeMenu();
    });
})();