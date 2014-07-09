package fi.juunas.sqlite;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Joonas Westlin
 *
 */
public class CreateBuilder {
	public static final int FK_DEL_CASCADE = 1;
	private StringBuilder str;
	private int columnCount;
	private List<String> pkCols;
	
	private enum ColumnType{NULL, NOTNULL, PRIMARY};
	
	public CreateBuilder(String table){
		str = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		str.append(table);
		str.append(" (");
		columnCount = 0;
		pkCols = new LinkedList<String>();
	}
	
	public CreateBuilder addPkColumn(String name, String type){
		addCol(name, type, ColumnType.PRIMARY);
		return this;
	}
	
	public CreateBuilder addColumn(String name, String type){
		addCol(name, type, ColumnType.NOTNULL);
		return this;
	}
	
	public CreateBuilder addOptionalColumn(String name, String type){
		addCol(name, type, ColumnType.NULL);
		return this;
	}
	
	private void addCol(String name, String type, ColumnType colType){
		if(columnCount != 0) str.append(", ");
		
		str.append(name);
		str.append(" ");
		str.append(type);
		str.append(" ");
		
		switch(colType){
			case PRIMARY:
				pkCols.add(name);
			case NOTNULL:
				str.append("NOT NULL");
				break;
			case NULL:
				str.append("NULL");
				break;
			default:
				break;
		}
		columnCount++;
	}
	
	public CreateBuilder addForeignKey(String colName, String fTableName){
		return addForeignKey(colName, fTableName, colName, 0);
	}
	
	public CreateBuilder addForeignKey(String colName, String fTableName, int flags){
		return addForeignKey(colName, fTableName, colName, flags);
	}
	public CreateBuilder addForeignKey(String columnName, String fTableName, String fColName){
		return addForeignKey(columnName, fTableName, columnName, 0);
	}
	public CreateBuilder addForeignKey(String columnName, String fTableName, String fColName, int flags){
		str.append(", FOREIGN KEY (");
		str.append(columnName);
		str.append(") REFERENCES ");
		str.append(fTableName);
		str.append("(");
		str.append(fColName);
		str.append(")");
		
		if((flags & FK_DEL_CASCADE) != 0){
			str.append(" ON DELETE CASCADE");
		}else{
			
		}
		
		return this;
	}
	
	public String create(){
		if(pkCols.size() > 0){
			str.append(", PRIMARY KEY(");
			for(int i = 0; i < pkCols.size(); i++){
				if(i != 0){
					str.append(", ");
				}
				str.append(pkCols.get(i));
			}
			str.append(")");
		}
		str.append(");");
		return str.toString();
	}
}
