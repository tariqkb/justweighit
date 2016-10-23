package com.justweighit.etl;

import com.justweighit.database.exec.FastInserter;
import com.justweighit.database.jooq.tables.records.WeightRecord;
import com.justweighit.etl.reader.NDBColumn;
import com.justweighit.etl.reader.NDBFileReader;
import com.justweighit.etl.reader.NDBRow;
import com.justweighit.units.Unit;
import org.jooq.DSLContext;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static com.justweighit.database.jooq.Tables.WEIGHT;
import static com.justweighit.etl.WeightImport.WeightColumns.*;

public class WeightImport {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	enum WeightColumns implements NDBColumn {
		ndbno, seq, amount, measurement, grams, numDataPoints, stdDev;
		
		public int index() {return ordinal();}
	}
	
	private final FastInserter<WeightRecord> inserter;
	private final UnitResolver resolver = new UnitResolver();
	
	public WeightImport(DSLContext context) {
		this.inserter = new FastInserter<>(context, WEIGHT, 1000);
	}
	
	public void run() {
		NDBRow elements = null;
		try {
			NDBFileReader reader = new NDBFileReader(DataSource.WEIGHT);
			
			while ((elements = reader.parseLine()) != null) {
				inserter.queue(createRecord(elements));
			}
			
			inserter.insert();
		} catch (Throwable e) {
			logger.severe("Failed to process row: " + elements);
			throw new RuntimeException(e);
		}
	}
	
	private WeightRecord createRecord(NDBRow elements) {
		Unit unit = parseUnit(elements.get(WeightColumns.measurement).getStringValue());
		return new WeightRecord()
			.setNdbno(elements.get(ndbno).getStringValue())
			.setAmount(BigDecimal.valueOf(elements.get(amount).getDoubleValue()))
			.setUnit(unit != null ? unit.id() : null)
			.setDescription(elements.get(WeightColumns.measurement).getStringValue())
			.setGrams(BigDecimal.valueOf(elements.get(grams).getDoubleValue()));
	}
	
	private Unit parseUnit(String measurement) {
		return resolver.resolve(measurement);
	}
}