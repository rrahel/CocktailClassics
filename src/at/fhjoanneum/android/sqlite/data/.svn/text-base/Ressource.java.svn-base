package at.fhjoanneum.android.sqlite.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data class for the resource.
 * This class is needed to hold all the values of an resource.
 * You have easily access to every property. 
 * @author Manuela Mayer
 *
 */
public class Ressource implements Parcelable{

	private int id;
	private String name;
	private int inBar;
	private int inShoppingList;
	
	
	public Ressource() {
		super();
	}
	
	public Ressource(String ressource, int inBar, int inShoppingList) {
		super();
		this.name = ressource;
		this.inBar = inBar;
		this.inShoppingList = inShoppingList;
		
	}
	
	public Ressource(int id, String ressource, int inBar, int inShoppingList) {
		super();
		this.id = id;
		this.name = ressource;
		this.inBar = inBar;
		this.inShoppingList = inShoppingList;
		
	}
	
	/**
	 * Needed to give a resource to another fragment.
	 * @param in parcel
	 */
	private Ressource(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.inBar = in.readInt();
		this.inShoppingList = in.readInt();
		
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
	
	public int getBar() {
		return inBar;
	}

	public void setInBar(int inBar) {
		this.inBar = inBar;
	}
	
	public int getInShoppingList() {
		return inShoppingList;
	}

	public void setInShoppingList(int inShoppingList) {
		this.inShoppingList = inShoppingList;
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
	 * Needed to give the resource to another fragment
	 * For example: you set the flag to selectedResource and call
	 * it with this flag in the other fragment
	 */
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeInt(getBar());
		parcel.writeInt(getInShoppingList());
	}
	
	public static final Parcelable.Creator<Ressource> CREATOR = new Parcelable.Creator<Ressource>() {
		public Ressource createFromParcel(Parcel in) {
			return new Ressource(in);
		}

		public Ressource[] newArray(int size) {
			return new Ressource[size];
		}
	};
	
}
