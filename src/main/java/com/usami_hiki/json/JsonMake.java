package com.usami_hiki.json;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMake {

	public static void main(String[] args) throws Exception {
		new JsonMake().exe();
	}

	private void exe() throws Exception {
		Scanner scan = new Scanner(new File("txt/all.txt"));

		LinkedHashMap<String, ArknightsData> map = new LinkedHashMap<>();

		String line[];
		while (scan.hasNextLine()) {
			line = scan.nextLine().split("\t");

			System.out.println(String.join(" ", line));

			String name = line[1];

			ArknightsData data;
			if (!map.containsKey(name)) {
				data = new ArknightsData();
				data.name = name;
				data.folder = line[2];
				data.rarity = line[4];
				data.coordinates = new ArrayList<>();
			} else {
				data = map.get(name);
			}

			Coordinate coord = new Coordinate();
			coord.name = line[0];
			coord.type = Arrays.asList("front", "back", "base");
			coord.filename = line[3];

			data.coordinates.add(coord);

			map.put(name, data);
		}

		ObjectMapper mapper = new ObjectMapper();
		String testJson1 = mapper.writeValueAsString(map.values());

		System.out.println(testJson1);
	}

	class ArknightsData {
		@JsonProperty("name")
		String name;
		@JsonProperty("rarity")
		String rarity;
		@JsonProperty("folder")
		String folder;
		@JsonProperty("coordinates")
		ArrayList<Coordinate> coordinates;
	}

	class Coordinate {
		@JsonProperty("name")
		String name;
		@JsonProperty("type")
		List<String> type;
		@JsonProperty("filename")
		String filename;
	}
}
