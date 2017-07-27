package com.fsvps.lang.translate;

public class Translator {

	private static final String YANDEX_API_KEY 	= "";
	private static final String LANG 			= "es";
	

	public static void main(String[] args) {
		System.out.println("Starting translating to '" + LANG + "'");
		
		TranslationService translationService = new YandexTranslationServiceFactory().createTranslationService(YANDEX_API_KEY);
		translationService.translate(LANG);
		
		System.out.println("Finished translating to '" + LANG + "'");
	}
}
