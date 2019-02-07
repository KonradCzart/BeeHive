package com.beehive.domain.weather;

import java.io.IOException;
import java.util.Optional;

import com.beehive.infrastructure.payload.WeatherDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class WeatherDTODeserializer extends JsonDeserializer<WeatherDTO> {

	@Override
	public WeatherDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = jsonParser.getCodec();
		JsonNode treeNode = objectCodec.readTree(jsonParser);
		
		JsonNode mainNode = treeNode.get("main");
		Double minTemperature = mainNode.get("temp_min").asDouble();
		Double maxTemperature = mainNode.get("temp_max").asDouble();
		Double temperature = mainNode.get("temp").asDouble();
		Double pressure = mainNode.get("pressure").asDouble();
		Integer humidity = mainNode.get("humidity").asInt();
		
		JsonNode cloudsNode = treeNode.get("clouds");
		Integer cloudsPercentage = cloudsNode.get("all").asInt();
		
		JsonNode windNode = treeNode.get("wind");
		Double windSpeed = windNode.get("speed").asDouble();
		Double windDegree = windNode.get("deg").asDouble();
		
		JsonNode weatherNode = treeNode.get("weather");
		String weatherType = weatherNode.get(0).get("main").asText();
		
		Optional<JsonNode> rainNode = Optional.ofNullable(treeNode.get("rain"));
		Double rainDownfall = rainNode.map((node) -> node.get("3h"))
				.map(JsonNode::asDouble)
				.orElse(0.0);
		
		return WeatherDTO.builder()
				.withMinTemp(minTemperature)
				.withMaxTemp(maxTemperature)
				.withTemp(temperature)
				.withPressure(pressure)
				.withHumidity(humidity)
				.withCloudPercentage(cloudsPercentage)
				.withWindSpeed(windSpeed)
				.withWindDeg(windDegree)
				.withWeatherType(weatherType)
				.withRainMililitersPer3h(rainDownfall)
				.build();
	}

}
