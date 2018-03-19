package com.justweighit.etl;

import com.justweighit.database.exec.FastInserter;
import com.justweighit.database.jooq.tables.records.NutrientRecord;
import com.justweighit.etl.reader.NDBColumn;
import com.justweighit.etl.reader.NDBFileReader;
import com.justweighit.etl.reader.NDBRow;
import org.jooq.DSLContext;

import java.util.logging.Logger;

import static com.justweighit.database.jooq.Tables.NUTRIENT;
import static com.justweighit.etl.NutrientImport.NutrDefColumns.nutrNo;
import static com.justweighit.etl.NutrientImport.NutrDefColumns.nutrDsc;
import static com.justweighit.etl.NutrientImport.NutrDefColumns.units;

public class NutrientImport {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	enum NutrDefColumns implements NDBColumn {
		nutrNo, units, tagname, nutrDsc, numDesc, srOrder;
		
		public int index() {return ordinal();}
	}
	
	private final FastInserter<NutrientRecord> inserter;
	
	public NutrientImport(DSLContext context) {
		this.inserter = new FastInserter<>(context, NUTRIENT, 1000);
	}
	
	public void run() {
		logger.info("Begin...");
		NDBRow elements = null;
		try {
			NDBFileReader reader = new NDBFileReader(DataSource.NUTR_DEF);
			
			while ((elements = reader.parseLine()) != null) {
				inserter.queue(createRecord(elements));
			}
			
			inserter.insert();
			
			logger.info("End (" + inserter.rows() + " rows)");
		} catch (Throwable e) {
			logger.severe("Failed to process row: " + elements);
			throw new RuntimeException(e);
		}
	}
	
	private NutrientRecord createRecord(NDBRow elements) {
		return new NutrientRecord()
			.setId(Integer.valueOf(elements.get(nutrNo).getStringValue()))
			.setName(elements.get(nutrDsc).getStringValue())
			.setUnit(elements.get(units).getStringValue());
	}
}
