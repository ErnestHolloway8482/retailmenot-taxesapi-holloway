package database;

/**
 * This class is responsible for building all of the JSQL based queries that are
 * utilized within the DAO classes for domain model read/write/modify
 * 
 * @author Ernest Holloway
 * 
 */
public class JSQLQueryBuilder {

	public String buildQueryToSearchByOneField(String fieldName, String value) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(fieldName + "=");
		stringBuilder.append("'" + value + "'");

		return stringBuilder.toString();
	}

}
