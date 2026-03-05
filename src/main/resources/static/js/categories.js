let selectedCategories = window.initialCategories || [];

document.addEventListener("DOMContentLoaded", () => {

    const input = document.getElementById("category-input");
    const suggestions = document.getElementById("category-suggestions");

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
            const response = await fetch(`/categories/search?query=${query}`);

            const categories = await response.json();

            suggestions.innerHTML = "";

            categories
                .filter(category => !selectedCategories.some(l => l.id === category.id))
                .forEach(category => {

                    const li = document.createElement("li");
                    li.className = "list-group-item list-group-item-action";
                    li.textContent = category.name;

                    li.onclick = () => {
                        addCategory(category);
                        suggestions.innerHTML = "";
                        input.value = "";
                    };

                    suggestions.appendChild(li);
                });

        } catch (error) {
            console.error("Error cargando categories:", error);
        }
    });

    renderCategories();
});

function addCategory(category) {
    selectedCategories.push(category);
    renderCategories();
}

function removeCategory(id) {
    selectedCategories = selectedCategories.filter(l => l.id !== id);
    renderCategories();
}

function renderCategories() {
    const container = document.getElementById("selected-categories");
    const hidden = document.getElementById("hidden-categories");

    container.innerHTML = "";
    hidden.innerHTML = "";

    selectedCategories.forEach((category, index) => {

        const span = document.createElement("span");
        span.className = "badge bg-warning me-1";
        span.style.cursor = "pointer";
        span.innerHTML = `${category.name} ✕`;

        span.onclick = () => removeCategory(category.id);

        container.appendChild(span);

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = `categories[${index}].id`;
        input.value = category.id;

        hidden.appendChild(input);
    });
}