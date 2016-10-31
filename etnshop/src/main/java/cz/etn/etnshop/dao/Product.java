package cz.etn.etnshop.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = -2739622030641073945L;

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SERIALNO = "serialNo";

	private int id;

	private String name;

	private String serialNo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = COLUMN_ID)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = COLUMN_NAME, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = COLUMN_SERIALNO, nullable = true)
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
