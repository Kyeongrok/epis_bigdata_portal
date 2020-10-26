package bigdata.portal.dmm.entity;

import java.util.ArrayList;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Category {

	private String categoryName;
	private ArrayList<SubCategory> subCategoryList;

	public Category(String categoryName) {
		this.subCategoryList = new ArrayList<SubCategory>();
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<SubCategory> getSubCategoryList() {
		return subCategoryList;
	}

	public void addSubCategory(SubCategory subCategory) {
		this.subCategoryList.add(subCategory);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return new HashCodeBuilder().append(categoryName).toHashCode();
	}

	@Override
	public String toString() {
		return "Menu [category=" + categoryName + ", subCategoryList=" + subCategoryList + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Category category = (Category) obj;

		if (obj instanceof Category) {			
			return new EqualsBuilder().append(categoryName, category.getCategoryName()).isEquals();
		}

		return false;
	}

}