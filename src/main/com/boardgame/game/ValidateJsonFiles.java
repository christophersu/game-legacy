package com.boardgame.game;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

class ValidateJsonFiles {
	private static final String CONSTANT_SCHEMA_PATH = "res/boardSchema.json";
	private static final String VARIABLE_SCHEMA_PATH = 
			"res/standardGame6Schema.json";
	
	static boolean validateConstantGameState(String instancePath) {
		assert instancePath != null : "Null instance path.";
		
		boolean isSuccess = false;
		
		try {
			isSuccess = validate(CONSTANT_SCHEMA_PATH, instancePath);
		} catch (IOException | ProcessingException e) {}
		
		return isSuccess;
	}
	
	static boolean validateVariableGameState(String instancePath) {
		assert instancePath != null : "Null instance path.";
		
		boolean isSuccess = false;

		try {
			isSuccess = validate(VARIABLE_SCHEMA_PATH, instancePath);
		} catch (IOException | ProcessingException e) {}
		
		return isSuccess;
	}
	
	private static boolean validate(String schemaPath, String instancePath) 
			throws IOException, ProcessingException {
		assert schemaPath != null;
		assert instancePath != null;
		
		JsonNode schemaNode = JsonLoader.fromPath(schemaPath);	
		JsonNode instanceNode = JsonLoader.fromPath(instancePath);
		
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		
		JsonSchema schema = factory.getJsonSchema(schemaNode);
		
		ProcessingReport report = schema.validate(instanceNode);
		
		return report.isSuccess();
	}
}
