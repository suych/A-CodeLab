package org.suych.fm.util;

import java.util.UUID;

public class IdBuilder {
	public static String newId() {
		UUID uuid = UUID.randomUUID();
		String newsGuid = uuid.toString();
		return newsGuid.toUpperCase().replaceAll("-", "");
	}
}
