package at.fhjoanneum.android.sqlite.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data class for the category.
 * This class is needed to hold all the values of an category.
 * You have easily access to every property. 
 * @author Mayer Manuela
 *
 */
public class Category implements Parcelable {
	private int id;
	private String name;

	public Category() {
		super();
	}

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category(String name) {
		this.name = name;
	}

	/**
	 * Needed to give a category to another fragment.
	 * @param in parcel
	 */
	private Category(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
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

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Needed to give the category to another fragment
	 * For example: you set the flag to selectedCategory and call
	 * it with this flag in the other fragment
	 */
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
	}

	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}

		public Category[] newArray(int size) {
			return new Category[size];
		}
	};
}
