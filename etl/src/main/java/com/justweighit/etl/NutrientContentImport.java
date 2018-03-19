package com.justweighit.etl;

import com.justweighit.database.exec.FastInserter;
import com.justweighit.database.jooq.tables.records.NutrientContentRecord;
import com.justweighit.etl.reader.NDBColumn;
import com.justweighit.etl.reader.NDBFileReader;
import com.justweighit.etl.reader.NDBRow;
import com.justweighit.units.Unit;
import org.jooq.DSLContext;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static com.justweighit.database.jooq.Tables.NUTRIENT_CONTENT;
import static com.justweighit.etl.NutrientContentImport.NutDataColumns.ndbno;
import static com.justweighit.etl.NutrientContentImport.NutDataColumns.nutrNo;
import static com.justweighit.etl.NutrientContentImport.NutDataColumns.nutrVal;

public class NutrientContentImport {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	enum NutDataColumns implements NDBColumn {
		ndbno, nutrNo, nutrVal, numDataPts, stdError, srcCd, derivCd, refNdbno, addNutrMark, numStudies, min, max, df, lowEb, upEb, statCmt, addModDate, cc;
		
		public int index() {return ordinal();}
	}
	
	private final FastInserter<NutrientContentRecord> inserter;
	private final UnitResolver resolver = new UnitResolver();
	
	public NutrientContentImport(DSLContext context) {
		this.inserter = new FastInserter<>(context, NUTRIENT_CONTENT, 1000);
	}
	
	public void run() {
		logger.info("Begin...");
		NDBRow elements = null;
		try {
			NDBFileReader reader = new NDBFileReader(DataSource.NUT_DATA);
			
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
	
	private NutrientContentRecord createRecord(NDBRow elements) {
		return new NutrientContentRecord()
			.setNdbno(elements.get(ndbno).getStringValue())
			.setNutrientId(Integer.valueOf(elements.get(nutrNo).getStringValue()))
			.setValue(BigDecimal.valueOf(elements.get(nutrVal).getDoubleValue()));
	}
}
