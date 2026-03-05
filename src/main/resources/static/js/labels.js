let selectedLabels = window.initialLabels || [];

document.addEventListener("DOMContentLoaded", () => {

    const input = document.getElementById("label-input");
    const suggestions = document.getElementById("suggestions");

    if (!input || !suggestions) {
        console.error("No se encontró input o suggestions");
        return;
    }

    input.addEventListener("input", async (e) => {
        const query = e.target.value;

        if (query.length < 2) {
            suggestions.innerHTML = "";
            return;
        }

        try {
            const response = await fetch(`/labels/search?query=${query}`);

            const labels = await response.json();

            suggestions.innerHTML = "";

            labels
                .filter(label => !selectedLabels.some(l => l.id === label.id))
                .forEach(label => {

                    const li = document.createElement("li");
                    li.className = "list-group-item list-group-item-action";
                    li.textContent = label.name;

                    li.onclick = () => {
                        addLabel(label);
                        suggestions.innerHTML = "";
                        input.value = "";
                    };

                    suggestions.appendChild(li);
                });

        } catch (error) {
            console.error("Error cargando labels:", error);
        }
    });

    renderLabels();
});

function addLabel(label) {
    selectedLabels.push(label);
    renderLabels();
}

function removeLabel(id) {
    selectedLabels = selectedLabels.filter(l => l.id !== id);
    renderLabels();
}

function renderLabels() {
    const container = document.getElementById("selected-labels");
    const hidden = document.getElementById("hidden-labels");

    container.innerHTML = "";
    hidden.innerHTML = "";

    selectedLabels.forEach((label, index) => {

        const span = document.createElement("span");
        span.className = "badge bg-warning me-1";
        span.style.cursor = "pointer";
        span.innerHTML = `${label.name} ✕`;

        span.onclick = () => removeLabel(label.id);

        container.appendChild(span);

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = `labels[${index}].id`;
        input.value = label.id;

        hidden.appendChild(input);
    });
}