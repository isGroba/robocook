package com.cocina.robocook;

import com.cocina.robocook.dao.RecipeRepository;
import com.cocina.robocook.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class RobocookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobocookApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RecipeRepository receitaRepository) {
		return runner -> {

			//########## RECEITA-PASO ######################
			// saveRecietaAndPasos(receitaRepository);
			// findReceitaById(receitaRepository);
			// updateReceita(receitaRepository);
			// updatePaso(receitaRepository);
			// deletePasoById(receitaRepository);
			// deleteRecetaById(receitaRepository);
			// #############################################

			//########## RECEITA-INGREDIENTE ######################
			// saveRecipeAndStepsAndIngredients(receitaRepository);
			// findRecitaWithStepsAndIngredientsById(receitaRepository);
			// findWithEntityGraph(receitaRepository);
			// buscandoCousas(receitaRepository);
			//deleteLabel(receitaRepository);
			//deleteCategory(receitaRepository);
			// TODO :: pendiente ver que los updates y los delete para recipe funcionan

			// TODO :: a partir de aquí implementar el MVC para gestión interna/mantenimiento

		};
	}

	private void deleteCategory(RecipeRepository receitaRepository) {
		receitaRepository.deleteCategoryById((long)1);
	}

	private void buscandoCousas(RecipeRepository receitaRepository) {
		Label theLabel = receitaRepository.findLabelAndRecipesById((long)2);
		System.out.println(theLabel);
		System.out.println(theLabel.getRecipes());
	}

	private void deleteLabel(RecipeRepository receitaRepository) {
		receitaRepository.deleteLabelById((long)1);
	}

	private void findRecitaWithStepsAndIngredientsById(RecipeRepository receitaRepository) {
		long id = 5;

		Recipe tempReceita = receitaRepository.findRecipeAndIngredientsById(id);

		System.out.println("RecipeEntity: " + tempReceita);
		System.out.println("Pasos: " + tempReceita.getSteps());
		System.out.println("Ingredientes: " + tempReceita.getRecipeIngredients());

		Set<RecipeIngredient> listado = tempReceita.getRecipeIngredients();
		for(RecipeIngredient ri : listado){
			Ingredient ingredient = ri.getIngredient();
			System.out.println(ingredient.getName());
		}

		System.out.println("Done!");

	}

	private void saveRecipeAndStepsAndIngredients(RecipeRepository receitaRepository) {

		System.out.println("Creating recipe... ");
		Recipe theRecipe = new Recipe("Tortilla de pataca", "Tortilla básica de pataca", "50", Dificulty.MEDIA, Season.INVERNO, "70", "90", "4", "1700");

		System.out.println("Creating steps");
		Step step1 = new Step(1,"Pelar as patacas e picalas en cadrados para frixir");
		Step step2 = new Step(2,"Frixir as patacas en abundante aceite ata que estén casi feitas, pero sen chegar a frixilas de todo");
		Step step3 = new Step(3,"Batir 6 ovos, e cando as patacas estén listas, botalas no recipiente co ovo e mixturar ben");
		Step step4 = new Step(4,"Unha vez feito por o lume unha tixola con puco aceite a lume medio e cando esté quente botar a mixtura de ovo e pataca");
		Step step5 = new Step(5,"Cando comece a estar feita, darlle a volta coa axuda dun prato de deixale un minuto no lume. A continuación retirar da tixola e a servir na mesa");

		theRecipe.addStep(step1);
		theRecipe.addStep(step2);
		theRecipe.addStep(step3);
		theRecipe.addStep(step4);

		System.out.println("Creating ingredients");
		Ingredient ingredient1 = new Ingredient("Ovo", 50, null);
		Ingredient ingredient2 = new Ingredient("Pataca", 10, null);
		Ingredient ingredient3 = new Ingredient("Aceite", 300, null);

		theRecipe.addRecipeIngredient(new RecipeIngredient(theRecipe, ingredient1, 6.0, "unidades"));
		theRecipe.addRecipeIngredient(new RecipeIngredient(theRecipe, ingredient2, 0.5, "kilogramos"));
		theRecipe.addRecipeIngredient(new RecipeIngredient(theRecipe, ingredient3, 200.0, "mililitros"));

		System.out.println("Processing and saving");
		receitaRepository.saveRecipe(theRecipe);
		System.out.println("Done!");
	}

	private void deleteRecetaById(RecipeRepository receitaRepository) {
		long id = 5;

		receitaRepository.deleteRecipeById(id);
		System.out.println("Done!");
	}

	private void updatePaso(RecipeRepository receitaRepository) {
		long id = 5;
		System.out.println("Finding step: " + id);
		Step tempStep = receitaRepository.findStepById(id);
		tempStep.setDescription(tempStep.getDescription() + " Disfruta de cada paso de la receta incluido el paso de limpiar la cocina.");

		System.out.println("Updating: " + tempStep);
		receitaRepository.updateStep(tempStep);
		System.out.println("DONE!");
	}

	private void deletePasoById(RecipeRepository receitaRepository) {
		long id = 5;
		System.out.println("Deletting StepEntity: " + id);
		receitaRepository.deleteStepById(id);
		System.out.println("Done!");

	}

	private void updateReceita(RecipeRepository receitaRepository) {
		long id = 5;
		long ingredientId = 8;

		System.out.println("Finding recipe: " + id);
		Recipe tempReceita = receitaRepository.findRecipeCompleteById(id);
		Ingredient tempIngredient = receitaRepository.findIngredientById(ingredientId);

		//tempReceita.setPreparationTime("55");
		//tempReceita.addStep(new StepEntity(5, "Saborea este plato... y ponte a recoger!!"));
		//tempReceita.addRecipeIngredient(new RecipeIngredientEntity(tempReceita, tempIngredient, 200.0, "mililitros"));

		System.out.println("Adding label");
		Label theLabel = new Label("Para os nenos");
		tempReceita.addLabel(theLabel);

		System.out.println("Adding CategoryEntity");
		Category theCategory = new Category("vexetariana");
		tempReceita.addCategory(theCategory);

		System.out.println("Updateing recipe");
		receitaRepository.updateRecipe(tempReceita);

		System.out.println("Feito!");

	}

	private void findReceitaById(RecipeRepository receitaRepository) {
		long id = 5;

		Recipe tempReceita = receitaRepository.findRecipeById(id);

		System.out.println("RecipeEntity: " + tempReceita);
		System.out.println("Pasos: " + tempReceita.getSteps()
		);

	}

	private void saveRecietaAndPasos(RecipeRepository receitaRepository) {
		Recipe aReceita = new Recipe("Ensaladilla rusa", "Ensaladilla moi fresca, con diferentes ingredientes e maionesa", "45", Dificulty.SINXELA, Season.VERAN,
				"85", "95", "4", "1500");

		aReceita.addStep(new Step(1, "Ferver o variado de verduras que temos gardado no conxelador"));
		aReceita.addStep( new Step(2, "Picar os ingredientes frescos, tomate, cebola, etc... exuntalos todos nunha tarteira"));
		aReceita.addStep( new Step(3, "Xuntar todos os ingredientes na tarteira, e aliñar con aceite, vinagre e sal o gusto. Unha vez feito isto engadir maionesa o gusto e mezclar todo ben."));
		aReceita.addStep( new Step(4, "Servir e disfrutar."));

		System.out.println("RecipeEntity; " + aReceita);
		System.out.println("Pasos: " + aReceita.getSteps());

		receitaRepository.saveRecipe(aReceita);

		System.out.println("Feito!");

	}

}
