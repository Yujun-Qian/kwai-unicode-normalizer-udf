package com.kwai.unicodenormalizer;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

import com.kwai.unicodenormalizer.IntentParser.IntentParserResult;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2022-02-18
 */
public class IntentParserUDTF extends GenericUDTF {
	private static final Integer OUT_COLS = 3;
	//the output columns size
	private transient Object forwardColObj[] = new Object[OUT_COLS];

	private transient ObjectInspector[] inputOIs;

	/**
	 *
	 * @param argOIs check the argument is valid.
	 * @return the output column structure.
	 * @throws UDFArgumentException
	 */
	@Override
	public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
		if (argOIs.length != 2 || argOIs[0].getCategory() != ObjectInspector.Category.PRIMITIVE
				|| argOIs[1].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentException("intent parser only takes two arguments with type of string");
		}

		inputOIs = argOIs;
		List<String> outFieldNames = new ArrayList<String>();
		List<ObjectInspector> outFieldOIs = new ArrayList<ObjectInspector>();
		outFieldNames.add("query");
		outFieldNames.add("type");
		outFieldNames.add("qr_value");
		outFieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		outFieldOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
		outFieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		return  ObjectInspectorFactory.getStandardStructObjectInspector(outFieldNames, outFieldOIs);
	}

	@Override
	public void process(Object[] objects) throws HiveException {
		try {
			//need OI to convert data type to get java type
			String query = objects[0].toString();
			String sug = objects[1].toString();
			IntentParser parser = new IntentParser();
			IntentParserResult result = parser.parse(query, sug);

			forwardColObj[0] = result.getKey();
			forwardColObj[1] = result.getType();
			forwardColObj[2] = result.getValue();

			//output a row with two column
			forward(forwardColObj);
		} catch (Exception e) {
		}
	}

	@Override
	public void close() throws HiveException {
	}
}
