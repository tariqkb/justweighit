package com.justweighit.database;
//
//import org.jooq.util.GenerationTool;
//import org.jooq.util.jaxb.Configuration;
//import org.jooq.util.jaxb.CustomType;
//import org.jooq.util.jaxb.Database;
//import org.jooq.util.jaxb.ForcedType;
//import org.jooq.util.jaxb.Generate;
//import org.jooq.util.jaxb.Generator;
//import org.jooq.util.jaxb.Jdbc;
//import org.jooq.util.jaxb.Target;
//
//import com.wiseor.dbutil.BooleanConverter;
//import com.wiseor.dbutil.DatabaseConnector;
//import com.wiseor.dbutil.LocalDateTimeTypeProvider;
//import com.wiseor.dbutil.LocalDateTypeProvider;
//import com.wiseor.dbutil.LocalTimeTypeProvider;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//

public class JooqGenerator {
	
}
//
//	private DatabaseConnector connector;
//	private String packName;
//
//	public JooqGenerator(DatabaseConnector connector, String packName) {
//		this.connector = connector;
//		this.packName = packName;
//	}
//
//	public void run() throws Exception {
//
//		String username = "root";
//		String password = "";
//		String url = "jdbc:postgresql://localhost:5432/justweighit";
//		String dbname = "public";
//
//		Jdbc jdbc = new Jdbc();
//		jdbc.setUser(username);
//		jdbc.setPassword(password);
//		jdbc.setUrl(url);
//		jdbc.setDriver(org.postgresql.Driver.class.getName());
//		jdbc.setSchema(dbname);
//
//		Database db = new Database();
//		db.setName(org.jooq.util.mysql.MySQLDatabase.class.getName());
//		db.setInputSchema(dbname);
//
//		db.setCustomTypes(customTypes());
//		db.setForcedTypes(forcedTypes());
//
//		Generate generate = new Generate();
//		generate.setRelations(true);
//		generate.setDeprecated(false);
//		generate.setRecords(true);
//		generate.setImmutablePojos(false);
//		generate.setFluentSetters(true);
//		generate.setDaos(true);
//		generate.setInterfaces(true);
//
//		Target target = new Target();
//		target.setDirectory("src");
////		target.setDirectory("../DatabaseUtil/src");
//		target.setPackageName(packName);
//
//		Generator generator = new Generator();
//		//generator.setName("com.jooq.util.DefaultGenerator");
//		generator.setDatabase(db);
//		generator.setGenerate(generate);
//		generator.setTarget(target);
//
//		Configuration configuration = new Configuration();
//		configuration.setJdbc(jdbc);
//		configuration.setGenerator(generator);
//
//		GenerationTool.generate(configuration);
//
//	}
//
//	private List<CustomType> customTypes() {
//		CustomType boolType = new CustomType();
//		boolType.setName(java.lang.Boolean.class.getName());
//		boolType.setConverter(com.wiseor.dbutil.BooleanConverter.class.getName());
//
//		CustomType localTimeType = new CustomType();
//		localTimeType.setName("LocalTime");
//		localTimeType.setType(org.joda.time.LocalTime.class.getName());
//		localTimeType.setBinding(com.wiseor.dbutil.LocalTimeTypeProvider.class.getName());
//
//		CustomType localDateType = new CustomType();
//		localDateType.setName("LocalDate");
//		localDateType.setType(org.joda.time.LocalDate.class.getName());
//		localDateType.setBinding(com.wiseor.dbutil.LocalDateTypeProvider.class.getName());
//
//		CustomType localDateTimestampType = new CustomType();
//		localDateTimestampType.setName("LocalDateTime");
//		localDateTimestampType.setType(org.joda.time.LocalDateTime.class.getName());
//		localDateTimestampType.setBinding(com.wiseor.dbutil.LocalDateTimeTypeProvider.class.getName());
//
//		return new ArrayList<CustomType>(Arrays.asList(boolType, localTimeType, localDateType,
//			localDateTimestampType));
//	}
//
//	private List<ForcedType> forcedTypes() {
//		ForcedType boolForcedType = new ForcedType();
//		boolForcedType.setName(java.lang.Boolean.class.getName());
//		boolForcedType.setTypes("tinyint");
//
//		ForcedType localTimeType = new ForcedType();
//		localTimeType.setName("LocalTime");
//		localTimeType.setTypes("^time$");
//
//		ForcedType localDateType = new ForcedType();
//		localDateType.setName("LocalDate");
//		localDateType.setTypes("^date$");
//
//		ForcedType localDateTimestampType = new ForcedType();
//		localDateTimestampType.setName("LocalDateTime");
//		localDateTimestampType.setTypes("^datetime$");
//
//		return new ArrayList<ForcedType>(
//			Arrays.asList(boolForcedType, localTimeType, localDateType, localDateTimestampType));
//	}
//}
