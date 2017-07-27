package com.fsvps.lang.translate;

public class YandexTranslationServiceFactory {

	private static final String TRANSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";

	public TranslationService createTranslationService(String key) {
		TranslationService service = new TranslationService();
		service.setBasePath("src/main/resources");
		service.setInputPath("content");
		service.setOutputPath("converted");
		service.setFileName("messages");
		service.setServiceURL(TRANSLATE_URL + "?key=" + key + "&lang=en-");
		
		return service;
	}
	
}
