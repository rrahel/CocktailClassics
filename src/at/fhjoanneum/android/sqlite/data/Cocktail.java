package at.fhjoanneum.android.sqlite.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data class for the cocktail.
 * This class is needed to hold all the values of an cocktail.
 * You have easily access to every property. 
 * @author Manuela Mayer
 *
 */
public class Cocktail implements Parcelable {

	private int id;
	private String name;
	private int raiting;
	private String recipie;
	private Category category;
	private String note;
	private int alcohol;

	public Cocktail() {
		super();
	}
	
	public Cocktail(int id, String name, int raiting, String recipie, String note, int alcohol, Category category) {
		this.id = id;
		this.name = name;
		this.raiting = raiting;
		this.recipie = recipie;
		this.note = note;
		this.alcohol = alcohol;
		this.category = category;
	}

	
	public Cocktail(String name, int raiting, String recipie, String note, int alcohol, Category category) {
		this.name = name;
		this.raiting = raiting;
		this.recipie = recipie;
		this.note = note;
		this.alcohol = alcohol;
		this.category = category;
	}

	/**
	 * Needed to give a cocktail to another fragment.
	 * @param in parcel
	 */
	private Cocktail(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.raiting = in.readInt();
		this.recipie = in.readString();
		this.note = in.readString();
		this.alcohol = in.readInt();
		this.category = in.readParcelable(Category.class.getClassLoader());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return raiting;
	}

	public void setRaiting(int raiting) {
		this.raiting = raiting;
	}
	
	public String getRecipie() {
		return recipie;
	}

	public void setRecipie(String recipie) {
		this.recipie = recipie;
	}
	
	public int getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(int alcohol) {
		this.alcohol = alcohol;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Cocktail [id=" + id + ", name=" + name 
				+ ", raiting=" + raiting
				+ ", recipie=" + recipie
				+ ", note=" + note
				+ ", alcohol=" + alcohol
				+ ", category=" + category + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Needed to give the cocktail to another fragment
	 * For example: you set the flag to selectedCocktail and call
	 * it with this flag in the other fragment
	 */
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeInt(getRating());
		parcel.writeString(getRecipie());
		parcel.writeString(getNote());
		parcel.writeInt(getAlcohol());
		parcel.writeParcelable(getCategory(), flags);
	}

	public static final Parcelable.Creator<Cocktail> CREATOR = new Parcelable.Creator<Cocktail>() {
		public Cocktail createFromParcel(Parcel in) {
			return new Cocktail(in);
		}

		public Cocktail[] newArray(int size) {
			return new Cocktail[size];
		}
	};

}
