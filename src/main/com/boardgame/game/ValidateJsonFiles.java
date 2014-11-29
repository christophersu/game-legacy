package com.boardgame.game;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class ValidateJsonFiles {
	public static void main(String[] args) 
			throws IOException, ProcessingException, ParseException {
		boolean result = true;
		
		
		result &= validate("res/boardSchema.json", "res/board.json");
		result &= validate("res/standardGame6Schema.json", 
				"res/standardGame6.json");
		
		if (result) {
			System.out.println("Success");
		} else {
			throw new RuntimeException("At least one file failed validation.");
		}
	}
	
	private static boolean validate(String schemaPath, String dataPath) 
			throws IOException, ProcessingException {
		JsonNode schemaNode, dataNode;
		
		try {
			schemaNode = JsonLoader.fromPath(schemaPath);	
		}
		catch (IOException e) {
			System.err.println(schemaPath);
			throw e;
		}
		
		try {
			dataNode = JsonLoader.fromPath(dataPath);	
		}
		catch (IOException e) {

			System.err.println(dataPath);
			throw e;
		}
		
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		
		JsonSchema schema = factory.getJsonSchema(schemaNode);
		
		ProcessingReport report = schema.validate(dataNode);
		
		for (ProcessingMessage message : report) {
			System.out.println(message.getMessage());
		}
		
		return report.isSuccess();
	}
}
