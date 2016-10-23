package com.justweighit.etl;

import com.justweighit.database.exec.FastInserter;
import com.justweighit.database.jooq.tables.records.FoodRecord;
import com.justweighit.etl.reader.NDBColumn;
import com.justweighit.etl.reader.NDBFileReader;
import com.justweighit.etl.reader.NDBRow;
import org.jooq.DSLContext;

import static com.justweighit.database.jooq.Tables.FOOD;
import static com.justweighit.etl.FoodDescriptionImport.FoodDesColumns.*;

public class FoodDescriptionImport {
	
	enum FoodDesColumns implements NDBColumn {
		ndbno, foodGrpCd, longDesc, shortDesc, comName, manufacName, survey, refDesc, refuse, sciName, nFactor, proFactor, fatFactor, choFactor;
		
		public int index() {return ordinal();}
	}
	
	private final FastInserter<FoodRecord> inserter;
	
	public FoodDescriptionImport(DSLContext context) {
		this.inserter = new FastInserter<>(context, FOOD, 1000);
	}
	
	public void run() {
		try {
			NDBFileReader reader = new NDBFileReader(DataSource.FOOD_DES);
			
			NDBRow elements;
			while ((elements = reader.parseLine()) != null) {
				
				inserter.queue(createRecord(elements));
			}
			
			inserter.insert();
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private FoodRecord createRecord(NDBRow elements) {
		return new FoodRecord()
			.setNdbno(elements.get(ndbno).getStringValue())
			.setDescription(elements.get(longDesc).getStringValue())
			.setShortDescription(elements.get(shortDesc).getStringValue());
	}
}