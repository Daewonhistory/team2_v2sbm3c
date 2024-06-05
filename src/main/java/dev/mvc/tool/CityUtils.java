package dev.mvc.tool;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class CityUtils {

  public String cityConvert(String city) {
    if (city == null ){
      city = "개인ip위치파악 X";
    } else {
      Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyC0bteBRH-gZwosafVfgEdAd6e7N5HyIP8").build().getService();
      Translation translation = translate.translate(city, Translate.TranslateOption.targetLanguage("ko"));


      if (translation.getTranslatedText().equals("서울")) {
        city = translation.getTranslatedText() + "특별시";
      } else {
        city = translation.getTranslatedText() + "시";
      }
    }
    System.out.println("city"+city);

    return city;
  }
}
