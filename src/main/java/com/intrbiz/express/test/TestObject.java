package com.intrbiz.express.test;

import java.util.HashMap;
import java.util.Map;

public class TestObject
{
	private String nullString = null;
	private String emptyString = "";
	private String testString = "test";
	
	private int testInt = 134563;
	private long testLong = 1235522453352L;
	private float testFloat = 1234.1223F;
	private double testDouble = 12345.11235332D;
	
	private Map<String,String> testMap = new HashMap<String,String>();
	
	public TestObject()
	{
		this.testMap.put("abc","123");
	}

	public String getNullString()
	{
		return nullString;
	}

	public void setNullString(String nullString)
	{
		this.nullString = nullString;
	}

	public String getEmptyString()
	{
		return emptyString;
	}

	public void setEmptyString(String emptyString)
	{
		this.emptyString = emptyString;
	}

	public String getTestString()
	{
		return testString;
	}

	public void setTestString(String testString)
	{
		this.testString = testString;
	}

	public int getTestInt()
	{
		return testInt;
	}

	public void setTestInt(int testInt)
	{
		this.testInt = testInt;
	}

	public long getTestLong()
	{
		return testLong;
	}

	public void setTestLong(long testLong)
	{
		this.testLong = testLong;
	}

	public float getTestFloat()
	{
		return testFloat;
	}

	public void setTestFloat(float testFloat)
	{
		this.testFloat = testFloat;
	}

	public double getTestDouble()
	{
		return testDouble;
	}

	public void setTestDouble(double testDouble)
	{
		this.testDouble = testDouble;
	}

	public Map<String, String> getTestMap()
	{
		return testMap;
	}

	public void setTestMap(Map<String, String> testMap)
	{
		this.testMap = testMap;
	}
}
