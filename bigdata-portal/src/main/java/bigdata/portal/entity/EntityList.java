
package bigdata.portal.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.NONE)
public class EntityList<T> {
	List<T> items = new ArrayList<T>();

	@XmlElementWrapper(name = "items")
	@XmlAnyElement
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<JAXBElement> getXMLItems() {
		List<JAXBElement> list = new ArrayList<JAXBElement>();
		for (T item : items) {
			list.add(new JAXBElement(new QName("item"), item.getClass(), item));
		}		
		return list;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void add(T data) {
		this.items.add(data);
	}

	public void addAll(List<T> dataList) {
		this.items.addAll(dataList);
	}
}
