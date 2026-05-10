document.addEventListener('DOMContentLoaded', function () {
    var form = document.querySelector('.mgmt-form');
    if (!form) return;

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        var method = (form.dataset.method || 'POST').toUpperCase();
        var action = form.action;
        var successUrl = form.dataset.successUrl || '/';
        var errorEl = form.querySelector('.form-error');

        var payload = buildPayload(form);

        fetch(action, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        }).then(function (r) {
            if (r.redirected) {
                window.location.href = r.url;
            } else if (r.ok) {
                window.location.href = successUrl;
            } else {
                showError(errorEl, 'Request failed with status ' + r.status + '. Check the values and try again.');
            }
        }).catch(function (err) {
            showError(errorEl, 'Network error: ' + err.message);
        });
    });

    function buildPayload(form) {
        var data = {};
        form.querySelectorAll('[name]').forEach(function (el) {
            var key = el.name;

            // multi-select: collect all selected option values as an array
            if (el.tagName === 'SELECT' && el.multiple) {
                var selected = Array.from(el.selectedOptions).map(function (o) { return o.value; }).filter(Boolean);
                if (selected.length === 0) return;
                setNestedValue(data, key, selected);
                return;
            }

            var raw = el.value.trim();

            if (!raw && el.type !== 'hidden') return;

            var value;
            var dataType = el.dataset.type;

            if (dataType === 'array') {
                value = raw.split(',').map(function (s) { return s.trim(); }).filter(Boolean);
                if (value.length === 0) return;
            } else if (dataType === 'number') {
                if (!raw) return;
                value = Number(raw);
            } else if (dataType === 'instant') {
                if (!raw) return;
                value = new Date(raw).toISOString();
            } else {
                if (!raw) return;
                value = raw;
            }

            setNestedValue(data, key, value);
        });
        return data;
    }

    function setNestedValue(obj, path, value) {
        var parts = path.split('.');
        var cur = obj;
        for (var i = 0; i < parts.length - 1; i++) {
            if (!cur[parts[i]] || typeof cur[parts[i]] !== 'object') {
                cur[parts[i]] = {};
            }
            cur = cur[parts[i]];
        }
        cur[parts[parts.length - 1]] = value;
    }

    function showError(el, msg) {
        if (!el) { alert(msg); return; }
        el.textContent = msg;
        el.classList.add('visible');
    }
});
