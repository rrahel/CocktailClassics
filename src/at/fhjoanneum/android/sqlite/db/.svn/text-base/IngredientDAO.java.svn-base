package at.fhjoanneum.android.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.data.Ingredient;
import at.fhjoanneum.android.sqlite.data.Ressource;

/**
 * Class to interact with the database.
 * Here you find all the queries for the ingredient table of the data base
 * @author Manuela Mayer, Kahmann Sebastian, Egger Andreas, Cemi Rrahel
 *
 */
public class IngredientDAO extends CocktailDBDAO {

	public static final String INGREDIENT_ID_WITH_PREFIX = "ingredient.id";
	public static final String COCKTAIL_ID_WITH_PREFIX = "cocktail.id";
	public static final String RESSOURCE_ID_WITH_PREFIX = "ressource.id";

	public static final String INGREDIENT_ID = "ing.id";
	public static final String RESSOURCE_ID = "res.id";

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	/**
	 * Constructor which calls the super constructor
	 * @param context Current state of the application
	 */
	public IngredientDAO(Context context) {
		super(context);
	}

	/**
	 * Save a new ingredient in the ingredient table
	 * @param ingredient Ingredient to save
	 * @return If the new ingredient was inserted
	 */
	public long save(Ingredient ingredient) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.INGREDIENT_COCKTAIL_ID, ingredient
				.getCocktail().getId());
		values.put(DataBaseHelper.INGREDIENT_RESSOURCE_ID, ingredient
				.getRessource().getId());
		values.put(DataBaseHelper.INGREDIENT_AMOUNT, ingredient.getAmount());
		values.put(DataBaseHelper.INGREDIENT_UNIT, ingredient.getUnit());

		return database.insert(DataBaseHelper.INGREDIENT_TABLE, null, values);
	}

	/**
	 * To update a ingredient
	 * @param ingredient Ingredient to update
	 * @return If the ingredient was updated
	 */
	public long update(Ingredient ingredient) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.INGREDIENT_COCKTAIL_ID, ingredient
				.getCocktail().getId());
		values.put(DataBaseHelper.INGREDIENT_RESSOURCE_ID, ingredient
				.getRessource().getId());
		values.put(DataBaseHelper.INGREDIENT_AMOUNT, ingredient.getAmount());
		values.put(DataBaseHelper.INGREDIENT_UNIT, ingredient.getUnit());

		long result = database.update(DataBaseHelper.INGREDIENT_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(ingredient.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	/**
	 * To delete a ingredient from the table
	 * @param ingredient Ingredient to delete
	 * @return If the ingredient was deleted
	 */
	public int deleteIngredient(Ingredient ingredient) {
		return database.delete(DataBaseHelper.INGREDIENT_TABLE,
				WHERE_ID_EQUALS, new String[] { ingredient.getId() + "" });
	}

	/**
	 * To get a list of all ingredients which have the filter input in the name of the related cocktail 
	 * @param filterInput Name of the ingredient you want to find
	 * @return Array list of the found ingredients
	 */
	public ArrayList<Ingredient> getIngredients(String filterInput) {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		String query = "SELECT distinct " + INGREDIENT_ID_WITH_PREFIX + ","
				+ COCKTAIL_ID_WITH_PREFIX + "," + RESSOURCE_ID_WITH_PREFIX
				+ "," + DataBaseHelper.INGREDIENT_AMOUNT + ","
				+ DataBaseHelper.INGREDIENT_UNIT + " FROM "
				+ DataBaseHelper.INGREDIENT_TABLE + " ingredient "
				+ " INNER JOIN " + DataBaseHelper.RESSOURCE_TABLE
				+ " ressource " + " ON " + " ingredient."
				+ DataBaseHelper.INGREDIENT_RESSOURCE_ID + " = "
				+ " ressource." + DataBaseHelper.ID_COLUMN + " INNER JOIN "
				+ DataBaseHelper.COCKTAIL_TABLE + " cocktail " + " ON "
				+ " ingredient." + DataBaseHelper.INGREDIENT_COCKTAIL_ID
				+ " = " + " cocktail." + DataBaseHelper.ID_COLUMN 
				+ " WHERE "
				+ " cocktail." + DataBaseHelper.NAME_COLUMN
				+ " LIKE  '%" + filterInput + "%'" 
				+ " order by cocktail." + DataBaseHelper.NAME_COLUMN + " ; ";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Ingredient ingredient = new Ingredient();
			ingredient.setId(cursor.getInt(0));

			Cocktail cocktail = new Cocktail();
			CocktailDAO cocktailDAO = new CocktailDAO(getmContext());
			cocktail.setId(cursor.getInt(1));
			cocktail.setName(cocktailDAO.getCocktailyById(cocktail.getId()).getName());
			cocktail.setRaiting(cocktailDAO.getCocktailyById(cocktail.getId()).getRating());
			cocktail.setRecipie(cocktailDAO.getCocktailyById(cocktail.getId()).getRecipie());
			cocktail.setNote(cocktailDAO.getCocktailyById(cocktail.getId()).getNote());
			cocktail.setAlcohol(cocktailDAO.getCocktailyById(cocktail.getId()).getAlcohol());
			cocktail.setCategory(cocktailDAO.getCocktailyById(cocktail.getId()).getCategory());
			ingredient.setCocktail(cocktail);

			Ressource ressource = new Ressource();
			RessourceDAO ressourceDAO = new RessourceDAO(getmContext());
			ressource.setId(cursor.getInt(2));
			ressource.setName(ressourceDAO.getRessourceById(ressource.getId()).getName());
			ressource.setInBar(ressourceDAO.getRessourceById(ressource.getId()).getBar());
			ressource.setInShoppingList(ressourceDAO.getRessourceById(ressource.getId()).getInShoppingList());
			ingredient.setRessource(ressource);

			ingredient.setAmount(cursor.getDouble(3));
			ingredient.setUnit(cursor.getString(4));

			ingredients.add(ingredient);
		}
		cursor.close();
		return ingredients;
	}

	/**
	 * Returns a list of all ingredients which have set the inshoppinglist flag in the related cocktail
	 * @return Array list of ingredients which have the inshoppinglist flag set in the related cocktail
	 */
	public ArrayList<Ingredient> getAllIngredientsForShopping() {

		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		String query = "SELECT distinct " + INGREDIENT_ID_WITH_PREFIX + ","
				+ COCKTAIL_ID_WITH_PREFIX + "," + RESSOURCE_ID_WITH_PREFIX
				+ "," + DataBaseHelper.INGREDIENT_AMOUNT + ","
				+ DataBaseHelper.INGREDIENT_UNIT + " FROM "
				+ DataBaseHelper.INGREDIENT_TABLE + " ingredient "
				+ " INNER JOIN " + DataBaseHelper.RESSOURCE_TABLE
				+ " ressource " + " ON " + " ingredient."
				+ DataBaseHelper.INGREDIENT_RESSOURCE_ID + " = "
				+ " ressource." + DataBaseHelper.ID_COLUMN + " INNER JOIN "
				+ DataBaseHelper.COCKTAIL_TABLE + " cocktail " + " ON "
				+ " ingredient." + DataBaseHelper.INGREDIENT_COCKTAIL_ID
				+ " = " + " cocktail." + DataBaseHelper.ID_COLUMN + " WHERE "
				+ " ressource." + DataBaseHelper.RESSOURCE_INSHOPPINGLIST
				+ " = " + 1
				+ " group by ressource." + DataBaseHelper.NAME_COLUMN
				+ " order by ressource." + DataBaseHelper.NAME_COLUMN + " ; ";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Ingredient ingredient = new Ingredient();
			ingredient.setId(cursor.getInt(0));

			Cocktail cocktail = new Cocktail();
			CocktailDAO cocktailDAO = new CocktailDAO(getmContext());
			cocktail.setId(cursor.getInt(1));
			cocktail.setName(cocktailDAO.getCocktailyById(cocktail.getId())
					.getName());
			cocktail.setRaiting(cocktailDAO.getCocktailyById(cocktail.getId())
					.getRating());
			cocktail.setRecipie(cocktailDAO.getCocktailyById(cocktail.getId())
					.getRecipie());
			cocktail.setNote(cocktailDAO.getCocktailyById(cocktail.getId())
					.getNote());
			cocktail.setAlcohol(cocktailDAO.getCocktailyById(cocktail.getId())
					.getAlcohol());
			cocktail.setCategory(cocktailDAO.getCocktailyById(cocktail.getId())
					.getCategory());
			ingredient.setCocktail(cocktail);

			Ressource ressource = new Ressource();
			RessourceDAO ressourceDAO = new RessourceDAO(getmContext());
			ressource.setId(cursor.getInt(2));
			ressource.setName(ressourceDAO.getRessourceById(ressource.getId())
					.getName());
			ressource.setInBar(ressourceDAO.getRessourceById(ressource.getId())
					.getBar());
			ressource.setInShoppingList(ressourceDAO.getRessourceById(
					ressource.getId()).getInShoppingList());
			ingredient.setRessource(ressource);

			ingredient.setAmount(cursor.getDouble(3));
			ingredient.setUnit(cursor.getString(4));

			ingredients.add(ingredient);
		}
		cursor.close();
		return ingredients;
	}

	/**
	 * Loads all ingredients in the ingredient table
	 */
	public void loadIngredients() {

		CocktailDAO cocktailDAO = new CocktailDAO(getmContext());
		RessourceDAO ressourceDAO = new RessourceDAO(getmContext());

		Ingredient ginFizzOJuice = new Ingredient(cocktailDAO.getCocktails(
				"Gin Fizz", true).get(0), ressourceDAO.getRessources("Zitronensaft")
				.get(0), 3, " cl");
		Ingredient ginFizzGin = new Ingredient(cocktailDAO.getCocktails(
				"Gin Fizz", true).get(0), ressourceDAO.getRessources("Gin").get(0),
				5, " cl");
		Ingredient ginFizzSugarJuice = new Ingredient(cocktailDAO.getCocktails(
				"Gin Fizz", true).get(0), ressourceDAO.getRessources("Zuckersirup")
				.get(0), 2, " cl");
		Ingredient saturnGin = new Ingredient(cocktailDAO
				.getCocktails("Saturn", true).get(0), ressourceDAO.getRessources(
				"Gin").get(0), 3.7, " cl");
		Ingredient saturnLJuice = new Ingredient(cocktailDAO.getCocktails(
				"Saturn", true).get(0), ressourceDAO.getRessources("Zuckersirup")
				.get(0), 1.5, " cl");
		Ingredient saturnMaracuja = new Ingredient(cocktailDAO.getCocktails(
				"Saturn", true).get(0), ressourceDAO.getRessources("Maracujasirup")
				.get(0), 1.5, " cl");
		Ingredient saturnFalernum = new Ingredient(cocktailDAO.getCocktails(
				"Saturn", true).get(0),
				ressourceDAO.getRessources("Falernum").get(0), 0.7, " cl");
		Ingredient saturnMandel = new Ingredient(cocktailDAO.getCocktails(
				"Saturn", true).get(0), ressourceDAO.getRessources("Mandelsirup")
				.get(0), 0.7, " cl");
		Ingredient bolognaGin = new Ingredient(cocktailDAO.getCocktails(
				"Bologna", true).get(0), ressourceDAO.getRessources("Gin").get(0), 2,
				" cl");
		Ingredient bolognaAperol = new Ingredient(cocktailDAO.getCocktails(
				"Bologna", true).get(0), ressourceDAO.getRessources("Aperol").get(0),
				2, " cl");
		Ingredient bolognaDVermouth = new Ingredient(cocktailDAO.getCocktails(
				"Bologna", true).get(0), ressourceDAO.getRessources("Dry Vermouth")
				.get(0), 2, " cl");
		Ingredient bigBenGin = new Ingredient(cocktailDAO.getCocktails(
				"Big Ben", true).get(0), ressourceDAO.getRessources("Gin").get(0), 5,
				" cl");
		Ingredient bigBenLJuice = new Ingredient(cocktailDAO.getCocktails(
				"Big Ben", true).get(0), ressourceDAO.getRessources("Zitronensaft")
				.get(0), 2, " cl");
		Ingredient bigBenOJuice = new Ingredient(cocktailDAO.getCocktails(
				"Big Ben", true).get(0), ressourceDAO.getRessources("Orangensaft")
				.get(0), 4, " cl");
		Ingredient bigBenGren = new Ingredient(cocktailDAO.getCocktails(
				"Big Ben", true).get(0), ressourceDAO.getRessources("Grenadine").get(
				0), 1, " bl");
		Ingredient bigBenBitter = new Ingredient(cocktailDAO.getCocktails(
				"Big Ben", true).get(0), ressourceDAO.getRessources("Bitter Lemon")
				.get(0), 0, " zum Auffüllen");
		Ingredient negroniGin = new Ingredient(cocktailDAO.getCocktails(
				"Negroni", true).get(0), ressourceDAO.getRessources("Gin").get(0), 3,
				" cl");
		Ingredient negroniSVermouth = new Ingredient(cocktailDAO.getCocktails(
				"Negroni", true).get(0), ressourceDAO.getRessources("Sweet Vermouth")
				.get(0), 3, " cl");
		Ingredient negroniCamp = new Ingredient(cocktailDAO.getCocktails(
				"Negroni", true).get(0),
				ressourceDAO.getRessources("Campari").get(0), 3, " cl");
		Ingredient barraBiteWodka = new Ingredient(cocktailDAO.getCocktails(
				"Barracuda Bite", true).get(0), ressourceDAO.getRessources("Wodka")
				.get(0), 4, " cl");
		Ingredient barraBiteLiJuice = new Ingredient(cocktailDAO.getCocktails(
				"Barracuda Bite", true).get(0), ressourceDAO.getRessources(
				"Limettensaft").get(0), 2, " cl");
		Ingredient barraBiteGren = new Ingredient(cocktailDAO.getCocktails(
				"Barracuda Bite", true).get(0), ressourceDAO.getRessources(
				"Grenadine").get(0), 2, " cl");
		Ingredient tequilaSunTeq = new Ingredient(cocktailDAO.getCocktails(
				"Tequila Sunrise", true).get(0), ressourceDAO
				.getRessources("Tequila").get(0), 6, " cl");
		Ingredient tequilaSunGren = new Ingredient(cocktailDAO.getCocktails(
				"Tequila Sunrise", true).get(0), ressourceDAO.getRessources(
				"Grenadine").get(0), 2, " cl");
		Ingredient tequilaSunLJuice = new Ingredient(cocktailDAO.getCocktails(
				"Tequila Sunrise", true).get(0), ressourceDAO.getRessources(
				"Zitronensaft").get(0), 1, " dash");
		Ingredient tequilaSunOJuice = new Ingredient(cocktailDAO.getCocktails(
				"Tequila Sunrise", true).get(0), ressourceDAO.getRessources(
				"Orangensaft").get(0), 11, " cl");
		Ingredient martinezGin = new Ingredient(cocktailDAO.getCocktails(
				"Martinez", true).get(0), ressourceDAO.getRessources("Gin").get(0),
				3, " cl");
		Ingredient martinezSVermouth = new Ingredient(cocktailDAO.getCocktails(
				"Martinez", true).get(0), ressourceDAO
				.getRessources("Sweet Vermouth").get(0), 6, " cl");
		Ingredient martinezOBitters = new Ingredient(cocktailDAO.getCocktails(
				"Martinez", true).get(0), ressourceDAO
				.getRessources("Orange Bitters").get(0), 1, " dash");
		Ingredient martinezMaraschino = new Ingredient(cocktailDAO
				.getCocktails("Martinez", true).get(0), ressourceDAO.getRessources(
				"Maraschino").get(0), 1, " dash");
		Ingredient martiniGin = new Ingredient(cocktailDAO.getCocktails(
				"Martini", true).get(0), ressourceDAO.getRessources("Gin").get(0), 6,
				" cl");
		Ingredient martiniDVermouth = new Ingredient(cocktailDAO.getCocktails(
				"Martini", true).get(0), ressourceDAO.getRessources("Dry Vermouth")
				.get(0), 1, " cl");
		Ingredient ginBasilGin = new Ingredient(cocktailDAO.getCocktails(
				"Gin-Basil Smash", true).get(0), ressourceDAO.getRessources("Gin")
				.get(0), 5, " cl");
		Ingredient ginBasilZuckerSirup = new Ingredient(cocktailDAO
				.getCocktails("Gin-Basil Smash", true).get(0), ressourceDAO
				.getRessources("Zuckersirup").get(0), 2, " cl");
		Ingredient ginBasilLjuice = new Ingredient(cocktailDAO.getCocktails(
				"Gin-Basil Smash", true).get(0), ressourceDAO.getRessources(
				"Zitronensaft").get(0), 3, " cl");
		Ingredient ginBasilBasil = new Ingredient(cocktailDAO.getCocktails(
				"Gin-Basil Smash", true).get(0), ressourceDAO.getRessources(
				"Basilikum").get(0), 15, " kleine Blätter");
		Ingredient maiTaiRum = new Ingredient(cocktailDAO.getCocktails(
				"Mai Tai", true).get(0), ressourceDAO.getRessources("Light Rum").get(
				0), 3, " cl");
		Ingredient maiTaiCuracao = new Ingredient(cocktailDAO.getCocktails(
				"Mai Tai", true).get(0), ressourceDAO.getRessources(
				"Curacao Dry Orange").get(0), 1.5, " cl");
		Ingredient maiTaiJRum = new Ingredient(cocktailDAO.getCocktails(
				"Mai Tai", true).get(0), ressourceDAO.getRessources("Jamaika Rum")
				.get(0), 3, " cl");
		Ingredient maiTaiMandel = new Ingredient(cocktailDAO.getCocktails(
				"Mai Tai", true).get(0), ressourceDAO.getRessources("Mandelsirup")
				.get(0), 1.5, " cl");
		Ingredient maiTaiLiJuice = new Ingredient(cocktailDAO.getCocktails(
				"Mai Tai", true).get(0), ressourceDAO.getRessources("Limettensaft")
				.get(0), 3, " cl");
		Ingredient cubaLibreLim = new Ingredient(cocktailDAO.getCocktails(
				"Cuba Libre", true).get(0), ressourceDAO.getRessources("Limette")
				.get(0), 1, " stk.");
		Ingredient cubaLibreRum = new Ingredient(cocktailDAO.getCocktails(
				"Cuba Libre", true).get(0), ressourceDAO.getRessources("Light Rum")
				.get(0), 4, " cl");
		Ingredient cubaLibreCola = new Ingredient(cocktailDAO.getCocktails(
				"Cuba Libre", true).get(0),
				ressourceDAO.getRessources("Cola").get(0), 0, " Zum Auffüllen");
		Ingredient singaporeSlingGin = new Ingredient(cocktailDAO.getCocktails(
				"Singapore Sling", true).get(0), ressourceDAO.getRessources("Gin")
				.get(0), 5, " cl");
		Ingredient singaporeSlingCoint = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Cointreau").get(0), 0.7, " cl");
		Ingredient singaporeSlingCherry = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Cherry Brandy (Heering)").get(0), 1.5, " cl");
		Ingredient singaporeSlingBen = new Ingredient(cocktailDAO.getCocktails(
				"Singapore Sling", true).get(0), ressourceDAO.getRessources(
				"Benedictine").get(0), 0.7, " cl");
		Ingredient singaporeSlingGren = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Grenadine").get(0), 1, " cl");
		Ingredient singaporeSlingLiJuice = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Limettensaft").get(0), 1.5, " cl");
		Ingredient singaporeSlingAngo = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Angostura Bitters").get(0), 1, " dash");
		Ingredient singaporeSlingAnnanas = new Ingredient(cocktailDAO
				.getCocktails("Singapore Sling", true).get(0), ressourceDAO
				.getRessources("Ananassaft").get(0), 12, " cl");
		Ingredient whiskeySourWhiskey = new Ingredient(cocktailDAO
				.getCocktails("Whiskey Sour", true).get(0), ressourceDAO
				.getRessources("Whiskey").get(0), 5, " cl");
		Ingredient whiskeySourLJuice = new Ingredient(cocktailDAO.getCocktails(
				"Whiskey Sour", true).get(0), ressourceDAO.getRessources(
				"Zitronensaft").get(0), 3, " cl");
		Ingredient whiskeySourSugar = new Ingredient(cocktailDAO.getCocktails(
				"Whiskey Sour", true).get(0), ressourceDAO.getRessources(
				"Zuckersirup").get(0), 2, " cl");
		Ingredient elPresiRum = new Ingredient(cocktailDAO.getCocktails(
				"El Presidente", true).get(0), ressourceDAO.getRessources(
				"Light Rum").get(0), 4.5, " cl");
		Ingredient elPresiDVerm = new Ingredient(cocktailDAO.getCocktails(
				"El Presidente", true).get(0), ressourceDAO.getRessources(
				"Dry Vermouth").get(0), 1.5, " cl");
		Ingredient elPresiCoint = new Ingredient(cocktailDAO.getCocktails(
				"El Presidente", true).get(0), ressourceDAO.getRessources(
				"Cointreau").get(0), 1, " cl");
		Ingredient elPresiGren = new Ingredient(cocktailDAO.getCocktails(
				"El Presidente", true).get(0), ressourceDAO.getRessources(
				"Grenadine").get(0), 0.5, " cl");
		Ingredient eastSourWhiskey = new Ingredient(cocktailDAO.getCocktails(
				"Eastern Sour", true).get(0), ressourceDAO.getRessources(
				"Whiskey").get(0), 6, " cl");
		Ingredient eastSourZit = new Ingredient(cocktailDAO.getCocktails(
				"Eastern Sour", true).get(0), ressourceDAO.getRessources(
				"Zitronensaft").get(0), 3, " cl");
		Ingredient eastSourMand = new Ingredient(cocktailDAO.getCocktails(
				"Eastern Sour", true).get(0), ressourceDAO.getRessources(
				"Mandelsirup").get(0), 0.7, " cl");
		Ingredient eastSourOran = new Ingredient(cocktailDAO.getCocktails(
				"Eastern Sour", true).get(0), ressourceDAO.getRessources(
				"Orangensaft").get(0), 5, " cl");
		Ingredient eastSourZuck = new Ingredient(cocktailDAO.getCocktails(
				"Eastern Sour", true).get(0), ressourceDAO.getRessources(
				"Zuckersirup").get(0), 0.7, " cl");
		Ingredient derbyWhiskey = new Ingredient(cocktailDAO.getCocktails(
				"Derby", true).get(0), ressourceDAO.getRessources(
				"Whiskey").get(0), 6, " cl");
		Ingredient derbyBen = new Ingredient(cocktailDAO.getCocktails(
				"Derby", true).get(0), ressourceDAO.getRessources(
				"Benedictine").get(0), 0.7, " cl");
		Ingredient derbyAngost = new Ingredient(cocktailDAO.getCocktails(
				"Derby", true).get(0), ressourceDAO.getRessources(
				"Angostura Bitters").get(0), 1, " dash");
		Ingredient margTeq = new Ingredient(cocktailDAO.getCocktails(
				"Margarita", true).get(0), ressourceDAO.getRessources(
				"Tequila").get(0), 6, " cl");
		Ingredient margCoint = new Ingredient(cocktailDAO.getCocktails(
				"Margarita", true).get(0), ressourceDAO.getRessources(
				"Cointreau").get(0), 3, " cl");
		Ingredient margLim = new Ingredient(cocktailDAO.getCocktails(
				"Margarita", true).get(0), ressourceDAO.getRessources(
				"Limettensaft").get(0), 3, " cl");
		Ingredient caipiCachaca = new Ingredient(cocktailDAO.getCocktails(
				"Caipirinha", true).get(0), ressourceDAO.getRessources(
				"Cachaca").get(0), 5, " cl");
		Ingredient caipiRohrZuck = new Ingredient(cocktailDAO.getCocktails(
				"Caipirinha", true).get(0), ressourceDAO.getRessources(
				"Rohrzucker").get(0), 4, " bl");
		Ingredient caipiLim = new Ingredient(cocktailDAO.getCocktails(
				"Caipirinha", true).get(0), ressourceDAO.getRessources(
				"Limette").get(0), 1, " stk.");
		Ingredient ipanemLim = new Ingredient(cocktailDAO.getCocktails(
				"Ipanema", true).get(0), ressourceDAO.getRessources(
				"Limette").get(0), 1, " stk.");
		Ingredient ipanemRohrZuck = new Ingredient(cocktailDAO.getCocktails(
				"Ipanema", true).get(0), ressourceDAO.getRessources(
				"Rohrzucker").get(0), 5, " bl");
		Ingredient ipanemGinger = new Ingredient(cocktailDAO.getCocktails(
				"Ipanema", true).get(0), ressourceDAO.getRessources(
				"Ginger Ale").get(0), 6, " cl");
		Ingredient floridaOr = new Ingredient(cocktailDAO.getCocktails(
				"Florida", true).get(0), ressourceDAO.getRessources(
				"Orangensaft").get(0), 8, " cl");
		Ingredient floridaGrap = new Ingredient(cocktailDAO.getCocktails(
				"Florida", true).get(0), ressourceDAO.getRessources(
				"Grapefruitsaft").get(0), 4, " cl");
		Ingredient floridaZit = new Ingredient(cocktailDAO.getCocktails(
				"Florida", true).get(0), ressourceDAO.getRessources(
				"Zitronensaft").get(0), 2, " cl");
		Ingredient floridaZuck = new Ingredient(cocktailDAO.getCocktails(
				"Florida", true).get(0), ressourceDAO.getRessources(
				"Zuckersirup").get(0), 4, " cl");
		Ingredient floridaSoda = new Ingredient(cocktailDAO.getCocktails(
				"Florida", true).get(0), ressourceDAO.getRessources(
				"Soda").get(0), 0, " zum Auffüllen");
		

		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(floridaOr);
		ingredients.add(floridaGrap);
		ingredients.add(floridaZuck);
		ingredients.add(floridaZit);
		ingredients.add(floridaSoda);
		ingredients.add(ipanemGinger);
		ingredients.add(ipanemRohrZuck);
		ingredients.add(ipanemLim);
		ingredients.add(caipiCachaca);
		ingredients.add(caipiRohrZuck);
		ingredients.add(caipiLim);
		ingredients.add(margTeq);
		ingredients.add(margCoint);
		ingredients.add(margLim);
		ingredients.add(derbyWhiskey);
		ingredients.add(derbyBen);
		ingredients.add(derbyAngost);
		ingredients.add(eastSourWhiskey);
		ingredients.add(eastSourOran);
		ingredients.add(eastSourZit);
		ingredients.add(eastSourMand);
		ingredients.add(eastSourZuck);
		ingredients.add(elPresiRum);
		ingredients.add(elPresiDVerm);
		ingredients.add(elPresiCoint);
		ingredients.add(elPresiGren);
		ingredients.add(whiskeySourWhiskey);
		ingredients.add(whiskeySourLJuice);
		ingredients.add(whiskeySourSugar);
		ingredients.add(singaporeSlingAnnanas);
		ingredients.add(singaporeSlingGin);
		ingredients.add(singaporeSlingCherry);
		ingredients.add(singaporeSlingLiJuice);
		ingredients.add(singaporeSlingGren);
		ingredients.add(singaporeSlingCoint);
		ingredients.add(singaporeSlingBen);
		ingredients.add(singaporeSlingAngo);
		ingredients.add(cubaLibreRum);
		ingredients.add(cubaLibreLim);
		ingredients.add(cubaLibreCola);
		ingredients.add(maiTaiRum);
		ingredients.add(maiTaiJRum);
		ingredients.add(maiTaiLiJuice);
		ingredients.add(maiTaiMandel);
		ingredients.add(maiTaiCuracao);
		ingredients.add(ginBasilGin);
		ingredients.add(ginBasilLjuice);
		ingredients.add(ginBasilZuckerSirup);
		ingredients.add(ginBasilBasil);
		ingredients.add(martiniDVermouth);
		ingredients.add(martiniGin);
		ingredients.add(martinezGin);
		ingredients.add(martinezSVermouth);
		ingredients.add(martinezOBitters);
		ingredients.add(martinezMaraschino);
		ingredients.add(tequilaSunOJuice);
		ingredients.add(tequilaSunTeq);
		ingredients.add(tequilaSunGren);
		ingredients.add(tequilaSunLJuice);
		ingredients.add(barraBiteWodka);
		ingredients.add(barraBiteLiJuice);
		ingredients.add(barraBiteGren);
		ingredients.add(negroniGin);
		ingredients.add(negroniCamp);
		ingredients.add(negroniSVermouth);
		ingredients.add(bigBenGin);
		ingredients.add(bigBenLJuice);
		ingredients.add(bigBenOJuice);
		ingredients.add(bigBenGren);
		ingredients.add(bigBenBitter);
		ingredients.add(bolognaGin);
		ingredients.add(bolognaAperol);
		ingredients.add(bolognaDVermouth);
		ingredients.add(saturnGin);
		ingredients.add(saturnLJuice);
		ingredients.add(saturnMaracuja);
		ingredients.add(saturnFalernum);
		ingredients.add(saturnMandel);
		ingredients.add(ginFizzGin);
		ingredients.add(ginFizzOJuice);
		ingredients.add(ginFizzSugarJuice);
		for (Ingredient ingredient : ingredients) {
			save(ingredient);
		}
	}

}
