let ingredientIndex = 0;

function initRecipeForm(initialIngredientsCount){
    ingredientIndex = initialIngredientsCount;

    const serchInput = document.getElementById('ingredient-search');
    if(serchInput){
        serchInput.addEventListener('input', handleIngredientSearch);
    }
}
function  handleIngredientSearch(e){
    let term = e.target.value;
    if (term.length < 2) {
        document.getElementById('search-results').innerHTML = '';
        return;
    }

    // Llamada al endpoint de búsqueda
    fetch(`/ingredients/search?query=` + term )
        .then(response => response.json())
        .then(data => {
            let html = '';
            data.forEach(ing => {
                html += `<button type="button" class="list-group-item list-group-item-action"
                         onclick="addIngredientRow(`+ing.id+`, '`+ ing.name+`')">`+ing.name+`</button>`;
            });
            document.getElementById('search-results').innerHTML = html;
        });
};

function addIngredientRow(id, name) {
    const container = document.getElementById("ingredients-container");
    const tr = document.createElement("tr");

    tr.innerHTML = `
        <input type="hidden" name="recipeIngredients[` + ingredientIndex + `].ingredient.id" value="${id}">
        <td><span>` + name + `</span></td>
        <td><input type="text" name="recipeIngredients[` + ingredientIndex + `].amount" class="form-control" placeholder="1, 2, 3... "></td>
        <td><input type="text" name="recipeIngredients[` + ingredientIndex + `].unit" class="form-control" placeholder="Unit, kilograms, liters... "></td>
        <td><input type="text" name="recipeIngredients[` + ingredientIndex + `].optional" class="form-control" value="false" placeholder="No"></td>
        <td><button type="button" class="btn btn-danger btn-sm" onclick="removeIngredient(this)">X</button></td>
    `;

    container.appendChild(tr);
    ingredientIndex++;

    // Limpiar buscador
    document.getElementById('ingredient-search').value = '';
    document.getElementById('search-results').innerHTML = '';
}

function reindexIngredients() {
    const rows = document.querySelectorAll("#ingredients-container tr");

    rows.forEach((row, index) => {
        const inputs = row.querySelectorAll("input");

        inputs.forEach(input => {
            if (input.name && input.name.includes("recipeIngredients[")) {
                const fieldName = input.name.split('.').pop();

                if(input.name.includes("ingredient.id")){
                    input.name = "recipeIngredients[" + index + "].ingredient.id";
                }else{
                    input.name = "recipeIngredients[" + index + "]." + fieldName;
                }
            }
        });
    });

    ingredientIndex = rows.length;
}

function removeIngredient(button) {
    button.closest('tr').remove();
    reindexIngredients();
}