package at.fhjoanneum.android.sqlite.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data class for the ingredient.
 * This class is needed to hold all the values of an ingredient.
 * You have easily access to every property. 
 * @author Manuela Mayer
 *
 */
public class Ingredient implements Parcelable{
	
	private int id;
	private Cocktail cocktail;
	private Ressource ressource;
	private double amount;
	private String unit;
	
	public Ingredient() {
		super();
	}
	
	public Ingredient(Cocktail cocktail, Ressource ressource, double amount, String unit) {
		this.cocktail = cocktail;
		this.ressource = ressource;
		this.amount = amount;
		this.unit = unit;
	}
	
	public Ingredient(int id, Cocktail cocktail, Ressource ressource, double amount, String unit) {
		this.id = id;
		this.cocktail = cocktail;
		this.ressource = ressource;
		this.amount = amount;
		this.unit = unit;
	}
	
	/**
	 * Needed to give a ingredient to another fragment.
	 * @param in parcel
	 */
	private Ingredient(Parcel in) {
		super();
		this.id = in.readInt();
		this.cocktail = in.readParcelable(Cocktail.class.getClassLoader());
		this.ressource = in.readParcelable(Ressource.class.getClassLoader());
		this.amount = in.readDouble();
		this.unit = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cocktail getCocktail() {
		return cocktail;
	}

	public void setCocktail(Cocktail cocktail) {
		this.cocktail = cocktail;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return "Employee [Cocktail=" + cocktail 
				+ ", ressource=" + ressource
				+ ", amount=" + amount
				+ ", unit=" + unit + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * Needed to give the ingredient to another fragment
	 * For example: you set the flag to selectedIngredient and call
	 * it with this flag in the other fragment
	 */
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeParcelable(getCocktail(), flags);
		parcel.writeParcelable(getRessource(), flags);
		parcel.writeDouble(getAmount());
		parcel.writeString(getUnit());
	}
	
	public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
		public Ingredient createFromParcel(Parcel in) {
			return new Ingredient(in);
		}

		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};

}
